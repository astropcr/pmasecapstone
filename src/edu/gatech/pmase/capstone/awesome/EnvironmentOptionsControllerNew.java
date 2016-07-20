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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreenSwitchEvent;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class EnvironmentOptionsControllerNew implements Initializable,
                                             ControlledScreen {

    ScreensController myController;
    
    @FXML   private AnchorPane  ap;
    @FXML   private ToggleGroup tg;
    @FXML   private Button      btnEopClose;
    @FXML   private ListView    environmentOptions;
    
    @FXML   private Button button = null;
    
    private ObservableList      tempObsList;
    
//    private final SimpleStringProperty Name;
    
    
    
    public EnvironmentOptionsControllerNew() {
        ;
    }
    
    
    void setupEnvOpts(String envOpt){
        List<TerrainEffect> envOptList = TerrainEffect.getEffectByLabel(envOpt);

        tempObsList.addAll(envOptList);
        
        environmentOptions.setItems(tempObsList);
        
        environmentOptions.setCellFactory(
            new Callback<ListView<TerrainEffect>, javafx.scene.control.ListCell<TerrainEffect>>() {
                @Override
                public ListCell<TerrainEffect> call(ListView<TerrainEffect> environmentOptions) {
                   return new EnvOptCell();
                }
        });
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
//        ap.addEventHandler(EnvironmentOptionChangeEvent.OPTION_SELECTED, 
//                           new EnvironmentOptionChangeEventHandler("Handled by EOP instance!"));
        
        
//        eop.removeUnusedButtons();
//        eop.connectToModel();
        
    }  
    
    @FXML
    void initialize()
    {
        ;
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
//        Event.fireEvent((EventTarget)eop, new EnvironmentOptionChangeEvent(EnvironmentOptionChangeEvent.OPTION_SELECTED));
        
        // Now switch the window
        Event.fireEvent((EventTarget) event.getSource(), new ScreenSwitchEvent()); // this should use the custom event to switch windows

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
