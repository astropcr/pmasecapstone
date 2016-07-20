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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionQuestion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    
    @FXML   private EnvironmentElementStatus eesBeach;
    @FXML   private EnvironmentElementStatus eesBridges;
    @FXML   private EnvironmentElementStatus eesElevation;
    @FXML   private EnvironmentElementStatus eesFoilage;
    @FXML   private EnvironmentElementStatus eesPersistence;
    @FXML   private EnvironmentElementStatus eesPopulation;
    @FXML   private EnvironmentElementStatus eesRange;
    @FXML   private EnvironmentElementStatus eesRoads;
    @FXML   private EnvironmentElementStatus eesStreams;
    @FXML   private EnvironmentElementStatus eesTrafficability;
    @FXML   private EnvironmentElementStatus eesUrbanization;
    @FXML   private EnvironmentElementStatus eesWaterWays;
    @FXML   private EnvironmentElementStatus eesWetness;
    
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
        // TODO
        
        
        if(btnDepClose != null) {
//            button.addEventHandler(TestEvent.OPTION_SELECTED, new TestEventHandler(this.getScreenParent(), "main"));
            System.out.println(this.getClass().toString());
        }
        
        if(button != null)
        {   
            button.addEventHandler(TestEvent.OPTION_SELECTED, new TestEventHandler(this.getScreenParent(), "main"));
        }

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
        
//        DRTSGUIModel.getInstance().addWeightingOptionPanel(DisasterResponseTradeStudy.screenPlatformWeightingID, this.cbWeightingsPlatformsComplete);
        eesBeach.connectToModel();
        eesBridges.connectToModel();
        eesElevation.connectToModel();
        eesFoilage.connectToModel();
        eesPersistence.connectToModel();
        eesPopulation.connectToModel();
        eesRange.connectToModel();
        eesRoads.connectToModel();
        eesStreams.connectToModel();
        eesTrafficability.connectToModel();
        eesUrbanization.connectToModel();
        eesWaterWays.connectToModel();
        eesWetness.connectToModel();
        
        
        
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        attachControllersToEachOther();
        
        Event.fireEvent((EventTarget) event.getSource(), new TestEvent());
    }
    
    @FXML
    private void handleTestEvent(TestEvent event) {
        System.out.println("Test event successfully captured!! (original)");
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
        String toSet = DisasterResponseTradeStudy.screenMainID;
        String relatedEopPanel = "";
        
        // ---------------------------------------------------------------------
        // First, select the ID based on the caller (button)
        // ---------------------------------------------------------------------
        if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesBeach)) {
            toSet = DisasterResponseTradeStudy.screenEnvBeachID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesBridges)) {
            toSet = DisasterResponseTradeStudy.screenEnvBridgesID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesElevation)) {
            toSet = DisasterResponseTradeStudy.screenEnvElevationID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesFoilage)) {
            toSet = DisasterResponseTradeStudy.screenEnvFoilageID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesPersistence)) {
            toSet = DisasterResponseTradeStudy.screenEnvPersistenceID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesPopulation)) {
            toSet = DisasterResponseTradeStudy.screenEnvPopulationID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesRange)) {
            toSet = DisasterResponseTradeStudy.screenEnvRangeID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesRoads)) {
            toSet = DisasterResponseTradeStudy.screenEnvRoadsID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesStreams)) {
            toSet = DisasterResponseTradeStudy.screenEnvStreamsID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesTrafficability)) {
            toSet = DisasterResponseTradeStudy.screenEnvTrafficabilityID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesUrbanization)) {
            toSet = DisasterResponseTradeStudy.screenEnvUrbanizationID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesWaterWays)) {
            toSet = DisasterResponseTradeStudy.screenEnvWaterWaysID;
        }
        else if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eesWetness)) {
            toSet = DisasterResponseTradeStudy.screenEnvWetnessID;
        }
        
        // ---------------------------------------------------------------------
        // Now, let's set the screen
        // ---------------------------------------------------------------------
        myController.setScreen(toSet);
    }
    
    // -------------------------------------------------------------------------
    // Weighting Criteria
    // -------------------------------------------------------------------------
    @FXML
    private void goToPlatformsWeightingCriteria(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenPlatformWeightingID);
    }
    
    @FXML
    private void goToCommsWeightingCriteria(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenCommsWeightingID);
    }
    
    @FXML
    private void goToSensorsWeightingCriteria(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenSensorsWeightingID);
    }
    
    // -------------------------------------------------------------------------
    // Wires all of the controllers together.  Can only be completed after they
    // have all been constructed, instatiated, and the scene is set.
    // -------------------------------------------------------------------------
    public void attachControllersToEachOther()
    {
        eesElevation.setTetheredEnvironmentOptionPanel("eopElevation");  // TODO: Rename eop
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
