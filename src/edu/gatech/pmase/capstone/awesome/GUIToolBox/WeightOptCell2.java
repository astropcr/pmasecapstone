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

import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class WeightOptCell2 extends ListCell<WeightingChoice> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(WeightOptCell2.class);

    private Label label = new Label("");
    private ToggleGroup tg = new ToggleGroup();
    private HBox hbox = new HBox(8);

    private RadioButton rb1 = new RadioButton("1/10");
    private RadioButton rb2 = new RadioButton("1/5");
    private RadioButton rb3 = new RadioButton("1");
    private RadioButton rb4 = new RadioButton("5");
    private RadioButton rb5 = new RadioButton("10");

    /**
     * Constructor.
     */
    public WeightOptCell2() {
        hbox.getChildren().add(label);

        rb1.setToggleGroup(tg);
        rb1.setSelected(false);
        rb1.setUserData(new Double(1 / 10));

        rb2.setToggleGroup(tg);
        rb2.setSelected(false);
        rb2.setUserData(new Double(1 / 5));

        rb3.setToggleGroup(tg);
        rb3.setSelected(true);
        rb3.setUserData(new Double(1));

        rb4.setToggleGroup(tg);
        rb4.setSelected(false);
        rb4.setUserData(new Double(5.0));

        rb5.setToggleGroup(tg);
        rb5.setSelected(false);
        rb5.setUserData(new Double(10.0));

        hbox.getChildren().add(rb1);
        hbox.getChildren().add(rb2);
        hbox.getChildren().add(rb3);
        hbox.getChildren().add(rb4);
        hbox.getChildren().add(rb5);
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (tg.getSelectedToggle() != null) {
                    final Toggle tog = tg.getSelectedToggle();
                    LOGGER.debug("User Data: " + tog.getUserData().toString());
                    getItem().setResult((double) tog.getUserData());
                }
            }
        });
    }

    @Override
    protected void updateItem(WeightingChoice item, boolean empty) {
        if (empty) {
            LOGGER.debug("Empty");
            label.setText("");
            tg.selectToggle(rb3);
        } else if (item != null) {
            super.updateItem(item, empty);
            LOGGER.debug(item.toString());
            label.setText("Is " + item.getOptionOneLabel() + " better than " + item.getOptionTwoLabel());
        } else {
            LOGGER.error("Null Item!");
        }

        setGraphic(hbox);
    }
}
