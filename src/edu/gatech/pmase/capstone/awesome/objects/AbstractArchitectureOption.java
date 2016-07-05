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

/**
 * Abstract class for all Architecture options read in from Tech Market Survey Based Spreadsheets.
 */
public abstract class AbstractArchitectureOption {

    /**
     * The ranking (?-?) of the cost.
     */
    private int costRanking;

    /**
     * The actual cost value for the option.
     */
    private double actualCost;
    
    /**
     * Name of the option.
     */
    private String label;
    
    /**
     * ID of option.
     */
    private long id;

    /**
     *
     * @return
     */
    public int getCostRanking() {
        return costRanking;
    }

    /**
     *
     * @param costRanking
     */
    public void setCostRanking(int costRanking) {
        this.costRanking = costRanking;
    }

    /**
     *
     * @return
     */
    public double getActualCost() {
        return actualCost;
    }

    /**
     *
     * @param actualCost
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * 
     * @return 
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label 
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return 
     */
    public long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(long id) {
        this.id = id;
    }
}
