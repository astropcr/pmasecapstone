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

import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.SensorOption;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the PlatformCompatibilityCriterion class.
 */
public class PlatformCompatibilityCriterionTest {

    /**
     * List of options to check.
     */
    private final List<PlatformOption> restrictions1 = new ArrayList<>();
    private final List<PlatformOption> restrictions2 = new ArrayList<>();

    /**
     * Test IDs.
     */
    private static final int ID_SIX = 6;
    private static final int ID_FIVE = 5;
    private static final int ID_FOUR = 4;
    private static final int ID_THREE = 3;
    private static final int ID_TWO = 2;
    private static final int ID_ONE = 1;

    /**
     * Test Options.
     */
    private final PlatformOption option1 = new PlatformOption();
    private final PlatformOption option2 = new PlatformOption();
    private final PlatformOption option3 = new PlatformOption();
    private final PlatformOption option4 = new PlatformOption();
    private final PlatformOption option5 = new PlatformOption();
    private final PlatformOption option6 = new PlatformOption();

    /**
     *
     */
    @Before
    public void setup() {
        option1.setId(ID_ONE);
        option2.setId(ID_TWO);
        option3.setId(ID_THREE);
        option4.setId(ID_FOUR);
        option5.setId(ID_FIVE);
        option6.setId(ID_SIX);

        restrictions1.add(option1);
        restrictions1.add(option2);
        restrictions1.add(option3);

        restrictions2.add(option4);
        restrictions2.add(option5);
    }

    /**
     * Test of checkArchitectureResultRemovedByFilter method, of class
     * PlatformCompatibilityCriterion.
     */
    @Test
    public void testCheckArchitectureResultRemovedByFilterFound() {
        final DRTSArchitectureResult arch = new DRTSArchitectureResult();
        final CommunicationOption comms = new CommunicationOption();
        comms.setPlatformLimitations(restrictions1);
        final SensorOption sensor = new SensorOption();
        sensor.setPlatformLimitations(restrictions2);

        arch.setComms(comms);
        arch.setSensor(sensor);
        arch.setPlatform(option1);

        final PlatformCompatibilityCriterion instance = new PlatformCompatibilityCriterion();
        final boolean expResult = true;
        final boolean result = instance.checkArchitectureResultRemovedByFilter(arch);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkArchitectureResultRemovedByFilter method, of class
     * PlatformCompatibilityCriterion.
     */
    @Test
    public void testCheckArchitectureResultRemovedByFilterNotFound() {
        final DRTSArchitectureResult arch = new DRTSArchitectureResult();
        final CommunicationOption comms = new CommunicationOption();
        comms.setPlatformLimitations(restrictions1);
        final SensorOption sensor = new SensorOption();
        sensor.setPlatformLimitations(restrictions2);

        arch.setComms(comms);
        arch.setSensor(sensor);
        arch.setPlatform(option6);

        final PlatformCompatibilityCriterion instance = new PlatformCompatibilityCriterion();
        final boolean expResult = false;
        final boolean result = instance.checkArchitectureResultRemovedByFilter(arch);
        assertEquals(expResult, result);
    }

}
