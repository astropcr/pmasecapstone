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
import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyFinalSelector;
import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyOptimator;
import edu.gatech.pmase.capstone.awesome.impl.ahp.AHPOptimator;
import edu.gatech.pmase.capstone.awesome.impl.database.CommunicationsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.SensorsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
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
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Singleton to drive the Trade Study Work.
 */
public class DisasterResponseTradeStudySingleton {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudySingleton.class);

    /**
     * The Singleton Instance.
     */
    private static final DisasterResponseTradeStudySingleton instance = new DisasterResponseTradeStudySingleton();

    /**
     * Data loaded from db.
     */
    private final List<PlatformOption> loadedPlatformOptions = new ArrayList<>();
    private final List<CommunicationOption> loadedCommOptions = new ArrayList<>();
    private final List<SensorOption> loadedSensorOptions = new ArrayList<>();

    /**
     * Options set by user.
     */
    private List<DisasterEffect> selectedDisasterEffects = new ArrayList<>(4);
    private List<TerrainEffect> selectedTerrainEffects = new ArrayList<>(4);
    private List<WeightingChoice> platformWeightingChoice;
    private List<WeightingChoice> commWeightingChoice;
    private List<WeightingChoice> sensorWeightingChoice;

    /**
     * Private constructor.
     */
    private DisasterResponseTradeStudySingleton() {
        final PlatformDatabaseDriver platformDb = new PlatformDatabaseDriver();
        loadedPlatformOptions.addAll(platformDb.getPlatformOptionsFromDatabase());
        LOGGER.debug("Loaded " + loadedPlatformOptions.size() + " PlatformOptions.");

        // get comms
        final CommunicationsDatabaseDriver commDb = new CommunicationsDatabaseDriver();
        loadedCommOptions.addAll(commDb.getCommOptionsFromDatabase(loadedPlatformOptions));
        LOGGER.debug("Loaded " + loadedCommOptions.size() + " CommunicationOption");

        // get sensors
        final SensorsDatabaseDriver sensorDb = new SensorsDatabaseDriver();
        loadedSensorOptions.addAll(sensorDb.getSensorOptionsFromDatabase(loadedPlatformOptions));
        LOGGER.debug("Loaded " + loadedSensorOptions.size() + " SensorOption");

        // get weighting choices
        commWeightingChoice = PrioritizationUtil.getWeightingChoice(DisasterResponseTradeStudySingleton.getPrioritizationAttributess(loadedCommOptions));
        platformWeightingChoice = PrioritizationUtil.getWeightingChoice(DisasterResponseTradeStudySingleton.getPrioritizationAttributess(loadedPlatformOptions));
        sensorWeightingChoice = PrioritizationUtil.getWeightingChoice(DisasterResponseTradeStudySingleton.getPrioritizationAttributess(loadedSensorOptions));
    }

    /**
     * Gets the single instance to use. Not thread safe, not safe with multiple
     * class loaders.
     *
     * @return the single instance to use.
     */
    public static DisasterResponseTradeStudySingleton getInstance() {
        return instance;
    }

    /**
     * Gets the options to prioritize and weight upon.
     *
     * @param opts the options loaded from the db
     * @return the attributes of those options to weight and prioritize upon
     */
    private static List<ArchitectureOptionAttribute> getPrioritizationAttributess(
            final List<? extends AbstractArchitectureOption> opts) {
        List<ArchitectureOptionAttribute> results = new ArrayList<>(0);
        if (null != opts && !opts.isEmpty()) {
            results = opts.get(0).getPrioritizationAttributess();
        }

        return results;
    }

    /**
     * Calculates the Disaster Response Trade Study results given the same
     * input. Expects the disaster effects, terrain effects, and the three users
     * weightings to be set.
     *
     * @return Filename of the resulting architecture file
     */
    public String calculate() {
        if (selectedDisasterEffects == null || selectedTerrainEffects == null | selectedDisasterEffects.isEmpty() || selectedTerrainEffects.isEmpty()) {
            LOGGER.warn("No disaster or terrain effects specified.");
        }

        // filter results
        final IDisasterResponseTradeStudyFilterer filterer = new DRTSFilterer();
        final List<PlatformOption> filteredPlatforms = filterer.filterPlatforms(selectedDisasterEffects, selectedTerrainEffects, loadedPlatformOptions);
        final List<CommunicationOption> filteredComms = filterer.filterCommunications(selectedDisasterEffects, selectedTerrainEffects, loadedCommOptions);
        final List<SensorOption> filteredSensors = filterer.filterSensors(selectedDisasterEffects, selectedTerrainEffects, loadedSensorOptions);

        // get priorities from user input
        final List<ArchitectureOptionAttribute> commPriorities = PrioritizationUtil.getPriorityWeightingsForAttributes(commWeightingChoice,
                DisasterResponseTradeStudySingleton.getPrioritizationAttributess(filteredComms));
        final List<ArchitectureOptionAttribute> sensorPriorities = PrioritizationUtil.getPriorityWeightingsForAttributes(sensorWeightingChoice,
                DisasterResponseTradeStudySingleton.getPrioritizationAttributess(filteredSensors));
        final List<ArchitectureOptionAttribute> platformPriorities = PrioritizationUtil.getPriorityWeightingsForAttributes(platformWeightingChoice,
                DisasterResponseTradeStudySingleton.getPrioritizationAttributess(filteredPlatforms));

        // optimate
        final IDisasterResponseTradeStudyOptimator optimator = new AHPOptimator();
        final List<DRTSArchitectureResult> results = optimator.generateOptimizedArchitectures(filteredPlatforms,
                filteredSensors, filteredComms, platformPriorities, sensorPriorities, commPriorities);

        // sanity check
        final IDisasterResponseTradeStudyFinalSelector sanity = new DRTSSanityFilter();
        final List<DRTSArchitectureResult> finalResults = sanity.selectFinalArchitecture(results);
        LOGGER.info(finalResults.size() + " final results returned");

        final DRTSArchitectureResult topResult = finalResults.get(0);
        LOGGER.info("Final Architecture Selected with a score of: " + topResult.getTotalScore() + ": " + topResult.toString());

        // write file
        final DisasterResponseTradeStudyOutputer outputter = new DisasterResponseTradeStudyOutputer();
        String resultFile;
        try {
            resultFile = outputter.createOutputFile(finalResults, selectedDisasterEffects);
            LOGGER.info("Architecture Results writen to file: " + resultFile);
        } catch (IOException | InvalidFormatException ex) {
            LOGGER.error("Cannot write architecture results out.", ex);
            resultFile = "";
        }

        return resultFile;
    }

    /**
     * Gets all the TerrainEffects with a particular label (ex: "Elevation"). If
     * none found with label, returns empty List.s
     *
     * @param type the label to look by
     * @return List of TerrainEffects with the label, or empty list
     */
    public List<TerrainEffect> getAllTerrainEffectsByType(final String type) {
        List<TerrainEffect> result = TerrainEffect.getEffectByLabel(type);
        if (null == result) {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * List of Disaster Effects to use.
     *
     * @return disaster effects to use.
     */
    public List<DisasterEffect> getAllDisasterEffects() {
        return Arrays.asList(DisasterEffect.values());
    }

    /**
     *
     * @return
     */
    public List<DisasterEffect> getSelectedDisasterEffects() {
        return selectedDisasterEffects;
    }

    /**
     *
     * @param selectedDisasterEffects
     */
    public void setSelectedDisasterEffects(final List<DisasterEffect> selectedDisasterEffects) {
        this.selectedDisasterEffects = selectedDisasterEffects;
    }

    /**
     *
     * @param eff
     */
    public void addDisasterEffect(final DisasterEffect eff) {
        this.selectedDisasterEffects.add(eff);
    }

    /**
     *
     * @param eff
     */
    public void addTerrainEffect(final TerrainEffect eff) {
        this.selectedTerrainEffects.add(eff);
    }

    /**
     *
     * @return
     */
    public List<TerrainEffect> getSelectedTerrainEffects() {
        return selectedTerrainEffects;
    }

    /**
     *
     * @param selectedTerrainEffects
     */
    public void setSelectedTerrainEffects(final List<TerrainEffect> selectedTerrainEffects) {
        this.selectedTerrainEffects = selectedTerrainEffects;
    }

    /**
     *
     * @return
     */
    public List<WeightingChoice> getPlatformWeightingChoice() {
        return platformWeightingChoice;
    }

    /**
     *
     * @param platformWeightingChoice
     */
    public void setPlatformWeightingChoice(List<WeightingChoice> platformWeightingChoice) {
        this.platformWeightingChoice = platformWeightingChoice;
    }

    /**
     *
     * @return
     */
    public List<WeightingChoice> getCommWeightingChoice() {
        return commWeightingChoice;
    }

    /**
     *
     * @param commWeightingChoice
     */
    public void setCommWeightingChoice(List<WeightingChoice> commWeightingChoice) {
        this.commWeightingChoice = commWeightingChoice;
    }

    /**
     *
     * @return
     */
    public List<WeightingChoice> getSensorWeightingChoice() {
        return sensorWeightingChoice;
    }

    /**
     *
     * @param sensorWeightingChoice
     */
    public void setSensorWeightingChoice(List<WeightingChoice> sensorWeightingChoice) {
        this.sensorWeightingChoice = sensorWeightingChoice;
    }

}
