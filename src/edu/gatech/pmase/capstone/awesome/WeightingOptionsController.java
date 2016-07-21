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
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class WeightingOptionsController implements ControlledScreen {

    ScreensController myController;
    
    @FXML   private WeightingOptionPanel    wop; // this is going to be broken apart
    @FXML   private ToggleGroup             tg;
    @FXML   private Button                  btnWopClose;
    @FXML   private ListView                weightingOptions;
    @FXML   private Label                   titleLabel;
    
    // -------------------------------------------------------------------------
    // These are part of the labels the 5 criteria for weighting
    // -------------------------------------------------------------------------
    @FXML   private TextFlow tfWeightingOption1;
    @FXML   private TextFlow tfWeightingOption2;
    @FXML   private TextFlow tfWeightingOption3;
    @FXML   private TextFlow tfWeightingOption4;
    @FXML   private TextFlow tfWeightingOption5;
    
    private final Text tWeightingOption1;
    private final Text tWeightingOption2;
    private final Text tWeightingOption3;
    private final Text tWeightingOption4;
    private final Text tWeightingOption5;
    
    private ObservableList<WeightingChoice>   tempObsList;
    
    private WeightingAreasOfConcern weightingOpt = WeightingAreasOfConcern.UNKNOWN;
    private DisasterResponseTradeStudySingleton DRTSS;
    
    public WeightingOptionsController(){
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
    
    
    void setupEnvOpts(WeightingAreasOfConcern weightingOpt){
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
        // Set the title label.
        // ---------------------------------------------------------------------
        titleLabel.setText(weightingOpt.label);
        
        // ---------------------------------------------------------------------
        // Create the choices
        // ---------------------------------------------------------------------
        List<WeightingChoice> weightingOptList = new ArrayList<>();
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
