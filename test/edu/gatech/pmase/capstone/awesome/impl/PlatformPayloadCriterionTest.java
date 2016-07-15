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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the PlatformPayloadCriterion class.
 */
public class PlatformPayloadCriterionTest {

    /**
     * Test of checkArchitectureResultRemovedByFilter method, of class
     * PlatformPayloadCriterion.
     */
    @Test
    public void testCheckArchitectureResultRemovedByFilter() {
        final PlatformPayloadCriterion instance = new PlatformPayloadCriterion();

        final DRTSArchitectureResult arch = new DRTSArchitectureResult();
        final CommunicationOption comms = new CommunicationOption();
        final SensorOption sensor = new SensorOption();
        final PlatformOption platform = new PlatformOption();

        arch.setComms(comms);
        arch.setSensor(sensor);
        arch.setPlatform(platform);

        // payload = weight
        comms.setWeight(10.0);
        sensor.setWeight(15.0);
        platform.setPayload(25.0);

        boolean expResult = false;
        boolean result = instance.checkArchitectureResultRemovedByFilter(arch);
        assertEquals(expResult, result);

        // payload > weight
        comms.setWeight(10.0);
        sensor.setWeight(15.0);
        platform.setPayload(30.0);

        expResult = false;
        result = instance.checkArchitectureResultRemovedByFilter(arch);
        assertEquals(expResult, result);

        // payload < weight
        comms.setWeight(10.0);
        sensor.setWeight(15.0);
        platform.setPayload(20.0);

        expResult = true;
        result = instance.checkArchitectureResultRemovedByFilter(arch);
        assertEquals(expResult, result);
    }

}
