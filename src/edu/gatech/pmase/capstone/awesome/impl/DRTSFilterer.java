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

import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyFilterer;
import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of IDisasterResponseTradeStudyFilterer.
 */
public class DRTSFilterer implements IDisasterResponseTradeStudyFilterer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.
            getLogger(DRTSFilterer.class);

    @Override
    public List<PlatformOption> filterPlatforms(
            final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects,
            final List<PlatformOption> loadedPlatformOptions) {
        return filterOptions(selectedDisasterEffects, selectedTerrainEffects,
                loadedPlatformOptions);
    }

    @Override
    public List<CommunicationOption> filterCommunications(
            List<DisasterEffect> selectedDisasterEffects,
            List<TerrainEffect> selectedTerrainEffects,
            List<CommunicationOption> loadedCommOptions) {
        return filterOptions(selectedDisasterEffects, selectedTerrainEffects,
                loadedCommOptions);
    }

    @Override
    public List<SensorOption> filterSensors(
            List<DisasterEffect> selectedDisasterEffects,
            List<TerrainEffect> selectedTerrainEffects,
            List<SensorOption> loadedSensorOptions) {
        return filterOptions(selectedDisasterEffects, selectedTerrainEffects,
                loadedSensorOptions);
    }

    /**
     * Filters the options loaded from a database based upon user selected
     * disaster effects and terrain effects.
     *
     * @param <T>                     the option type that extends
     *                                AbstractArchitectureOption
     * @param selectedDisasterEffects the list of user selected disaster effects
     * @param selectedTerrainEffects  the list of user selected terrain effects
     * @param loadedOptions           a list of AbstractArchitectureOption from
     *                                a database
     *
     * @return a List of options from the loaded options but filtered based on
     *         user terrain and disaster inputs
     */
    private static <T extends AbstractArchitectureOption> List<T> filterOptions(
            final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects,
            final List<T> loadedOptions) {
        final List<T> results = new ArrayList<>();

        for (final T loadedOption : loadedOptions) {
            if (testDisasterLimitations(loadedOption, selectedDisasterEffects)
                    || testTerrainLimitations(loadedOption,
                            selectedTerrainEffects)) {
                LOGGER.debug(
                        "Filtering out option: " + loadedOption.getLabel() + " of type: "
                        + loadedOption.getClass().getSimpleName());
            } else {
                results.add(loadedOption);
            }
        }

        return results;
    }

    /**
     * Given the architecture option, decides whether it should be removed based
     * on user selected disaster effects.
     *
     * @param option                  the option loaded from the database
     * @param selectedDisasterEffects list of selected disaster effects by the
     *                                user
     *
     * @return true if the given option should be removed
     */
    private static boolean testDisasterLimitations(
            final AbstractArchitectureOption option,
            final List<DisasterEffect> selectedDisasterEffects) {
        boolean remove = false;
        final List<DisasterEffect> disasterLimits = option.
                getDisasterLimitations();

        for (final DisasterEffect effect : selectedDisasterEffects) {
            if (disasterLimits.contains(effect)) {
                remove = true;
                break;
            }
        }

        return remove;
    }

    /**
     * Given the architecture option, decides whether it should be removed based
     * on user selected terrain effects.
     *
     * @param loadedOption           the option loaded from the database
     * @param selectedTerrainEffects list of selected terrain effects by the
     *                               user
     *
     * @return true if the given option should be removed
     */
    private static boolean testTerrainLimitations(
            final AbstractArchitectureOption loadedOption,
            final List<TerrainEffect> selectedTerrainEffects) {
        boolean remove = false;
        final List<TerrainEffect> terrainLimits = loadedOption.
                getTerrainLimitation();

        for (final TerrainEffect effect : selectedTerrainEffects) {
            if (terrainLimits.contains(effect)) {
                remove = true;
                break;
            }
        }

        return remove;
    }
}
