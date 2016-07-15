/*
 * The MIT License
 *
 * Copyright 2016 Georgia Tech PMASE 2014 Cohort Team Awesome.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.gatech.pmase.capstone.awesome.impl;

import edu.gatech.pmase.capstone.awesome.IDisasterResponseFinalSelectionCriterion;
import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyFinalSelector;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checks the generated architectures for the highest ranked, viable
 * architecture.
 */
public class DRTSSanityFilter implements IDisasterResponseTradeStudyFinalSelector {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DRTSSanityFilter.class);

    /**
     * Property name for the number of final results properties.
     */
    private static final String NUM_FINAL_RESULTS_PROPERTY_NAME = "num.final.results";

    /**
     * Name of the properties file.
     */
    private static final String PROPERTIES_FILE = "disasterResponseTradeStudy.properties";

    /**
     * Number of final architecture results to return to the user.
     */
    private static int NUM_FINAL_RESULTS = 3;

    /**
     * List of criteria that need to be passed to be added to final architecture
     * list.
     */
    private final static List<IDisasterResponseFinalSelectionCriterion> SELECTION_CRITERIA = new ArrayList<>(3);

    /**
     * Load property from property file.
     */
    static {
        try {
            final Properties props = new Properties();
            props.load(new FileInputStream(PROPERTIES_FILE));
            final String numResultsTemp = props.getProperty(NUM_FINAL_RESULTS_PROPERTY_NAME);

            LOGGER.debug("Number of final architecture results to return: " + numResultsTemp);
            NUM_FINAL_RESULTS = Integer.parseInt(numResultsTemp);

            // Add any new sanity checks that are made to list
            SELECTION_CRITERIA.add(new PlatformPayloadCriterion());
            SELECTION_CRITERIA.add(new PlatformCompatibilityCriterion());
        } catch (IOException ex) {
            LOGGER.error("Cannot load required properties file.", ex);
        }
    }

    @Override
    public List<DRTSArchitectureResult> selectFinalArchitecture(final List<DRTSArchitectureResult> archResults) {
        final List< DRTSArchitectureResult> finalResult = new ArrayList<>(NUM_FINAL_RESULTS);

        if (null != archResults && archResults.size() > 0) {
            LOGGER.debug("Filting through: " + archResults.size() + " architectures");
            int filterCount = 0;

            for (final DRTSArchitectureResult architectureResult : archResults) {
                if (DRTSSanityFilter.filterOutArch(architectureResult)) {
                    LOGGER.debug("Architecture Result: " + architectureResult.toString() + " filtered out.");
                    filterCount++;
                } else {
                    LOGGER.info("Found final architecture result " + (finalResult.size() + 1) + " : " + architectureResult.toString());
                    finalResult.add(architectureResult);

                    if (finalResult.size() == NUM_FINAL_RESULTS) {
                        break;  // found all we need
                    }
                }
            }
            LOGGER.debug("Filtered out " + filterCount + " architecture selections before finding " + NUM_FINAL_RESULTS);
        } else {
            LOGGER.error("List of architectures provided is invalid, cannot select final architecture(s).");
        }

        return finalResult;
    }

    /**
     * Determines if the given architecture should be filtered out.
     *
     * @param arch the architecture to check
     * @return true if should be filtered out (not included in final results),
     * false otherwise.
     */
    private static boolean filterOutArch(final DRTSArchitectureResult arch) {
        return SELECTION_CRITERIA.parallelStream().anyMatch(crit -> crit.checkArchitectureResultRemovedByFilter(arch));
    }

}
