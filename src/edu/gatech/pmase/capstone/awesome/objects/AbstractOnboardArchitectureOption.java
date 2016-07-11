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

import edu.gatech.pmase.capstone.awesome.objects.enums.SortOrderEnum;
import java.util.ArrayList;
import java.util.List;

/**
 * Architecture option that is carried on-board a platform or vehicle of some
 * type.
 */
public abstract class AbstractOnboardArchitectureOption extends AbstractArchitectureOption {

    /**
     * Weight of option (in kilograms - kg).
     */
    private double weight = 0.0;

    /**
     * List of platform options that cannot carry the architecture option
     * onboard.
     */
    private List<PlatformOption> platformLimitations = new ArrayList<>(0);

    /**
     *
     * @return
     */
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public List<PlatformOption> getPlatformLimitations() {
        return platformLimitations;
    }

    /**
     *
     * @param platformLimitations
     */
    public void setPlatformLimitations(List<PlatformOption> platformLimitations) {
        this.platformLimitations = platformLimitations;
    }

    @Override
    protected List<ArchitectureOptionAttribute> getBasePrioritizationAttributes() {
        final List<ArchitectureOptionAttribute> attrs = super.getBasePrioritizationAttributes();

        final ArchitectureOptionAttribute weightAttr = new ArchitectureOptionAttribute();
        weightAttr.setColNum(-1);
        weightAttr.setLabel("Weight");
        weightAttr.setSorting(SortOrderEnum.DESCENDING);    // TODO: fix if wrong.
        weightAttr.setType(Double.class);
        weightAttr.setUnits("Kilograms");
        weightAttr.setValue(this.getWeight());
        attrs.add(weightAttr);

        return attrs;
    }

}
