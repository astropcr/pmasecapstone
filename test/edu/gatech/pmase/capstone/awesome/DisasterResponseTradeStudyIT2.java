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
package edu.gatech.pmase.capstone.awesome;

import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the overall flow of the calculation side of the disaster response trade
 * study. Simulates user inputs with hardcoding.
 *
 * The user inputs needed are: 1) List<DisasterEffect>
 * 2) List<TerrainEffect>
 * 3) List<WeightingOption> for each (Sensor, Comms, Platform).
 */
public class DisasterResponseTradeStudyIT2 {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudyIT2.class);

    /**
     * Test user inputs.
     */
    private static final List<DisasterEffect> selectedDisasterEffects = new ArrayList<>(4);
    private static final List<TerrainEffect> selectedTerrainEffects = new ArrayList<>(4);

    @BeforeClass
    public static void setupBefore() {
        // add disasters - MIKE: Need these from UI
        selectedDisasterEffects.add(DisasterEffect.HIGH_WIND); // ID 9
        selectedDisasterEffects.add(DisasterEffect.HAZARDOUS_MATERIAL_SPILL);  // ID 10

        // add terrain - MIKE: Need these from the UI
        selectedTerrainEffects.add(TerrainEffect.ELEVATION_3);  // ID 1
        selectedTerrainEffects.add(TerrainEffect.URBANIZATION_4);   // ID 12
    }

    @Test
    public void testTradeStudy() {
        final DisasterResponseTradeStudySingleton drts = DisasterResponseTradeStudySingleton.getInstance();

        // set Disaster
        drts.setSelectedDisasterEffects(selectedDisasterEffects);

        // set weighting choices       
        drts.setCommWeightingChoice(getWeightingOptions(drts.getCommWeightingChoice()));
        drts.setSensorWeightingChoice(getWeightingOptions(drts.getSensorWeightingChoice()));
        drts.setPlatformWeightingChoice(getWeightingOptions(drts.getPlatformWeightingChoice()));

        // set terrain
        drts.setSelectedTerrainEffects(selectedTerrainEffects);

        // optimate
        drts.calculate();
    }

    /**
     * Set WeightingOptions
     *
     * @param options opts to set
     * @return options with values set
     */
    private static List<WeightingChoice> getWeightingOptions(List<WeightingChoice> options) {
        int size = options.size();
        for (WeightingChoice opt : options) {
            LOGGER.debug("Weighting Option: " + opt.getOptionOneLabel() + "---" + opt.getOptionTwoLabel() + " = " + size);
            opt.setResult(size);
            size--;
        }

        return options;
    }

}
