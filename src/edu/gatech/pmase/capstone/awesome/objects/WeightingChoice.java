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
 * Represents a question given to the user to weight option and option.
 */
public class WeightingChoice {

    /**
     * The label of the first option.
     */
    private String optionOneLabel;

    /**
     * The label of the second option.
     */
    private String optionTwoLabel;

    /**
     * The result of the weighting from the user.
     */
    private double result;

    /**
     * Default Constructor.
     */
    public WeightingChoice() {
        // Do nothing.
    }

    /**
     * Overloaded Constructor.
     *
     * @param optionOneLabel label of the first option.
     * @param optionTwoLabel label of the second option.
     * @param result         result of the weighting from the user.
     */
    public WeightingChoice(final String optionOneLabel,
                           final String optionTwoLabel, final double result) {
        this.optionOneLabel = optionOneLabel;
        this.optionTwoLabel = optionTwoLabel;
        this.result = result;
    }

    /**
     * Gets the label of the first option.
     *
     * @return label of the first option.
     */
    public String getOptionOneLabel() {
        return optionOneLabel;
    }

    /**
     * Sets the label of the first option.
     *
     * @param optionOneLabel label of the first option.
     */
    public void setOptionOneLabel(String optionOneLabel) {
        this.optionOneLabel = optionOneLabel;
    }

    /**
     * Gets the label of the second option.
     *
     * @return the label of the second option.
     */
    public String getOptionTwoLabel() {
        return optionTwoLabel;
    }

    /**
     * Sets the label of the second option.
     *
     * @param optionTwoLabel the label of the second option.
     */
    public void setOptionTwoLabel(String optionTwoLabel) {
        this.optionTwoLabel = optionTwoLabel;
    }

    /**
     * Get the result of the weighting from the user.
     *
     * @return result of the weighting from the user.
     */
    public double getResult() {
        return result;
    }

    /**
     * Sets the result of the weighting from the user.
     *
     * @param result result of the weighting from the user.
     */
    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WeightingOption{" + "optionOneLabel=" + optionOneLabel + ", optionTwoLabel=" + optionTwoLabel + ", result=" + result + '}';
    }

}
