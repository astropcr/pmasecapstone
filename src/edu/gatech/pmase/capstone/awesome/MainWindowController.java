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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvOptStatusCell;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.GUIUpdateEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class MainWindowController implements Initializable,
        ControlledScreen {

    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(
            MainWindowController.class);

    ScreensController myController;

    @FXML
    private AnchorPane apMainWindow;

    @FXML
    private Label label;
    @FXML
    private Label lblTitleMain;
    @FXML
    private Label lblSubTitleWeightingCriteria;
    @FXML
    private Label lblSubTitleDisasterEffects;
    @FXML
    private Label lblSubTitleEnvironmentOptions;

    @FXML
    private EffectsOptionsPanel eopDisasterEffects;
    @FXML
    private Label lblDisasterEffects;
    @FXML
    private Button btnDepClose;
    @FXML
    private Button btnDepOpen;
    @FXML
    private Button btnCalculate;

    @FXML
    private GridView envStatusGrid;

    @FXML
    private CheckBox cbWeightingsPlatformsComplete;
    @FXML
    private CheckBox cbWeightingsCommunicationsComplete;
    @FXML
    private CheckBox cbWeightingsSensorsComplete;

    @FXML
    private CheckBox cbUseDefaults;

    @FXML
    private Button btnEopClose;                     // this could be used for them all?

    @FXML
    private Tooltip ttUseDefaults;

    private DisasterResponseTradeStudySingleton DRTSS;
    private DRTSGUIModel DRTSGM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DRTSS = DisasterResponseTradeStudySingleton.getInstance();
        DRTSGM = DRTSGUIModel.getInstance();

        // ---------------------------------------------------------------------
        // Attach event handlers not attached by FXML controller
        // ---------------------------------------------------------------------
        apMainWindow.addEventHandler(ScreenSwitchEvent.SCREEN_SELECTED,
                                     new ScreenSwitchEventHandler() {
                                 public void handle(ScreenSwitchEvent event) {
                                     goToEnvironmentOptions(event);
                                 }
                             ;
        });

        apMainWindow.addEventHandler(GUIUpdateEvent.SELECTION_UPDATED,
                                     new EventHandler<GUIUpdateEvent>() {
                                 public void handle(GUIUpdateEvent event) {
                                     determineCalculateButtonMode(event);
                                 }
                             ;

        });

        // ---------------------------------------------------------------------
        // Now attach all of the controllers to the model
        // ---------------------------------------------------------------------
        // ---------------------------------------------------------------------
        //                     Disaster Effects
        // ---------------------------------------------------------------------
        // .....................................................................
        // Initialize connect them to the model and initialize.
        // .....................................................................
        DRTSGM.setDisasterEffectsStatus(lblDisasterEffects);
        DRTSGM.setDisasterEffectHasBeenSelected(false);

        // ---------------------------------------------------------------------
        //                Environment/Terran Effects
        // ---------------------------------------------------------------------
        // .....................................................................
        // First, create a list of Environment/Terrain Effects that will be used
        // to populate the GridView with Environment Effect Stati
        // .....................................................................
        Set<String> strTerrainEffectStatus = TerrainEffect.getEffectLabels();
        ObservableList<TerrainEffect> tempObsList = FXCollections.
                observableArrayList();

        int i = 1;  // needs to be one ordered since the 0 orderded is an "UNKNOWN" place holder
        for (String str : strTerrainEffectStatus) {
            tempObsList.add(TerrainEffect.getEffectByIdAndCode(i, 0));
            i++;
        }
        envStatusGrid.setItems(tempObsList);

        // .....................................................................
        // Now let's create them
        // .....................................................................
        envStatusGrid.setCellFactory(
                new Callback<GridView<TerrainEffect>, GridCell<TerrainEffect>>() {
            @Override
            public GridCell<TerrainEffect> call(
                    GridView<TerrainEffect> environmentOptions) {
                return new EnvOptStatusCell();
            }
        });

        // .....................................................................
        // Initialize connect them to the model and initialize.
        // The EES panels are connected to the model inside the cell factory
        // methods, specifically insde the EnvOptStatusCell class via the
        // setInfo() call.  This is due to the EES being a custom controller.
        // .....................................................................
    }

    // -------------------------------------------------------------------------
    //                        Utility Functions
    // -------------------------------------------------------------------------
    private Boolean determineIfAllSelectionsHaveBeenMade() {
        Boolean determination;

        determination = (Boolean) (DRTSGM /* Weightings */
                                   .determineIfAllWaocSelectionsHaveBeenMade()
                                   && DRTSGM /* Environmental Factors */
                                   .determineIfAllEesSelectionsHaveBeenMade()
                                   && DRTSGM /* Disaster Effect */
                                   .determineIfAllDisEffectsSelectionsHaveBeenMade());

        LOGGER.debug(
                "determineIfAllSelectionsHaveBeenMade() has returned " + determination.
                toString()
        );

        return determination;
    }

    @FXML
    private void setDefaults() {
        // set the defaults
        //1. Disaster Effects
        DRTSGM.setDisasterEffectHasBeenSelected(Boolean.TRUE);

        //2. Environment Factors
        DRTSGM.setAllEesSelectionsToDefaults();

        //3. Weighting Criteria
        // set the calculate button to 'ret to go'
        setCalculateButtonMode();
    }

    @FXML
    private void calculateResults(ActionEvent event) {
        // ---------------------------------------------------------------------
        // 1. Check to see if all fields have been assigned.
        // ---------------------------------------------------------------------
        if (determineIfAllSelectionsHaveBeenMade() == true) // 2. If so, run the calculation
        {
            DRTSS.calculate();
        } else {
            // don't allow it
        }

        determineIfAllSelectionsHaveBeenMade();

    }

    /**
     * A catch all event handler that is meant to update the calculate button
     * when the user uses any control on the main screen.
     */
    private void determineCalculateButtonMode(GUIUpdateEvent event) {
        setCalculateButtonMode();

        LOGGER.debug(
                "determineCalculateButtonMode(GUIUpdateEvent) has been called due to event " + event.
                getEventType() + " fired by " + event.getSource().toString());
    }

    /**
     * This function will change the appearance of the calculated mode based on
     * whether or not the model is in an executable state.
     */
    private void setCalculateButtonMode() {
        if (determineIfAllSelectionsHaveBeenMade()) {
            this.btnCalculate.getStyleClass().
                    remove("calculationButtonNotReady");
            this.btnCalculate.getStyleClass().add("calculationButtonReady");
        } else {
            this.btnCalculate.getStyleClass().remove("calculationButtonReady");
            this.btnCalculate.getStyleClass().add("calculationButtonNotReady");
        }
    }

    // -------------------------------------------------------------------------
    // These functions are what switch between windows.
    // -------------------------------------------------------------------------
    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(DisasterResponseTradeStudy.screenMainID);
    }

    @FXML
    private void goToEffects(ActionEvent event) {
        myController.setScreen(DisasterResponseTradeStudy.screenEffectsOptID);
    }

    // -------------------------------------------------------------------------
    //                      Environment Status
    // -------------------------------------------------------------------------
    /**
     * This event handler will switch to the correct Environment Option window
     * selected by the user.
     *
     * @param event
     */
    private void goToEnvironmentOptions(ScreenSwitchEvent event) {
        String toSet = DisasterResponseTradeStudy.screenMainID; // the main screen is a graceful fall-through

        // First, select the ID based on the caller (button)
        EnvironmentElementStatus eesTemp = (EnvironmentElementStatus) ((Node) event.
                                                                       getSource()).
                getScene().getFocusOwner().getParent();
        toSet = ((TerrainEffect) eesTemp.getUserData()).terrainLabel;

        // Now, let's set the screen
        myController.setScreen(toSet);
    }

    // -------------------------------------------------------------------------
    //                      Weighting Criteria
    // -------------------------------------------------------------------------
    @FXML
    private void goToPlatformsWeightingCriteria(ActionEvent event) {
        myController.setScreen(WeightingAreasOfConcern.PLATFORMS.label);//DisasterResponseTradeStudy.screenPlatformWeightingID);
    }

    @FXML
    private void goToCommsWeightingCriteria(ActionEvent event) {
        myController.setScreen(WeightingAreasOfConcern.COMMS.label);
    }

    @FXML
    private void goToSensorsWeightingCriteria(ActionEvent event) {
        myController.setScreen(WeightingAreasOfConcern.SENSORS.label);//DisasterResponseTradeStudy.screenSensorsWeightingID);
    }

    /**
     * Connects all of the GUI element created in the control/view to the model
     */
    void connectToModel() {

        // ---------------------------------------------------------------------
        // Connect the Environmental Effect Status controls to the model
        // ---------------------------------------------------------------------
        ObservableList<Node> nTemp = envStatusGrid.getChildrenUnmodifiable();

        nTemp.stream().forEach((ees) -> {
            DRTSGM.addEes((TerrainEffect) ees.getUserData(),
                          (EnvironmentElementStatus) ees);
        });

        // ---------------------------------------------------------------------
        // Connect the Weighting Area check boxen controls to the model
        // ---------------------------------------------------------------------
        DRTSGM.addWaoccb(WeightingAreasOfConcern.PLATFORMS,
                         cbWeightingsPlatformsComplete);
        DRTSGM.addWaoccb(WeightingAreasOfConcern.COMMS,
                         cbWeightingsCommunicationsComplete);
        DRTSGM.addWaoccb(WeightingAreasOfConcern.SENSORS,
                         cbWeightingsSensorsComplete);
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
        return myController;
    }

}
