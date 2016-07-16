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
import edu.gatech.pmase.capstone.awesome.objects.enums.SortOrderEnum;
import java.util.List;

/**
 * Platform Option read in from the Tech Market Survey based "database".
 */
public class PlatformOption extends AbstractArchitectureOption {

    /**
     * Payload option can carry (in kilograms - kg).
     */
    private double payload = -1.0;

    /**
     * Type of the platform.
     */
    private PlatformType platformType = PlatformType.UNKNOWN;

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

    @Override
    public List<ArchitectureOptionAttribute> getPrioritizationAttributess() {
        final List<ArchitectureOptionAttribute> attrs = super.getBasePrioritizationAttributes();

        // get payload
        final ArchitectureOptionAttribute payloadAttr = new ArchitectureOptionAttribute();
        payloadAttr.setColNum(-1);
        payloadAttr.setLabel("Payload");
        payloadAttr.setSorting(SortOrderEnum.DESCENDING); // TODO: fix if wrong.
        payloadAttr.setType(Double.class);
        payloadAttr.setUnits("Kilograms");
        payloadAttr.setValue(this.getPayload());
        attrs.add(payloadAttr);

        return attrs;
    }

}
