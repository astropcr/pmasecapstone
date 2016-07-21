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

import edu.gatech.pmase.capstone.awesome.impl.database.CommunicationsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.SensorsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * IT test of Filterer and DB Driver.
 */
public class DisasterResponseTradeStudyFiltererTest {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudyFiltererTest.class);

    /**
     * System Under Test.
     */
    private DRTSFilterer instance;

    /**
     * Test user inputs.
     */
    private static final List<DisasterEffect> selectedDisasterEffects = new ArrayList<>(4);
    private static final List<TerrainEffect> selectedTerrainEffects = new ArrayList<>(4);
    private static final List<PlatformOption> loadedPlatformOptions = new ArrayList<>();
    private static final List<CommunicationOption> loadedCommOptions = new ArrayList<>();
    private static final List<SensorOption> loadedSensorOptions = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        // get platforms
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
    }

    @Before
    public void beforeEach() {
        instance = new DRTSFilterer();
    }

    /**
     * Test of filterPlatforms method, of class
 DRTSFilterer.
     */
    @Test
    public void testFilterPlatforms() {
        LOGGER.info("testFilterPlatforms");
        selectedDisasterEffects.clear();
        selectedTerrainEffects.clear();

        // add disasters
        selectedDisasterEffects.add(DisasterEffect.HIGH_WIND); // ID 9
        selectedDisasterEffects.add(DisasterEffect.HAZARDOUS_MATERIAL_SPILL);  // ID 10

        // add terrain
        selectedTerrainEffects.add(TerrainEffect.ELEVATION_3);  // ID 1
        selectedTerrainEffects.add(TerrainEffect.URBANIZATION_4);   // ID 12

        final List<PlatformOption> results = instance.filterPlatforms(selectedDisasterEffects, selectedTerrainEffects, loadedPlatformOptions);
        assertEquals(4, results.size());

        // test Ids
        for (final PlatformOption result : results) {
            final long id = result.getId();
            if (id == 1L || id == 2L || id == 4L || id == 6L || id > 11L) {
                fail("Filter did not work found id: " + id);
            }
        }

    }

    /**
     * Test of filterCommunications method, of class
 DRTSFilterer.
     */
    @Test
    public void testFilterCommunications() {
        LOGGER.info("testFilterCommunications");
        selectedDisasterEffects.clear();
        selectedTerrainEffects.clear();

        // add disasters
        selectedDisasterEffects.add(DisasterEffect.INFRASTRUCTURE_BREAKDOWN); //ID 14

        // add terrain
        selectedTerrainEffects.add(TerrainEffect.RANGE_2); // ID 13
        selectedTerrainEffects.add(TerrainEffect.SLOPE_3); // ID 2

        final List<CommunicationOption> results = instance.filterCommunications(selectedDisasterEffects,
                selectedTerrainEffects, loadedCommOptions);
        assertEquals(2, results.size());

        for (final CommunicationOption result : results) {
            final long id = result.getId();
            if (id != 2L && id != 3L) {
                fail("Filter did not work found id: " + id);
            }
        }
    }

    /**
     * Test of filterSensors method, of class
 DRTSFilterer.
     */
    @Test
    public void testFilterSensors() {
        LOGGER.info("testFilterSensors");
        selectedDisasterEffects.clear();
        selectedTerrainEffects.clear();

        // add disasters
        selectedDisasterEffects.add(DisasterEffect.ASH); //ID 13

        // add terrain
        selectedTerrainEffects.add(TerrainEffect.PERSISTANCE_3); // ID 14
        selectedTerrainEffects.add(TerrainEffect.BRIDGES_1); // ID 5 -- shouldnt do anything

        final List<SensorOption> results = instance.filterSensors(selectedDisasterEffects, selectedTerrainEffects, loadedSensorOptions);
        assertEquals(3, results.size());

        for (final SensorOption result : results) {
            final long id = result.getId();
            if (id != 5L && id != 6L && id != 7L) {
                fail("Filter did not work found id: " + id);
            }
        }
    }

}
