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
package edu.gatech.pmase.capstone.awesome.objects.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Effect that a disaster can create.
 */
public enum WeightingCategory {

    /**
     *
     */
    UNKNOWN(0, "Unknown", 0.0),
    /**
     *
     */
    EXTREMELY_LESS(1, "Extremely Less Important", 0.1),
    /**
     *
     */
    SIGNIFICANTLY_LESS(2, "Significantly Less Important", 0.5),
    /**
     *
     */
    EQUALLY(3, "Equally Important", 1.0),
    /**
     *
     */
    SIGNIFICANTLY_MORE(4, "Significantly More Important", 5.0),
    /**
     *
     */
    EXTREMELY_MORE(5, "Extremely More Important", 10.0);

    /**
     * The ID of the Weighting Category.
     */
    public int id;

    /**
     * Reader Friendly Label.
     */
    public final String label;

    /**
     * The value of the Weighting Category.
     */
    public final double value;

    /**
     * Constructor
     *
     * @param inId    the id of the disaster effect
     * @param inLabel the reader friendly label to use
     * @param inValue the value of the weighting category
     */
    private WeightingCategory(final int inId, final String inLabel,
                              final double inValue) {
        this.label = inLabel;
        this.id = inId;
        this.value = inValue;

    }

    /**
     * Given the ID, returns the associated WeightingCategory.
     *
     * @param inId the ID to find by
     *
     * @return the given Weighting Category. If none found to match ID, returns
     *         {@link WeightingCategory#UNKNOWN}.
     */
    public static WeightingCategory getCategoriesById(final int inId) {
        WeightingCategory effect = WeightingCategory.UNKNOWN;

        final Optional<WeightingCategory> result = Arrays.asList(
                WeightingCategory.values())
                .stream()
                .filter(eff -> (eff.id == inId))
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }

    /**
     * Given the label, returns the associated WeightingCategory.
     *
     * @param inLabel the label to find by
     *
     * @return the given Weighting Category. If none found to match label,
     *         returns {@link WeightingCategory#UNKNOWN}.
     */
    public static WeightingCategory getCategoriesByLabel(final String inLabel) {
        WeightingCategory effect = WeightingCategory.UNKNOWN;

        final Optional<WeightingCategory> result = Arrays.asList(
                WeightingCategory.values())
                .stream()
                .filter(eff -> eff.label.equals(inLabel))
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }

    /**
     * Given the Value, returns the associated WeightingCategory.
     *
     * @param inValue the value to find by
     *
     * @return the given Weighting Category. If none found to match value,
     *         returns {@link WeightingCategory#UNKNOWN}.
     */
    public static WeightingCategory getCategoriesByValue(final double inValue) {
        WeightingCategory effect = WeightingCategory.UNKNOWN;

        final Optional<WeightingCategory> result = Arrays.asList(
                WeightingCategory.values())
                .stream()
                .filter(eff -> eff.value == inValue)
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }

    /**
     * Returns a Set labels.
     *
     * @return the Set of Weighting Category labels
     */
    public static Set<String> getCategoryLabels() {
        return Arrays.asList(WeightingCategory.values())
                .stream().filter(eff -> eff.id != WeightingCategory.UNKNOWN.id)
                .map(eff -> eff.label)
                .collect(Collectors.toSet());
    }
}
