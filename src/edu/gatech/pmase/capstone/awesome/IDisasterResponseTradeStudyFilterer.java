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

import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.List;

/**
 * Interface to implementation to filter results loaded from database based on
 * the user input.
 */
public interface IDisasterResponseTradeStudyFilterer {

    /**
     * Filters the PlatformOption list loaded from the database based on the
     * user input.
     *
     * @param selectedDisasterEffects list of disaster effects selected by the
     * user.
     * @param selectedTerrainEffects list of terrain effects selected by the
     * user.
     * @param loadedPlatformOptions list of PlatformOptions that were loaded
     * from the database.
     * @return a list of PlatformOptions that were filtered based on the user
     * input.
     */
    List<PlatformOption> filterPlatforms(final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions);

    /**
     * Filters the CommunicationOption list loaded from the database based on the
     * user input.
     *
     * @param selectedDisasterEffects list of disaster effects selected by the
     * user.
     * @param selectedTerrainEffects list of terrain effects selected by the
     * user.
     * @param loadedPlatformOptions list of PlatformOptions that were loaded
     * from the database.
     * @param loadedCommOptions list of CommunicationOptions that were loaded
     * from the database.
     * @return a list of CommunicationOption that were filtered based on the
     * user input.
     */
    List<CommunicationOption> filterCommunications(final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions, 
            List<CommunicationOption> loadedCommOptions);

    /**
     * Filters the SensorOption list loaded from the database based on the
     * user input.
     *
     * @param selectedDisasterEffects list of disaster effects selected by the
     * user.
     * @param selectedTerrainEffects list of terrain effects selected by the
     * user.
     * @param loadedPlatformOptions list of PlatformOptions that were loaded
     * from the database.
     * @param loadedSensorOptions list of SensorOption that were loaded from the
     * database.
     * @return a list of SensorOption that were filtered based on the user
     * input.
     */
    List<SensorOption> filterSensors(final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects, List<PlatformOption> loadedPlatformOptions, 
            List<SensorOption> loadedSensorOptions);
}
