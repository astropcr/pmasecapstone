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

import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ControlledScreen;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.DisasterEffectCheckBoxData;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EffectsOptionsPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionQuestion;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class MainWindowController implements Initializable,
                                             ControlledScreen {

    ScreensController myController;

    
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
    
    @FXML    private EnvironmentOptionPanel eopElevation;
    @FXML   private EnvironmentElementStatus eesElevation;
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
            //button.addEventHandler(EventType.ROOT, new EventHandler<TestEvent>);
//            button.addEventHandler(TestEvent.OPTION_SELECTED, new EventHandler<TestEvent> {
//            
//                @Override
//                public void handle(TestEvent event) {
//                        System.out.println("Event is finally handled!");
//                }
//            });

            
//            button.addEventHandler(TestEvent.OPTION_SELECTED, new TestEventHandler(this.getScreenParent(), "main"));
            
////            button.addEventHandler(TestEvent.OPTION_SELECTED, new TestEventHandler(myController, "main"));
//            button.addEventHandler(TestEvent.OPTION_SELECTED, new EventHandler<TestEvent>() {
//            
//                @Override
//                public void handle(TestEvent event) {
//                    System.out.println("TestEventHandler has been provoked!!!");
//                }
//            });
        }     
            
        
//        eopElevation.removeUnusedButtons();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        attachControllersToEachOther();
        
        
        
        //this.myController.addEventFilter(TestEvent.ANY, this.testEventHandler);
        //this.myController.addEventHandler(TestEvent, eventFilter);
        
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
    
    @FXML
    private void goToEnvironmentOptions(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenEnvironmentID);
    }
    
    @FXML
    private void goToAPWeightingCriteria(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenAPWeightingID);
    }
    
    @FXML
    private void goToGPWeightingCriteria(ActionEvent event)  {
        myController.setScreen(DisasterResponseTradeStudy.screenGPWeightingID);
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
