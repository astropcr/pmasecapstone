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

import java.util.List;

/**
 * Communication Option read in from the Tech Market Survey based "database".
 */
public class CommunicationOption extends AbstractOnboardArchitectureOption {

    /**
     * Range of the options (in kilometers - lm).
     */
    private double range = -1.0;

    /**
     * Data rate of the option (in MegaBits per second - Mbps).
     */
    private double dataRate = -1.0;

    /**
     *
     * @return
     */
    @Deprecated
    public double getRange() {
        return range;
    }

    /**
     *
     * @param range
     */
    @Deprecated
    public void setRange(double range) {
        this.range = range;
    }

    /**
     *
     * @return
     */
    @Deprecated
    public double getDataRate() {
        return dataRate;
    }

    /**
     *
     * @param dataRate
     */
    @Deprecated
    public void setDataRate(double dataRate) {
        this.dataRate = dataRate;
    }

    @Override
    public List<ArchitectureOptionAttribute> getPrioritizationAttributess() {
        return super.getBasePrioritizationAttributes();
    }

}
