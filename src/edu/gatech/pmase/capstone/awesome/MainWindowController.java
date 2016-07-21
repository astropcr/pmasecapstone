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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionQuestion;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class MainWindowController implements Initializable,
                                             ControlledScreen {

    ScreensController myController;

    @FXML    private AnchorPane apMainWindow;
    
    @FXML    private Label label;    
    @FXML    private Label lblTitleMain;
    @FXML    private Label lblSubTitleWeightingCriteria;
    @FXML    private Label lblSubTitleDisasterEffects;
    @FXML    private Label lblSubTitleEnvironmentOptions;
    
    @FXML    private WeightingOptionQuestion woqc1;    
    @FXML    private WeightingOptionQuestion woqc2;    
    @FXML    private WeightingOptionQuestion woqc3;    
    @FXML    private WeightingOptionQuestion woqc4;    
    @FXML    private WeightingOptionQuestion woqc5;    
    @FXML    private WeightingOptionQuestion woqc6;
    
    
    @FXML    private TextFlow tfWeightingOption1;    
    @FXML    private TextFlow tfWeightingOption2;    
    @FXML    private TextFlow tfWeightingOption3;    
    @FXML    private TextFlow tfWeightingOption4;    
    @FXML    private TextFlow tfWeightingOption5;
    
    @FXML    private WeightingOptionPanel wop1;
    
    @FXML   private EffectsOptionsPanel eopDisasterEffects;
    @FXML   private Label lblDisasterEffects;
    @FXML   private Button btnDepClose;
    @FXML   private Button btnDepOpen;
    
    @FXML   private GridView envStatusGrid;
    
    @FXML   private CheckBox cbWeightingsPlatformsComplete;
    @FXML   private CheckBox cbWeightingsCommunicationsComplete;
    @FXML   private CheckBox cbWeightingsSensorsComplete;
    
    @FXML   private Button btnEopClose;                     // this could be used for them all?
    
    private Text weightingOption1;
    private Text weightingOption2;
    private Text weightingOption3;
    private Text weightingOption4;
    private Text weightingOption5;
    
    @FXML   private Button button = null;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        apMainWindow.addEventHandler(ScreenSwitchEvent.SCREEN_SELECTED,
                          new ScreenSwitchEventHandler() {
                              public void handle(ScreenSwitchEvent event) {
                                  goToEnvironmentOptions(event);
                              };    
                          });
          
          
        // ---------------------------------------------------------------------
        // Now attach all of the controllers to the model
        // ---------------------------------------------------------------------
        DRTSGUIModel.getInstance().setDisasterEffectsStatus(lblDisasterEffects);
        
        
        
        
        // ---------------------------------------------------------------------
        // First, create a list of Disaster Effects that will be used to populate
        // the GridView with Environment Effect Stati
        // ---------------------------------------------------------------------
        Set<String> strTerrainEffectStatus = TerrainEffect.getEffectLabels();
        ObservableList<TerrainEffect>   tempObsList = FXCollections.observableArrayList();
        
        int i = 1;  // needs to be one ordered since the 0 orderded is an "UNKNOWN" place holder
        for(String str : strTerrainEffectStatus)
        {
            tempObsList.add(TerrainEffect.getEffectByIdAndCode(i, 0));
            i++;
        }
        envStatusGrid.setItems(tempObsList);
        
        // ---------------------------------------------------------------------
        // Now let's create them
        // ---------------------------------------------------------------------
        envStatusGrid.setCellFactory(
            new Callback<GridView<TerrainEffect>, GridCell<TerrainEffect>>() {
                @Override
                public GridCell<TerrainEffect> call(GridView<TerrainEffect> environmentOptions) {
                   return new EnvOptStatusCell();
                }
        });
        
        // ---------------------------------------------------------------------
        // And to connect them...
        // The EES panels are connected to the model inside the cell factory 
        // methods, specifically insde the EnvOptStatusCell class via the 
        // setInfo() call.  This is due to the EES being a custom controller.
        // ---------------------------------------------------------------------
    }
    
    /**
     * This function handles the opening and closing of subpanels.
     * @param event
     * @throws IOException 
     */
    
    @FXML
    private void handlePanelSwitchButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        
    }
    
    
    
    
    // -------------------------------------------------------------------------
    // These functions are what switch between windows.
    // -------------------------------------------------------------------------
    @FXML
    private void goToMain(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenMainID);
    }
    
    @FXML
    private void goToEffects(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenEffectsOptID);
    }
    
    // -------------------------------------------------------------------------
    // Environment Status
    // -------------------------------------------------------------------------
    
    /**
     * This event handler will switch to the correct Environment Option window
     * selected by the user.
     * @param event 
     */
    private void goToEnvironmentOptions(ScreenSwitchEvent event)  {
        String toSet = DisasterResponseTradeStudy.screenMainID; // the main screen is a graceful fall-through

        // First, select the ID based on the caller (button)                
        EnvironmentElementStatus eesTemp = (EnvironmentElementStatus)((Node)event.getSource()).getScene().getFocusOwner().getParent();        
        toSet = ((TerrainEffect)eesTemp.getUserData()).terrainLabel;
        
        // Now, let's set the screen       
        myController.setScreen(toSet);
    }
    
    // -------------------------------------------------------------------------
    // Weighting Criteria
    // -------------------------------------------------------------------------
    @FXML
    private void goToPlatformsWeightingCriteria(ActionEvent event)  {
        myController.setScreen(WeightingAreasOfConcern.PLATFORMS.label);//DisasterResponseTradeStudy.screenPlatformWeightingID);
    }
    
    @FXML
    private void goToCommsWeightingCriteria(ActionEvent event)  {
        myController.setScreen(WeightingAreasOfConcern.COMMS.label);
    }
    
    @FXML
    private void goToSensorsWeightingCriteria(ActionEvent event)  {
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
            DRTSGUIModel.getInstance()
                    .addEes((TerrainEffect)ees.getUserData(),
                            (EnvironmentElementStatus)ees);
        });
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
