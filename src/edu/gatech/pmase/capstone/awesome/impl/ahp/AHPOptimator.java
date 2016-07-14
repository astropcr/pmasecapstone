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
package edu.gatech.pmase.capstone.awesome.impl.ahp;

import com.google.common.collect.Lists;
import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyOptimator;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Analytical Hierarchy Process based optimator.
 */
public class AHPOptimator implements IDisasterResponseTradeStudyOptimator {

    /**
     * Index in the combinations list that correspond to each component.
     */
    private static final int COMM_INDEX = 2;
    private static final int SENSOR_INDEX = 1;
    private static final int PLAT_INDEX = 0;

    /**
     * Number of components in architecture.
     */
    private static final int NUMBER_OF_COMPONENTS = 3;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AHPOptimator.class);

    @Override
    public List<DRTSArchitectureResult> generateOptimizedArchitectures(
            List<PlatformOption> platformOptions, List<SensorOption> sensorOptions,
            List<CommunicationOption> commOptions, final List<ArchitectureOptionAttribute> platformPrioritizes,
            final List<ArchitectureOptionAttribute> sensorPrioritizes, final List<ArchitectureOptionAttribute> commPrioritizes) {
        LOGGER.debug("Prioritizing architecture components");

        // sort options based on prioritization
        final ComponentAHPOptimator compOptimator = new ComponentAHPOptimator();
        platformOptions = compOptimator.generateOptimizedOption(platformOptions, platformPrioritizes);
        sensorOptions = compOptimator.generateOptimizedOption(sensorOptions, sensorPrioritizes);
        commOptions = compOptimator.generateOptimizedOption(commOptions, commPrioritizes);

        LOGGER.info("Creating architecture combinations from: " + platformOptions.size() + " platforms, "
                + sensorOptions.size() + " sensors, and " + commOptions.size() + " communication options.");

        // create, score, and sort architectures
        final List<DRTSArchitectureResult> results = Lists.cartesianProduct(platformOptions, sensorOptions, commOptions).parallelStream()
                .filter(list -> list.size() == NUMBER_OF_COMPONENTS)
                .map(list -> new DRTSArchitectureResult((PlatformOption) list.get(PLAT_INDEX),
                        (SensorOption) list.get(SENSOR_INDEX),
                        (CommunicationOption) list.get(COMM_INDEX))).sorted().collect(Collectors.toList());

        LOGGER.debug("Best Score: " + results.get(0).getTotalScore() + " to Worst Score: " + results.get(results.size() - 1).getTotalScore());
        LOGGER.info("Created " + results.size() + " architecture combinations.");
        return results;
    }

}
