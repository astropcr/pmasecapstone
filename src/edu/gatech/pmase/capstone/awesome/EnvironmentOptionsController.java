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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentOptionChangeEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentOptionChangeEventHandler;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.TestEventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentOptionsController implements Initializable,
                                             ControlledScreen {

    ScreensController myController;
    
    @FXML   private AnchorPane ap;
    @FXML   private EnvironmentOptionPanel eop;
    @FXML   private EnvironmentOptionPanel eopBeach;
    @FXML   private Button btnEopClose;
    
    @FXML   private Button button = null;
    
    private EnvironmentElementStatus ees;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
//        ap.addEventHandler(EnvironmentOptionChangeEvent.OPTION_SELECTED, 
//                           new EnvironmentOptionChangeEventHandler("Handled by EOP instance!"));
        
        
        eop.removeUnusedButtons();
        
    }  
    
    /**
     * This function will trigger a data update event for the Disaster Effects
     * and trigger a screen switch event for the main controller.
     * @param event 
     */
    @FXML
    private void doneButtonClicked(ActionEvent event)
    {
        // Fire event for data update
        
        // Now switch the window
        Event.fireEvent((EventTarget) event.getSource(), new ScreenSwitchEvent()); // this should use the custom event to switch windows
        
        // In the following line, the EES panel needs to be targeted, but currently there is now way to do this.
        // Scene.lookup reliably fails on each call and there's not a currently reliable way (given how FXML instantiates)
        // to pass references between instances.
        
//        // ---------------------------------------------------------------------
//        // First, select the ID based on the caller (button)
//        // ---------------------------------------------------------------------
//        EnvironmentElementStatus eesTemp = null;
//                
//        if(((Node)event.getSource()).getScene().getFocusOwner().getParent().equals(eop)) {
////            toSet = DisasterResponseTradeStudy.screenEnvBeachID;
//            eesTemp = ((EnvironmentElementStatus)(((Node)event.getSource()).getScene().lookup("#eesBeach")));
//        }
        
//        (EventTarget) eop
        Event.fireEvent((EventTarget)eop, new EnvironmentOptionChangeEvent(EnvironmentOptionChangeEvent.OPTION_SELECTED));
        this.goToMain(event);
    }
    
    // -------------------------------------------------------------------------
    // These functions are what switch between windows.
    // -------------------------------------------------------------------------
    
    @FXML
    private void goToMain(ActionEvent event)  {
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