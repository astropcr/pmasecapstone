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

import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility to get the values the GUI will need to render.
 */
public class GUIValuesUtil {

    /**
     * Gets the options upon with a components attributes should be prioritized
     * and weighted.
     *
     * @param loadedOptions a list of options (rows) loaded from the database
     *
     * @return list of choices the user must make as to how to weight things in
     *         an architecture
     */
    public static List<WeightingChoice> getComponentWeightingOptions(
            final List<? extends AbstractArchitectureOption> loadedOptions) {
        final List<WeightingChoice> results = new ArrayList<>();

        if (loadedOptions.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot create weighting options as no values loaded from database");
        } else {
            results.addAll(PrioritizationUtil.getWeightingChoice(loadedOptions.
                    get(0).getPrioritizationAttributess()));
        }

        return results;
    }

    /**
     * Gets all the TerrainEffects available as a List.
     *
     * @return List of all available TerrainEffects.
     */
    public static List<TerrainEffect> getAllTerrainEffects() {
        return Arrays.asList(TerrainEffect.values());
    }

    /**
     * Gets all of the DisasterEffects available as a List.
     *
     * @return List of all available DisasterEffects
     */
    public static List<DisasterEffect> getAllDisasterEffects() {
        return Arrays.asList(DisasterEffect.values());
    }

}
