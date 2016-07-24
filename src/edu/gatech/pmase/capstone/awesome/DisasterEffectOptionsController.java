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
package edu.gatech.pmase.capstone.awesome;

import edu.gatech.pmase.capstone.awesome.GUIToolBox.ControlledScreen;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EffectsOptionsPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class DisasterEffectOptionsController implements ControlledScreen {

    ScreensController myController;

    @FXML
    private EffectsOptionsPanel eopDisasterEffects;
    @FXML
    private Label titleLabel;
    @FXML
    private Button btnDepClose;

    @FXML
    private Button button = null;

    private DisasterResponseTradeStudySingleton DRTSS;
    private DRTSGUIModel DRTSGM;

    private static final String STR_QUESTION = "Select all disaster effects that apply.";
    private static final String STR_WARNING = "(Please select at least one option)";

    /**
     * Initializes some things.
     */
    public void initialize() {
        this.eopDisasterEffects.setQuestion("");
        titleLabel.textProperty().setValue(STR_QUESTION); // use this label instead of the control's label

        DRTSGM = DRTSGUIModel.getInstance();
        DRTSS = DisasterResponseTradeStudySingleton.getInstance();
    }

    /**
     * This function will trigger a data update event for the Disaster Effects
     * and trigger a screen switch event for the main controller.
     *
     * @param event
     */
    @FXML
    private void doneButtonClicked(ActionEvent event) {

        List<DisasterEffect> selectedDisasterEffects = FXCollections.
                observableArrayList();
        selectedDisasterEffects = eopDisasterEffects.getSelectionList();

        // -----------------------------------------------------------------
        // If a selection has been made, update the MVC and switch back to main
        // -----------------------------------------------------------------
        if (!selectedDisasterEffects.isEmpty()) {
            // .................................................................
            // Inform the user
            // .................................................................
            titleLabel.textProperty().setValue(STR_QUESTION);
            titleLabel.getStyleClass().add("questionOnPanel");
            titleLabel.getStyleClass().remove("warning");
            // .................................................................
            // Update the model
            // .................................................................
            DRTSGM.setDisasterEffectHasBeenSelected(true);
            DRTSS.setSelectedDisasterEffects(selectedDisasterEffects);

            // .................................................................
            // Update the view
            // .................................................................
            DRTSGM.updateDisasterEffectsStatus(
                    eopDisasterEffects.getSelectionStringized()
            );

            // Now switch the window
            goToMain(event);
        } // -----------------------------------------------------------------
        // Inform the user of an error
        // -----------------------------------------------------------------
        else {
            titleLabel.textProperty().setValue(STR_WARNING);
            titleLabel.getStyleClass().add("warning");
            titleLabel.getStyleClass().remove("questionOnPanel");
        }
    }

    // -------------------------------------------------------------------------
    // These functions are what switch between windows.
    // -------------------------------------------------------------------------
    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(DisasterResponseTradeStudy.screenMainID);
    }

    /**
     *
     * @param screenParent
     */
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    /**
     *
     * @return
     */
    @Override
    public ScreensController getScreenParent() {
        return myController; //To change body of generated methods, choose Tools | Templates.
    }
}
