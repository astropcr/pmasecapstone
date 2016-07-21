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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightOptCell;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingCategory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class WeightingOptionsController implements ControlledScreen {

    ScreensController myController;
    
    @FXML   private WeightingOptionPanel wop; // this is going to be broken apart
    @FXML   private ToggleGroup     tg;
    @FXML   private Button btnWopClose;
    @FXML   private ListView        weightingOptions;
    
    @FXML   private Button button = null;
    
    
    private ObservableList<WeightingChoice>   tempObsList;
    
    private WeightingCategory weightingOpt = WeightingCategory.UNKNOWN;
    private DisasterResponseTradeStudySingleton DRTSS;
    
    public WeightingOptionsController(){
        tempObsList = FXCollections.observableArrayList();
        
        DRTSS = DisasterResponseTradeStudySingleton.getInstance();
    }
    
    
    void setupEnvOpts(WeightingCategory weightingOpt){
        List<WeightingChoice> weightingOptList = new ArrayList<>();
                
        this.weightingOpt = weightingOpt;
        
        switch(weightingOpt)
        {
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
        
//        questionLabel.textProperty().setValue(envOpt + STR_QUESTION);
//        questionLabel.getStyleClass().add("questionOnPanel");

        tempObsList.addAll(weightingOptList);
        weightingOptions.setItems(tempObsList);
        
        weightingOptions.setCellFactory(
            new Callback<ListView<WeightingChoice>, ListCell<WeightingChoice>>() {
                @Override
                public ListCell<WeightingChoice> call(ListView<WeightingChoice> weightingOptions) {
                   return new WeightOptCell(tg);
                }
        });
    }
    
    /**
     * Initializes the controller class.
     */
    @FXML
    void initialize() {
        // TODO  
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
