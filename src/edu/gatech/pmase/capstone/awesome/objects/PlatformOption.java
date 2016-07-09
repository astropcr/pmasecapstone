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
package edu.gatech.pmase.capstone.awesome.objects;

import edu.gatech.pmase.capstone.awesome.objects.enums.PlatformType;

/**
 * Platform Option read in from the Tech Market Survey based "database".
 */
public class PlatformOption extends AbstractArchitectureOption {

    /**
     * Range platform can travel (in kilometers -km).
     */
    private double range;

    /**
     * Duration platform can operate (in hours - hrs).
     */
    private double opsDuration;

    /**
     * Payload option can carry (in kilograms - kg).
     */
    private double payload;

    /**
     * Type of the platform.
     */
    private PlatformType platformType;

    /**
     *
     * @return
     */
    public double getRange() {
        return range;
    }

    /**
     *
     * @param range
     */
    public void setRange(double range) {
        this.range = range;
    }

    /**
     *
     * @return
     */
    public double getOpsDuration() {
        return opsDuration;
    }

    /**
     *
     * @param opsDuration
     */
    public void setOpsDuration(double opsDuration) {
        this.opsDuration = opsDuration;
    }

    /**
     *
     * @return
     */
    public double getPayload() {
        return payload;
    }

    /**
     *
     * @param payload
     */
    public void setPayload(double payload) {
        this.payload = payload;
    }

    /**
     *
     * @return
     */
    public PlatformType getPlatformType() {
        return platformType;
    }

    /**
     *
     * @param platformType
     */
    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

}
