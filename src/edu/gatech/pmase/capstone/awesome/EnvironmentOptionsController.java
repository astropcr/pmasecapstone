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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvOptCell;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentOptionsController implements ControlledScreen {

    ScreensController myController;

    @FXML
    private AnchorPane ap;
    @FXML
    private ToggleGroup tg;
    @FXML
    private Button btnEopClose;
    @FXML
    private ListView environmentOptions;
    @FXML
    private Label questionLabel;

    private ObservableList<TerrainEffect> tempObsList;

    private static final String STR_QUESTION = " is defined as follow (choose most appropriate value):";
    private static final String STR_WARNING = "(Please select an option before continuing)";
    private String envOpt = "";

    public EnvironmentOptionsController() {
        tempObsList = FXCollections.observableArrayList();
        tg = new ToggleGroup();
    }

    void setupEnvOpts(String envOpt) {
        List<TerrainEffect> envOptList = TerrainEffect.getEffectByLabel(envOpt);
        this.envOpt = envOpt;

        questionLabel.textProperty().setValue(envOpt + STR_QUESTION);
        questionLabel.getStyleClass().add("questionOnPanel");

        tempObsList.addAll(envOptList);
        environmentOptions.setItems(tempObsList);

        environmentOptions.setCellFactory(
                new Callback<ListView<TerrainEffect>, ListCell<TerrainEffect>>() {
            @Override
            public ListCell<TerrainEffect> call(ListView<TerrainEffect> environmentOptions) {
                return new EnvOptCell(tg);
            }
        });
    }

    @FXML
    void initialize() {

    }

    /**
     * This function will trigger a data update event for the Disaster Effects
     * and trigger a screen switch event for the main controller.
     *
     * @param event
     */
    @FXML
    private void doneButtonClicked(ActionEvent event) {
        if (tg.getSelectedToggle() != null) {
            // Update the backend
            TerrainEffect temp = (TerrainEffect) tg.getSelectedToggle().getUserData();
            DisasterResponseTradeStudySingleton.getInstance().addTerrainEffect(temp);

            // Update the status window
            DRTSGUIModel.getInstance().updateEesTooltip(temp, temp.codeMeaning);
            DRTSGUIModel.getInstance().updateEesStatus(temp, Integer.toString(temp.codeNum));

            // Now switch the window
            this.goToMain(event);

            // Clear the warning from the label text
            this.questionLabel.textProperty().setValue(envOpt + STR_QUESTION);
            questionLabel.getStyleClass().add("questionOnPanel");
            questionLabel.getStyleClass().remove("warning");
        } else {
            this.questionLabel.textProperty().setValue(STR_WARNING);// inform the user!!!
            questionLabel.getStyleClass().remove("questionOnPanel");
            questionLabel.getStyleClass().add("warning");
        }
    }

    // -------------------------------------------------------------------------
    // These functions are what switch between windows.
    // -------------------------------------------------------------------------
    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(DisasterResponseTradeStudy.screenMainID);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public ScreensController getScreenParent() {
        return myController; //To change body of generated methods, choose Tools | Templates.
    }
}
