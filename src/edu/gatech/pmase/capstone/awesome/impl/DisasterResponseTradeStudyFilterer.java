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
public class DisasterResponseTradeStudyFilterer implements IDisasterResponseTradeStudyFilterer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudyFilterer.class);

    @Override
    public List<PlatformOption> filterPlatforms(List<DisasterEffect> selectedDisasterEffects,
            List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions) {
        final List<PlatformOption> result = new ArrayList<>();

        for (final PlatformOption loadedOption : loadedPlatformOptions) {
            if (testDisasterLimitations(loadedOption, selectedDisasterEffects)
                    || testTerrainLimitations(loadedOption, selectedTerrainEffects)) {
                LOGGER.debug("Filtering out option: "+ loadedOption.getLabel()+" of type: "
                        + loadedOption.getClass().getSimpleName());
            }
        }

        return result;
    }

    @Override
    public List<CommunicationOption> filterCommunications(List<DisasterEffect> selectedDisasterEffects,
            List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions,
            List<CommunicationOption> loadedCommOptions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SensorOption> filterSensors(List<DisasterEffect> selectedDisasterEffects,
            List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions,
            List<SensorOption> loadedSensorOptions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Given the architecture option, decides whether it should be removed based
     * on user selected disaster effects.
     *
     * @param option the option loaded from the database
     * @param selectedDisasterEffects list of selected disaster effects by the
     * user
     * @return true if the given option should be removed
     */
    private static boolean testDisasterLimitations(final AbstractArchitectureOption option,
            final List<DisasterEffect> selectedDisasterEffects) {
        boolean remove = false;
        final List<DisasterEffect> disasterLimits = option.getDisasterLimitations();

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
     * @param loadedOption the option loaded from the database
     * @param selectedTerrainEffects list of selected terrain effects by the
     * user
     * @return true if the given option should be removed
     */
    private static boolean testTerrainLimitations(AbstractArchitectureOption loadedOption,
            List<TerrainEffect> selectedTerrainEffects) {
        boolean remove = false;
        final List<TerrainEffect> terrainLimits = loadedOption.getTerrainLimitation();

        for (final TerrainEffect effect : selectedTerrainEffects) {
            if (terrainLimits.contains(effect)) {
                remove = true;
                break;
            }
        }

        return remove;
    }

}
