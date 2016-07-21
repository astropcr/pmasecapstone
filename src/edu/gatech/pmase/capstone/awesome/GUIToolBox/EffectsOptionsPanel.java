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
package edu.gatech.pmase.capstone.awesome.GUIToolBox;

import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
//public class EffectsOptionsPanel extends AnchorPane implements ControlledScreen {
public class EffectsOptionsPanel extends AnchorPane {

    @FXML   private Label titleLabel;
    @FXML   private BorderPane bpMain;
    @FXML   private ListView<DisasterEffectCheckBoxData> lvChoices;
    
    @FXML   private Button btnDone;
    
    private ScreensController myController;
    
    // -------------------------------------------------------------------------
    // These expose the controls that contain the questions and the related 
    // user preferences
    // -------------------------------------------------------------------------    
    
    // None, they are generated automatically by code below.  This control
    // consists of a ListView containing Check Boxen.
    
    
    // -------------------------------------------------------------------------
    // List of Effects the user can choose.
    // -------------------------------------------------------------------------
    private final ObservableList<DisasterEffectCheckBoxData> disasterEffects = FXCollections.observableArrayList();
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty question;
    
    
    public EffectsOptionsPanel() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/EffectsOptionsPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        // do not remove the following line if you're working with Scene Builder 2.0.  
        // This fixes a known bug.
        // See http://stackoverflow.com/questions/24016229/cant-import-custom-components-with-custom-cell-factories
        fxmlLoader.setClassLoader(getClass().getClassLoader()); 

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        // Initialize the control allowing the user to select the effects that apply
        createTheDisasterEffectList();
        lvChoices.setCellFactory(CheckBoxListCell.forListView(item -> item.onProperty()));
        lvChoices.setItems(disasterEffects);
        
        // Initialize the properties to read from the FXML file
        this.question = new SimpleStringProperty("title");
        
        // bind the properties to the text properties
        titleLabel.textProperty().bind(questionProperty());
        
    }
    
    // -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------
    
    @FXML
    private void handleOptionButtonAction(ActionEvent event) {
        ; // currently a place holder for possible future events
    }
    
    
    // ---------------------------------------------------------------------
    // A utility procedure that creates the disaster effects list.
    // ---------------------------------------------------------------------
    private void createTheDisasterEffectList()
    {
        
        for (DisasterEffect de : DisasterEffect.values())
        {
            if(!de.equals(DisasterEffect.UNKNOWN)) {
                disasterEffects.add(new DisasterEffectCheckBoxData(de.label, false));
            }
        }
    }
    
    
    // -------------------------------------------------------------------------
    // Getters and setters
    // Includes some exposed to the FXML file through NetBeans
    // -------------------------------------------------------------------------
    
    public ObservableList<DisasterEffectCheckBoxData> getSelection()
    {   
        return disasterEffects;
    }

    public List<DisasterEffect> getSelectionList()
    {
        List<DisasterEffect> selectedDisasterEffects = FXCollections.observableArrayList();

        for (DisasterEffectCheckBoxData decbd : disasterEffects)
        {
            if(decbd.isOn()) {
                selectedDisasterEffects.add(
                    DisasterEffect.getEffectByLabel(decbd.getName())
                );
            }
        }
        
        return selectedDisasterEffects;
    }
    
    /**
     * Function will return a comma + newline separated string representation of
     * those items selected by the user.
     * @return 
     */
    public String getSelectionStringized()
    {
        String temp = "";
        for (DisasterEffectCheckBoxData decbd : disasterEffects)
        {
            if(decbd.isOn()) { temp += decbd.getName() + ",\n"; }
        }
        
        // remove trailing ",\n"
        if(temp.endsWith(",\n"))
        {
            temp = temp.substring(0, temp.length()-2);
        }
        return temp;
    }
    
    // -------------------------------------------------------------------------
    // Sets the title/question statement directiong the user's interaction.
    // -------------------------------------------------------------------------
    public String getQuestion() {
        return questionProperty().get();
    }

    public void setQuestion(String fName) {
        questionProperty().set(fName);
    }
    
    public SimpleStringProperty questionProperty() {
        return question;
    }
    
    
    @FXML
    void initialize() {

    }      
}

