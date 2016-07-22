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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class DisasterEffectOptionsController implements ControlledScreen {

    ScreensController myController;
    
    @FXML   private EffectsOptionsPanel eopDisasterEffects;
    @FXML   private Label lblDisasterEffects;
    @FXML   private Button btnDepClose;
    
    @FXML   private Button button = null;
    
    
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO  
//    }
   
    
    public void initialize()
    {
        this.eopDisasterEffects.setQuestion("Select all disaster effects that apply.");
    }
    
    /**
     * This function will trigger a data update event for the Disaster Effects
     * and trigger a screen switch event for the main controller.
     * @param event 
     */
    @FXML
    private void doneButtonClicked(ActionEvent event)
    {
        // Update the model and the view
        DisasterResponseTradeStudySingleton.getInstance()
                                           .setSelectedDisasterEffects(
                                                   eopDisasterEffects.getSelectionList()
                                           );
        
        DRTSGUIModel.getInstance()
                    .updateDisasterEffectsStatus(
                            eopDisasterEffects.getSelectionStringized()
                    );
        
        
        // Now switch the window
        goToMain(event);
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
