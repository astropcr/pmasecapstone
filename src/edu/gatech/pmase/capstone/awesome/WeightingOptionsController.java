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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightOptCell;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingCategory;
import java.util.ArrayList;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class WeightingOptionsController implements ControlledScreen {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(WeightingOptionsController.class);

    ScreensController myController;

    @FXML
    private ToggleGroup tg;
    @FXML
    private Button btnWopClose;
    @FXML
    private ListView weightingOptions;
    @FXML
    private Label titleLabel;
    @FXML
    private Label lblInstructions;

    // -------------------------------------------------------------------------
    // These are part of the labels the 5 criteria for weighting
    // -------------------------------------------------------------------------
    @FXML
    private TextFlow tfWeightingOption1;
    @FXML
    private TextFlow tfWeightingOption2;
    @FXML
    private TextFlow tfWeightingOption3;
    @FXML
    private TextFlow tfWeightingOption4;
    @FXML
    private TextFlow tfWeightingOption5;

    private final Text tWeightingOption1;
    private final Text tWeightingOption2;
    private final Text tWeightingOption3;
    private final Text tWeightingOption4;
    private final Text tWeightingOption5;

    private static final String STR_INSTRUCTIONS
            = "In order to generate weighting criteria based on user preferences, "
            + "please answer the following questions by selecting the desired radio "
            + "button next to each question:";
    private static final String STR_WARNING = "(Please select an option before continuing)";

    private ObservableList<WeightingChoice> tempObsList;

    private WeightingAreasOfConcern weightingOpt = WeightingAreasOfConcern.UNKNOWN;
    private DisasterResponseTradeStudySingleton DRTSS;

    public WeightingOptionsController() {
        tempObsList = FXCollections.observableArrayList();
        tg = new ToggleGroup();
        DRTSS = DisasterResponseTradeStudySingleton.getInstance();

        // ---------------------------------------------------------------------
        // Set the weighting criteria category labes
        // ---------------------------------------------------------------------
        // Future work will set these automagically (possibly
        //for (String wcLabels : WeightingCategory.getCategoryLabels()) {
        //1. create a new TextFlow and related Text field
        //2. add them to the grid...might need to switch to a GridFlow
        //3. set the Text field 
        //}
        tWeightingOption1 = new Text(WeightingCategory.EXTREMELY_MORE.label);
        tWeightingOption2 = new Text(WeightingCategory.SIGNIFICANTLY_MORE.label);
        tWeightingOption3 = new Text(WeightingCategory.EQUALLY.label);
        tWeightingOption4 = new Text(WeightingCategory.SIGNIFICANTLY_LESS.label);
        tWeightingOption5 = new Text(WeightingCategory.EXTREMELY_LESS.label);
    }

    void setupEnvOpts(WeightingAreasOfConcern weightingOpt) {
        this.weightingOpt = weightingOpt;

        // ---------------------------------------------------------------------
        // Attach the Text properties to the Text Flow now that the TextFlows
        // are constructed by the FXML loader.
        // ---------------------------------------------------------------------
        tfWeightingOption1.getChildren().setAll(tWeightingOption1);
        tfWeightingOption2.getChildren().setAll(tWeightingOption2);
        tfWeightingOption3.getChildren().setAll(tWeightingOption3);
        tfWeightingOption4.getChildren().setAll(tWeightingOption4);
        tfWeightingOption5.getChildren().setAll(tWeightingOption5);

        // ---------------------------------------------------------------------
        // Set the title and instruction labels.
        // ---------------------------------------------------------------------
        titleLabel.setText(weightingOpt.label);
        lblInstructions.textProperty().setValue(STR_INSTRUCTIONS);

        // ---------------------------------------------------------------------
        // Create the choices
        // ---------------------------------------------------------------------
        List<WeightingChoice> weightingOptList = new ArrayList<>();
        switch (weightingOpt) {
            case PLATFORMS:
                weightingOptList.addAll(DRTSS.getPlatformWeightingChoice());
                break;

            case COMMS:
                weightingOptList.addAll(DRTSS.getCommWeightingChoice());
                break;

            case SENSORS:
                weightingOptList.addAll(DRTSS.getCommWeightingChoice());
                break;
        }

        LOGGER.debug("Num Weight Opts: " + weightingOptList.size());

        tempObsList.clear();
        tempObsList.addAll(weightingOptList);
        LOGGER.debug("Num Observe Opts: " + tempObsList.size());

        weightingOptions.setItems(tempObsList);

        weightingOptions.setCellFactory(
                new Callback<ListView<WeightingChoice>, ListCell<WeightingChoice>>() {
            @Override
            public ListCell<WeightingChoice> call(ListView<WeightingChoice> weightingOptions) {
                return new WeightOptCell();
            }
        });
//        weightingOptions.setEditable(true);
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    void initialize() {
        // TODO  
    }

    private Boolean determineIfSelectionsHaveBeenMade() {
        Boolean determination = true;
        WeightingCategory wc = WeightingCategory.UNKNOWN;
        WeightingChoice wtf;

        for (WeightingChoice obj : tempObsList) {
            //wc = ((WeightOptData)obj).getSelection();
            wtf = (WeightingChoice) obj;

            if (wc != WeightingCategory.UNKNOWN) {
                determination &= true;
            } else {
                determination = false;
            }
        }
        // .....................................................................
        // peforms the following operation more efficiently 
        //
        //  for(CheckBox cb : this.weightingsOptionsCheckBoxenCollection.values()){
        //      if (cb != null) { determination &= cb.isSelected(); }
        //  }
        // .....................................................................
//        determination = weightingsOptionsCheckBoxenCollection
//                        .values()
//                        .stream()
//                        .filter((cb) -> (cb != null))
//                                .map((cb) -> cb.isSelected())
//                                .reduce(determination, (accumulator, _item) -> accumulator & _item);

        return determination;
    }

    // -------------------------------------------------------------------------
    // Event Handlers
    // -------------------------------------------------------------------------
    /**
     * This function will trigger a data update event for the Disaster Effects
     * and trigger a screen switch event for the main controller.
     *
     * @param event
     */
    @FXML
    private void doneButtonClicked(ActionEvent event) {
        Boolean allQuestionsAnswered = determineIfSelectionsHaveBeenMade();

        if (allQuestionsAnswered) {
            // Update the model

            // Update the view
            DRTSGUIModel.getInstance().updateWoccb(weightingOpt, allQuestionsAnswered);

            // Now switch the window
//            Event.fireEvent((EventTarget) event.getSource(), new ScreenSwitchEvent()); // this should use the custom event to switch windows
            this.goToMain(event);

            // Clear the warning from the label text
            this.lblInstructions.textProperty().setValue(STR_INSTRUCTIONS);
            lblInstructions.getStyleClass().add("questionOnPanel"); // could be weightingCriteriaPanelText
            lblInstructions.getStyleClass().remove("warning");
        } else {
            this.lblInstructions.textProperty().setValue(STR_WARNING);// inform the user!!!
            lblInstructions.getStyleClass().remove("questionOnPanel");
            lblInstructions.getStyleClass().add("warning");
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
