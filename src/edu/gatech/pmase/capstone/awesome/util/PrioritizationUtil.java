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
package edu.gatech.pmase.capstone.awesome.util;

import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides utility methods for use in Prioritization.
 */
public class PrioritizationUtil {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            PrioritizationUtil.class);

    /**
     * Given a List of ArchitectureOptionAttribute returns the weighting option
     * comparisons for them.
     *
     * @param attrs list of attributes to compare and weight
     *
     * @return list of weighting options.
     */
    public static List<WeightingChoice> getWeightingChoice(
            final List<ArchitectureOptionAttribute> attrs) {
        final List<WeightingChoice> opts = new ArrayList<>();

        final int size = attrs.size();
        LOGGER.debug(
                "Creating prioritization weighting options for " + size + " attributes.");

        for (int count = 0; count < size; count++) {
            final ArchitectureOptionAttribute attr = attrs.get(count);
            final String opt1Label = attr.getLabel();

            for (int opt2Count = count + 1; opt2Count < size; opt2Count++) {
                final ArchitectureOptionAttribute attr2 = attrs.get(opt2Count);

                final WeightingChoice opt = new WeightingChoice();
                opt.setOptionOneLabel(opt1Label);
                opt.setOptionTwoLabel(attr2.getLabel());
                opt.setResult(Double.MIN_VALUE);
                LOGGER.trace("Creating Weighting Options for Attribute: "
                        + opt.getOptionOneLabel() + " versus " + opt.
                        getOptionTwoLabel());

                opts.add(opt);
            }
        }

        LOGGER.debug(
                "Created " + opts.size() + " weighting options for " + size + " attributes.");
        return opts;
    }

    /**
     * Given a list of ArchitectureOptionAttribute, find the attribute with the
     * given label.
     *
     * @param label the label to find
     * @param attrs the list of attributes
     *
     * @return the found attribute or null if none found
     */
    public static ArchitectureOptionAttribute getAttributeFromLabel(
            final String label,
            final List<ArchitectureOptionAttribute> attrs) {
        ArchitectureOptionAttribute found = null;

        final Optional<ArchitectureOptionAttribute> result = attrs.stream().
                filter(attr -> attr.getLabel().equals(label)).findFirst();
        if (result.isPresent()) {
            found = result.get();
        } else {
            LOGGER.error("Cannot load attribute from attribute label: " + label);
        }

        return found;
    }

    /**
     * Gets the weightings for each provided attribute based upon the provided
     * weighing options. Uses a prioritization matrix to perform weighting
     * calculation. Assumes that all labels in weighting options are respected
     * in the list of attributes.
     *
     * @param options list of weighting options, likely provided based upon user
     *                inputs.
     * @param attrs   list of attributes that had a set of prioritization
     *                questions asked to determine highest priority to user.
     *
     * @return same provided list of attributes but with prioritizes added.
     */
    public static List<ArchitectureOptionAttribute> getPriorityWeightingsForAttributes(
            final List<WeightingChoice> options,
            final List<ArchitectureOptionAttribute> attrs) {

        // init and setup mapping of attr labels to values
        final Map<String, Double> priCalc = new HashMap<>();
        attrs.stream().forEach(attr -> priCalc.put(attr.getLabel(), 0.0));
        LOGGER.debug("Found " + priCalc.size() + " attributes to prioritize");

        // get sums accross columns
        for (final WeightingChoice option : options) {
            final double value = option.getResult();

            final String opt1Label = option.getOptionOneLabel();
            final String opt2Label = option.getOptionTwoLabel();
            LOGGER.debug("opt1Label "+ opt1Label + ", opt2Label" + opt2Label);

            priCalc.put(opt1Label, (priCalc.get(opt1Label) + value));
            priCalc.put(opt2Label, (priCalc.get(opt2Label) + (1 / value)));
        }

        // get total sum down all rows
        final double totalSum = priCalc.values().stream().collect(Collectors.
                summingDouble(d -> d));

        for (final ArchitectureOptionAttribute attr : attrs) {
            final double priority = (priCalc.get(attr.getLabel()) / totalSum);
            LOGGER.debug(
                    "Setting priority for attribute: " + attr.getLabel() + " to: " + priority);
            attr.setPriority(priority);
        }

        return attrs;
    }

}
