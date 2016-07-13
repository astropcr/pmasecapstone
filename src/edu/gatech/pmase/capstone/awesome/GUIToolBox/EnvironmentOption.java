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
package edu.gatech.pmase.capstone.awesome.GUIToolBox;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is intended to capture all of the properties necessary to describe
 * an environment effect.  It's source was inspired by a code example on Stack Overflow.
 * Please see the following URL for more information:
 * http://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentOption {

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleIntegerProperty selectedOption = new SimpleIntegerProperty();

    public EnvironmentOption(String name, Integer selectedOption) {
        setName(name);
        setSelectedOption(selectedOption);
    }

    public final SimpleStringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {
        this.nameProperty().set(name);
    }

    public final SimpleIntegerProperty selectedOptionProperty() {
        return this.selectedOption;
    }

    public final Integer getSelectedOption() {
        return this.selectedOptionProperty().get();
    }

    public final void setSelectedOption(final Integer on) {
        this.selectedOptionProperty().set(on);
    }

    @Override
    public String toString() {
        return getName();
    }

}
