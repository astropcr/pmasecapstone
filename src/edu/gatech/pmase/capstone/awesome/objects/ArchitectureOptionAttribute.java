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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Attributes about an attribute for an Architecture Option. Contains the option
 * meta data and the value.
 */
public class ArchitectureOptionAttribute {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ArchitectureOptionAttribute.class);

    /**
     * Label for the attribute.
     */
    private String label;

    /**
     * Description of the units for the attribute (ex: km or lbs).
     */
    private String units;

    /**
     * Column Number of the attribute in the database file.
     */
    private int colNum;

    /**
     * Class type for the attribute value.
     */
    private Class type;

    /**
     * Value of the attribute. Updated when value is normalized.
     */
    private Object value;

    /**
     * Order the attribute should be sorted in when looking for the best result.
     */
    private SortOrderEnum sorting;

    /**
     * Calculated priority for the attribute.
     */
    private double priority;

    /**
     * Original value of the attribute. Not updated when the attribute is
     * normalized. Starts out the same value as value.
     */
    private Object originalValue;

    /**
     * Default Constructor.
     */
    public ArchitectureOptionAttribute() {
        // Do Nothing.
    }

    /**
     * Copy Constructor. Used for the copy pattern.
     *
     * @param original the original attribute that this attribute should be a
     * copy of.
     * @throws ClassNotFoundException if cannot find the class of the value that
     * is specified in type.
     */
    public ArchitectureOptionAttribute(final ArchitectureOptionAttribute original) throws ClassNotFoundException {
        this.colNum = original.colNum;
        this.label = original.label;
        this.type = original.type;
        this.sorting = SortOrderEnum.valueOf(original.getSorting().name());
        this.type = Class.forName(original.getType().getName());
        this.units = original.units;
    }

    /**
     * Gets Label for the attribute.
     *
     * @return Label for the attribute.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets Label for the attribute.
     *
     * @param label Label for the attribute.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets Description of the units for the attribute (ex: km or lbs).
     *
     * @return Description of the units for the attribute (ex: km or lbs).
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets Description of the units for the attribute (ex: km or lbs).
     *
     * @param units Description of the units for the attribute (ex: km or lbs).
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Gets the Column Number of the attribute in the database file.
     *
     * @return Column Number of the attribute in the database file.
     */
    public int getColNum() {
        return colNum;
    }

    /**
     * Sets the Column Number of the attribute in the database file.
     *
     * @param colNum Column Number of the attribute in the database file.
     */
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    /**
     * Get the Class type for the attribute value.
     *
     * @return Class type for the attribute value.
     */
    public Class getType() {
        return type;
    }

    /**
     * Sets Class type for the attribute value.
     *
     * @param type Class type for the attribute value.
     */
    public void setType(Class type) {
        this.type = type;
    }

    /**
     * Gets the Order the attribute should be sorted in when looking for the
     * best result.
     *
     * @return Order the attribute should be sorted in when looking for the best
     * result.
     */
    public SortOrderEnum getSorting() {
        return sorting;
    }

    /**
     * Sets the Order the attribute should be sorted in when looking for the
     * best result.
     *
     * @param sorting Order the attribute should be sorted in when looking for
     * the best result.
     */
    public void setSorting(SortOrderEnum sorting) {
        this.sorting = sorting;
    }

    /**
     * Gets the Value of the attribute. Updated when value is normalized.
     *
     * @return Value of the attribute. Updated when value is normalized.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the Value of the attribute. Updated when value is normalized.
     *
     * @param value Value of the attribute. Updated when value is normalized.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the Calculated priority for the attribute.
     *
     * @return Calculated priority for the attribute.
     */
    public double getPriority() {
        return priority;
    }

    /**
     * Sets the Calculated priority for the attribute.
     *
     * @param priority Calculated priority for the attribute.
     */
    public void setPriority(double priority) {
        this.priority = priority;
    }

    /**
     * Gets the Original value of the attribute. Not updated when the attribute
     * is normalized. Starts out the same value as value.
     *
     * @return Original value of the attribute. Not updated when the attribute
     * is normalized. Starts out the same value as value.
     */
    public Object getOriginalValue() {
        return originalValue;
    }

    /**
     * Sets the Original value of the attribute. Not updated when the attribute
     * is normalized. Starts out the same value as value.
     *
     * @param originalValue Original value of the attribute. Not updated when
     * the attribute is normalized. Starts out the same value as value.
     */
    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public String toString() {
        return "ArchitectureOptionAttribute{" + "label=" + label + ", units=" + units + ", colNum=" + colNum + ", type=" + type + ", value=" + value + ", sorting=" + sorting + ", priority=" + priority + ", originalValue=" + originalValue + '}';
    }

    /**
     * Gets the value of the attribute as a double if the value is numerical.
     *
     * @param attr the attribute to get the value of
     * @return the value as a double
     */
    public static Double getAttributeNumericalValue(final ArchitectureOptionAttribute attr) {
        Double value = 0.0;

        final Class clazz = attr.getType();
        if (Number.class.isAssignableFrom(clazz)) {
            value += ((Number) attr.getValue()).doubleValue();
        } else {
            LOGGER.error("Cannot get value for attribute: " + attr.getLabel()
                    + ". Value is not a recognized class: " + clazz.getName());
            value = null;
        }

        return value;
    }

}
