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

import edu.gatech.pmase.capstone.awesome.impl.DRTSFilterer;
import edu.gatech.pmase.capstone.awesome.impl.DRTSSanityFilter;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudyOutputer;
import edu.gatech.pmase.capstone.awesome.impl.ahp.AHPOptimator;
import edu.gatech.pmase.capstone.awesome.impl.database.CommunicationsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.SensorsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.util.PrioritizationUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import static org.junit.Assert.assertEquals;
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
public class DisasterResponseTradeStudyIT {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            DisasterResponseTradeStudyIT.class);

    /**
     * Test user inputs.
     */
    private static final List<DisasterEffect> selectedDisasterEffects = new ArrayList<>(
            4);
    private static final List<TerrainEffect> selectedTerrainEffects = new ArrayList<>(
            4);
    private static final List<PlatformOption> loadedPlatformOptions = new ArrayList<>();
    private static final List<CommunicationOption> loadedCommOptions = new ArrayList<>();
    private static final List<SensorOption> loadedSensorOptions = new ArrayList<>();

    /**
     *
     */
    @BeforeClass
    public static void setupBefore() {
        // add disasters - MIKE: Need these from UI
        selectedDisasterEffects.add(DisasterEffect.HIGH_WIND); // ID 9
        selectedDisasterEffects.add(DisasterEffect.HAZARDOUS_MATERIAL_SPILL);  // ID 10

        // add terrain - MIKE: Need these from the UI
        selectedTerrainEffects.add(TerrainEffect.ELEVATION_3);  // ID 1
        selectedTerrainEffects.add(TerrainEffect.URBANIZATION_4);   // ID 12
    }

    /**
     *
     */
    @Test
    public void testTradeStudy() {
        // get platforms
        final PlatformDatabaseDriver platformDb = new PlatformDatabaseDriver();
        loadedPlatformOptions.
                addAll(platformDb.getPlatformOptionsFromDatabase());
        LOGGER.debug(
                "Loaded " + loadedPlatformOptions.size() + " PlatformOptions.");
        assertEquals(loadedPlatformOptions.size(), 14);

        // get comms
        final CommunicationsDatabaseDriver commDb = new CommunicationsDatabaseDriver();
        loadedCommOptions.addAll(commDb.getCommOptionsFromDatabase(
                loadedPlatformOptions));
        LOGGER.debug(
                "Loaded " + loadedCommOptions.size() + " CommunicationOption");
        assertEquals(loadedCommOptions.size(), 9);

        // get sensors
        final SensorsDatabaseDriver sensorDb = new SensorsDatabaseDriver();
        loadedSensorOptions.addAll(sensorDb.getSensorOptionsFromDatabase(
                loadedPlatformOptions));
        LOGGER.debug("Loaded " + loadedSensorOptions.size() + " SensorOption");
        assertEquals(loadedSensorOptions.size(), 8);

        // filter results
        final IDisasterResponseTradeStudyFilterer filterer = new DRTSFilterer();
        final List<PlatformOption> filteredPlatforms = filterer.filterPlatforms(
                selectedDisasterEffects, selectedTerrainEffects,
                loadedPlatformOptions);
        assertEquals(filteredPlatforms.size(), 4);

        final List<CommunicationOption> filteredComms = filterer.
                filterCommunications(selectedDisasterEffects,
                                     selectedTerrainEffects, loadedCommOptions);
        assertEquals(filteredComms.size(), 5);

        final List<SensorOption> filteredSensors = filterer.filterSensors(
                selectedDisasterEffects, selectedTerrainEffects,
                loadedSensorOptions);
        assertEquals(filteredSensors.size(), 8);

        // get priorities from user input
        final List<ArchitectureOptionAttribute> commPriorities = this.
                getCommPriorities(filteredComms);
        final List<ArchitectureOptionAttribute> sensorPriorities = this.
                getSensorPriorities(filteredSensors);
        final List<ArchitectureOptionAttribute> platformPriorities = this.
                getPlatformPriorities(filteredPlatforms);

        // optimate
        final IDisasterResponseTradeStudyOptimator optimator = new AHPOptimator();
        final List<DRTSArchitectureResult> results = optimator.
                generateOptimizedArchitectures(filteredPlatforms,
                                               filteredSensors, filteredComms,
                                               platformPriorities,
                                               sensorPriorities, commPriorities);
        assertEquals(results.size(), 160);

        // sanity check
        final IDisasterResponseTradeStudyFinalSelector sanity = new DRTSSanityFilter();
        final List<DRTSArchitectureResult> finalResults = sanity.
                selectFinalArchitecture(results);
        LOGGER.info(finalResults.size() + " final results returned");
        assertEquals(finalResults.size(), 3);

        final DRTSArchitectureResult topResult = finalResults.get(0);
        LOGGER.info("Final Architecture Selected with a score of: " + topResult.
                getTotalScore() + ": " + topResult.toString());

        // write file
        final DisasterResponseTradeStudyOutputer instance = new DisasterResponseTradeStudyOutputer();
        try {
            LOGGER.debug(instance.createOutputFile(finalResults,
                                                   selectedDisasterEffects,
                                                   selectedTerrainEffects));
        } catch (IOException | InvalidFormatException ex) {
            LOGGER.error("Cannot write results out.", ex);
        }
    }

    /**
     *
     * @param comms
     *
     * @return
     */
    private List<ArchitectureOptionAttribute> getCommPriorities(
            List<CommunicationOption> comms) {
        final List<ArchitectureOptionAttribute> priAttrs = comms.get(0).
                getPrioritizationAttributess();
        final List<WeightingChoice> options = PrioritizationUtil.
                getWeightingChoice(priAttrs);

        int size = options.size();
        for (WeightingChoice opt : options) {
            LOGGER.debug(
                    "Weighting Option: " + opt.getOptionOneLabel() + "---" + opt.
                    getOptionTwoLabel() + " = " + size);
            opt.setResult(size);
            size--;
        }

        return PrioritizationUtil.getPriorityWeightingsForAttributes(options,
                                                                     priAttrs);
    }

    /**
     *
     * @param sensors
     *
     * @return
     */
    private List<ArchitectureOptionAttribute> getSensorPriorities(
            List<SensorOption> sensors) {
        final List<ArchitectureOptionAttribute> priAttrs = sensors.get(0).
                getPrioritizationAttributess();
        final List<WeightingChoice> options = PrioritizationUtil.
                getWeightingChoice(priAttrs);

        int size = options.size();
        for (WeightingChoice opt : options) {
            LOGGER.debug(
                    "Weighting Option: " + opt.getOptionOneLabel() + "---" + opt.
                    getOptionTwoLabel() + " = " + size);
            opt.setResult(size);
            size--;
        }

        return PrioritizationUtil.getPriorityWeightingsForAttributes(options,
                                                                     priAttrs);
    }

    /**
     *
     * @param platforms
     *
     * @return
     */
    private List<ArchitectureOptionAttribute> getPlatformPriorities(
            List<PlatformOption> platforms) {
        final List<ArchitectureOptionAttribute> priAttrs = platforms.get(0).
                getPrioritizationAttributess();
        final List<WeightingChoice> options = PrioritizationUtil.
                getWeightingChoice(priAttrs);

        int size = options.size();
        for (WeightingChoice opt : options) {
            LOGGER.debug(
                    "Weighting Option: " + opt.getOptionOneLabel() + "---" + opt.
                    getOptionTwoLabel() + " = " + size);
            opt.setResult(size);
            size--;
        }

        return PrioritizationUtil.getPriorityWeightingsForAttributes(options,
                                                                     priAttrs);
    }

}
