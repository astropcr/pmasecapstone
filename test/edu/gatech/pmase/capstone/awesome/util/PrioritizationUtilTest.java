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

import edu.gatech.pmase.capstone.awesome.impl.database.CommunicationsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.SensorsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for PrioritizationUtil class;.
 */
public class PrioritizationUtilTest {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PrioritizationUtilTest.class);

    /**
     * Test attributes.
     */
    private static final List<PlatformOption> plats = new ArrayList<>();
    private static final List<SensorOption> sensors = new ArrayList<>();
    private static final List<CommunicationOption> comms = new ArrayList<>();

    @BeforeClass
    public static void setUpClass() {
        final PlatformDatabaseDriver platDriver = new PlatformDatabaseDriver();
        final SensorsDatabaseDriver sensorDriver = new SensorsDatabaseDriver();
        final CommunicationsDatabaseDriver commsDriver = new CommunicationsDatabaseDriver();

        plats.addAll(platDriver.getPlatformOptionsFromDatabase());
        sensors.addAll(sensorDriver.getSensorOptionsFromDatabase(plats));
        comms.addAll(commsDriver.getCommOptionsFromDatabase(plats));
    }

    /**
     * Test of getWeightingChoice method, of class PrioritizationUtil.
     */
    @Test
    public void testGetWeightingOptions() {
        if (plats.isEmpty()) {
            fail("Could not load platforms from database.");
        } else {
            List<WeightingChoice> result = PrioritizationUtil.getWeightingChoice(plats.get(0).getPrioritizationAttributess());
            LOGGER.debug(result);

            result = PrioritizationUtil.getWeightingChoice(sensors.get(0).getPrioritizationAttributess());
            LOGGER.debug(result);

            result = PrioritizationUtil.getWeightingChoice(comms.get(0).getPrioritizationAttributess());
            LOGGER.debug(result);
        }
    }

    /**
     * Test of getPriorityWeightingsForAttributes method, of class
     * PrioritizationUtil.
     */
    @Test
    public void testGetPriorityWeightingsForAttributes() {
        final String opt1Label = "Ops";
        final String opt2Label = "Trans";
        final String opt3Label = "PR";
        final String opt4Label = "Planning";

        final ArchitectureOptionAttribute attr1 = new ArchitectureOptionAttribute();
        attr1.setLabel(opt1Label);

        final ArchitectureOptionAttribute attr2 = new ArchitectureOptionAttribute();
        attr2.setLabel(opt2Label);

        final ArchitectureOptionAttribute attr3 = new ArchitectureOptionAttribute();
        attr3.setLabel(opt3Label);

        final ArchitectureOptionAttribute attr4 = new ArchitectureOptionAttribute();
        attr4.setLabel(opt4Label);

        final List<ArchitectureOptionAttribute> attrs = new ArrayList<>();
        attrs.add(attr1);
        attrs.add(attr2);
        attrs.add(attr3);
        attrs.add(attr4);

        final WeightingChoice op1 = new WeightingChoice(opt2Label, opt1Label, 0.2);
        final WeightingChoice op2 = new WeightingChoice(opt3Label, opt1Label, 0.2);
        final WeightingChoice op3 = new WeightingChoice(opt4Label, opt1Label, 1.0);

        final WeightingChoice op4 = new WeightingChoice(opt3Label, opt2Label, 0.1);
        final WeightingChoice op5 = new WeightingChoice(opt4Label, opt2Label, 0.2);

        final WeightingChoice op6 = new WeightingChoice(opt4Label, opt3Label, 5.0);

        final List<WeightingChoice> options = new ArrayList<>();
        options.addAll(Arrays.asList(op1, op2, op3, op4, op5, op6));

        final List<ArchitectureOptionAttribute> results = PrioritizationUtil.getPriorityWeightingsForAttributes(options, attrs);
        for (final ArchitectureOptionAttribute result : results) {
            LOGGER.debug(result.getLabel() + " : " + result.getPriority());
        }
    }
}
