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

import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.enums.SortOrderEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Compares an architecture Component using user inputs and a prioritization
 * matrix.
 */
public class ComponentAHPOptimator implements IDisasterResponseTradeStudyComponentOptimator {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            ComponentAHPOptimator.class);

    @Override
    public <T extends AbstractArchitectureOption> List generateOptimizedOption(
            final List<T> options,
            final List<ArchitectureOptionAttribute> prioritizes) {
        final List<T> optionResults = new ArrayList<>(options.size());

        LOGGER.
                debug("Will prioritize on " + prioritizes.size() + " attributes.");
        // create mapping of attribute to priority
        final Map<String, Double> priorityMap = new HashMap<>();
        for (final ArchitectureOptionAttribute pri : prioritizes) {
            LOGGER.trace(
                    "Attribute: " + pri.getLabel() + " has priority: " + pri.
                    getPriority());
            priorityMap.put(pri.getLabel(), pri.getPriority());
        }

        // get attr mappings for all options
        final Map<T, List<ArchitectureOptionAttribute>> optionAttributes = new HashMap<>();
        for (final T option : options) {
            LOGGER.trace("Options Considered: " + option.getLabel());
            optionAttributes.put(option, option.getPrioritizationAttributess());
        }

        // get matrix of values
        final Collection<List<ArchitectureOptionAttribute>> values = optionAttributes.
                values();

        // get sum of each attribute total
        final Map<String, Double[]> attrMinAndMax = ComponentAHPOptimator.
                getMinAndMaxOfEachColumn(values);

        // normalize values and multiple priority weights (1-value if needed)
        values.parallelStream().forEach((optionAttr) -> {
            optionAttr.stream().forEach((attr) -> {
                final Double value = ComponentAHPOptimator.
                        normalizeAndPrioritizeValue(attr,
                                attrMinAndMax.get(attr.
                                        getLabel()),
                                priorityMap);

                if (null != value) {
                    attr.setValue(value);
                    attr.setType(Double.class);
                } else {
                    LOGGER.error("Cannot set value");
                }
            });
        });

        //  sum accross row to get total -- add to ArchOption somehow?
        for (final Map.Entry<T, List<ArchitectureOptionAttribute>> entry : optionAttributes.
                entrySet()) {
            final T option = entry.getKey();
            option.setScore(entry.getValue().stream().collect(Collectors.
                    summingDouble(attr -> (Double) attr.getValue())));
            optionResults.add(option);
        }

        // sort by score
        Collections.sort(optionResults);

        if (LOGGER.isDebugEnabled()) {
            optionResults.forEach(option -> {
                LOGGER.trace("Option:: " + option.getLabel() + " has score: " + Double.toString(option.getScore()));
            });
        }

        // return list of option
        return optionResults;
    }

    /**
     * Normalizes and prioritizes the given attributes value based upon user
     * inputs and the min and max values for the attribute.
     *
     * @param attr the attribute to normalize
     * @param attrMinAndMaxarray of doubles. The first double is the min value
     * for that attribute. The second double is the max value for that
     * @param priorityMap mapping and attribute label to it's priority as
     * decided by the user
     *
     * @return the resulting priority or null if cannot be found
     */
    private static Double normalizeAndPrioritizeValue(
            final ArchitectureOptionAttribute attr,
            final Double[] attrMinAndMax, final Map<String, Double> priorityMap) {
        final Double value = ArchitectureOptionAttribute.
                getAttributeNumericalValue(attr);
        if (null != value) {
            // normalize value
            double newValue = getNormalizedValue(value, attrMinAndMax);

            // flip if sorting decrees
            if (SortOrderEnum.ASCENDING == attr.getSorting()) {
                LOGGER.trace("Attr: " + attr.getLabel() + " is sorted " + SortOrderEnum.ASCENDING.name() + ". Inverting value");
                newValue = (1 - newValue);
            } else {
                LOGGER.trace("Attr: " + attr.getLabel() + " is sorted " + SortOrderEnum.DESCENDING.name());
            }

            // prioritize value
            final double pri = priorityMap.get(attr.getLabel());
            LOGGER.trace(
                    attr.getLabel() + " priority is: " + pri);
            attr.setPriority(pri);

            newValue = (newValue * pri);

            LOGGER.debug(
                    attr.getLabel() + " scored: " + value + " to " + newValue);
            return newValue;
        } else {
            return value;
        }

    }

    /**
     * Finds the min and max value for each attribute of the passed in attribute
     * matrix. Returns a mapping of the label of the attribute as the key and
     * and array with the min value in index 0 and the max value in index 1.
     *
     * @param optionAttributes the matrix of attributes
     *
     * @return mapping of the label of the attribute as the key and and array
     * with the min value in index 0 and the max value in index 1.
     */
    private static Map<String, Double[]> getMinAndMaxOfEachColumn(
            final Collection<List<ArchitectureOptionAttribute>> optionAttributes) {
        final Map<String, Double[]> attributeMinAndMax = new HashMap<>();

        for (final List<ArchitectureOptionAttribute> optionAttr : optionAttributes) {
            for (final ArchitectureOptionAttribute attr : optionAttr) {
                final String key = attr.getLabel();

                final Double value = ArchitectureOptionAttribute.
                        getAttributeNumericalValue(attr);
                if (null != value) {

                    Double[] minAndMax = attributeMinAndMax.get(key);
                    if (null == minAndMax || minAndMax.length < 2) {
                        minAndMax = new Double[]{Double.MAX_VALUE, Double.MIN_VALUE};
                    }

                    if (value < minAndMax[0]) {
                        minAndMax[0] = value;
                    }

                    if (value > minAndMax[1]) {
                        minAndMax[1] = value;
                    }

                    attributeMinAndMax.put(key, minAndMax);
                } else {
                    LOGGER.warn(
                            "Could not read attribute " + attr.getLabel() + " value ");
                }
            }
        }

        if (LOGGER.isTraceEnabled()) {
            final StringBuilder sb = new StringBuilder();
            attributeMinAndMax.entrySet().stream().forEach((entry) -> {
                sb.append("\n\n").append(entry.getKey()).append("\nMin: ").
                        append(entry.getValue()[0]).append("\nMax: ").append(
                        entry.getValue()[1]);
            });
            LOGGER.trace("Attribute Min and Max:" + sb.toString());
        }

        return attributeMinAndMax;
    }

    /**
     * Gets the normalized value given the original value, the min and max for
     * that attribute.
     *
     * @param origValue the original value to normalize
     * @param attrMinAndMax array of doubles. The first double is the min value
     * for that attribute. The second double is the max value for that
     * attribute.
     *
     * @return the normalized value
     */
    private static double getNormalizedValue(final double origValue,
            final Double[] attrMinAndMax) {
        double normalValue = 0;
        final double bottom = (attrMinAndMax[1] - attrMinAndMax[0]);
        final double top = (origValue - attrMinAndMax[0]);

        if (bottom != 0) {
            normalValue = (top / bottom);
        }

        LOGGER.trace("Normalized " + origValue + " to " + normalValue);
        return normalValue;
    }

}
