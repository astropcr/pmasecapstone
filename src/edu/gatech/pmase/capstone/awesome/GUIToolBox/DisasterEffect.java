/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.pmase.capstone.awesome.GUIToolBox;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is intended to capture all of the properties necessary to describe
 * a disaster effect.  It's source was inspired by a code example on Stack Overflow.
 * Please see the following URL for more information:
 * http://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class DisasterEffect {
//public static class DisasterEffect {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleBooleanProperty on = new SimpleBooleanProperty();

    public DisasterEffect(String name, boolean on) {
        setName(name);
        setOn(on);
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

    public final SimpleBooleanProperty onProperty() {
        return this.on;
    }

    public final boolean isOn() {
        return this.onProperty().get();
    }

    public final void setOn(final boolean on) {
        this.onProperty().set(on);
    }

    @Override
    public String toString() {
        return getName();
    }

}
