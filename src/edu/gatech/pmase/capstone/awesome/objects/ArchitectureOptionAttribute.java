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

/**
 *
 */
public class ArchitectureOptionAttribute {

    private String label;

    private String units;

    private int colNum;

    private Class type;

    private Object value;

    private SortOrderEnum sorting;

    /**
     * Default Constructor.
     */
    public ArchitectureOptionAttribute() {
    }

    /**
     * Copy Constructor.
     *
     * @param original
     * @throws ClassNotFoundException
     */
    public ArchitectureOptionAttribute(ArchitectureOptionAttribute original) throws ClassNotFoundException {
        this.colNum = original.colNum;
        this.label = original.label;
        this.type = original.type;
        this.sorting = SortOrderEnum.valueOf(original.getSorting().name());
        this.type = Class.forName(original.getType().getName());
        this.units = original.units;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public SortOrderEnum getSorting() {
        return sorting;
    }

    public void setSorting(SortOrderEnum sorting) {
        this.sorting = sorting;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ArchitectureOptionAttribute{" + "label=" + label + ", units=" + units + ", colNum=" + colNum + ", type=" + type + ", value=" + value + ", sorting=" + sorting + '}';
    }

}
