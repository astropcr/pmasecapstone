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

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentOptionPanel extends AnchorPane {
    @FXML    private Label questionLabel;
    @FXML    private Button btnDone;
    
    // -------------------------------------------------------------------------
    // These expose the controls that contain the questions and the related 
    // user preferences
    // -------------------------------------------------------------------------    
    
    // These are part of the labels the 5 criteria for weighting 
    @FXML   private ToggleButton tbEnvironmentOption1;
    @FXML   private ToggleButton tbEnvironmentOption2;
    @FXML   private ToggleButton tbEnvironmentOption3;
    @FXML   private ToggleButton tbEnvironmentOption4;
    @FXML   private ToggleButton tbEnvironmentOption5;

    @FXML   private ToggleGroup tgEnvironmentOptions;
    
    // -------------------------------------------------------------------------
    // These values control what the user selection 'is'.  It should be mapped
    // to some kind of value systems such "Least", "Less", "Equal", "More", "Most"
    // -------------------------------------------------------------------------
    private final Integer TB_VAL_1 = 1;
    private final Integer TB_VAL_2 = 2;
    private final Integer TB_VAL_3 = 3;
    private final Integer TB_VAL_4 = 4;
    private final Integer TB_VAL_5 = 5;
    
    private final String TB_DEFAULT_VAL = "Environment Option";
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty question;
    private final SimpleStringProperty envOpt1;
    private final SimpleStringProperty envOpt2;
    private final SimpleStringProperty envOpt3;
    private final SimpleStringProperty envOpt4;
    private final SimpleStringProperty envOpt5;
    
    
    
    public EnvironmentOptionPanel() {
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/EnvironmentOptionPanel.fxml"));

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
             
        // Set the styles
        tbEnvironmentOption1.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        tbEnvironmentOption2.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        tbEnvironmentOption3.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        tbEnvironmentOption4.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        tbEnvironmentOption5.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        
        // Set values for which one was selected.
        tbEnvironmentOption1.setUserData(TB_VAL_1);
        tbEnvironmentOption2.setUserData(TB_VAL_1);
        tbEnvironmentOption3.setUserData(TB_VAL_1);
        tbEnvironmentOption4.setUserData(TB_VAL_1);
        tbEnvironmentOption5.setUserData(TB_VAL_1);
        
        // Initialize the properties to read from the FXML file
        this.question = new SimpleStringProperty("question");
        this.envOpt1 = new SimpleStringProperty(TB_DEFAULT_VAL + " 1");
        this.envOpt2 = new SimpleStringProperty(TB_DEFAULT_VAL + " 2");
        this.envOpt3 = new SimpleStringProperty(TB_DEFAULT_VAL + " 3");
        this.envOpt4 = new SimpleStringProperty(TB_DEFAULT_VAL + " 4");
        this.envOpt5 = new SimpleStringProperty(TB_DEFAULT_VAL + " 5");
        
        // bind the XML properties to the text properties
        questionLabel.textProperty().bind(questionProperty());
        questionLabel.setStyle("-fx-font-weight: bold;");
        tbEnvironmentOption1.textProperty().bind(envOpt1Property());
        tbEnvironmentOption2.textProperty().bind(envOpt2Property());
        tbEnvironmentOption3.textProperty().bind(envOpt3Property());
        tbEnvironmentOption4.textProperty().bind(envOpt4Property());
        tbEnvironmentOption5.textProperty().bind(envOpt5Property());
    }
    
    /**
     * This function will remove the unused buttons from the GUI and keep it from
     * accidentally being included.  This should be 'future proof' from changing
     * the number of buttons as longs as they're added to the ToggleGroup
     */
    public void removeUnusedButtons()
    {
        tgEnvironmentOptions.getToggles().forEach((toggle) -> {
            if(((ToggleButton)(toggle)).getText().contains(TB_DEFAULT_VAL))
            {
               ((ToggleButton)(toggle)).setVisible(false);                      // this should probably remove the offending value.  Time considerations prevented this.
            }
        });
    }
    
    
    // -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------
    @FXML
    private void handleOptionSelected(ActionEvent event) {
        Event.fireEvent((EventTarget) event.getSource(), new EnvironmentOptionChangeEvent(EnvironmentOptionChangeEvent.OPTION_SELECTED));
        Event.fireEvent((EventTarget) event.getSource(), new ScreenSwitchEvent(ScreenSwitchEvent.SCREEN_SELECTED)); // TODO: eventually figure out how to switch windows
    }
    

    
    // -------------------------------------------------------------------------
    // Getters and setters
    // Includes some exposed to the FXML file through NetBeans
    // -------------------------------------------------------------------------
    
    /**
     * This method returns the selected value as defined by TB_VAL_x enumeration
     * @return String
     */
    public Integer getSelection()
    {
        // TODO: return all of the selections.
        return (Integer) tgEnvironmentOptions.getSelectedToggle().getUserData();
    }
    
    
    
    public String getQuestion() {
        return questionProperty().get();
    }

    public void setQuestion(String fName) {
        questionProperty().set(fName);
    }
    
    public SimpleStringProperty questionProperty() {
        return question;
    }
    
    
    
    public String getEnvOpt1() {
        return envOpt1Property().get();
    }

    public void setEnvOpt1(String fName) {
        envOpt1Property().set(fName);
    }
    
    public SimpleStringProperty envOpt1Property() {
        return envOpt1;
    }
    
    // Environment Option 2
    public String getEnvOpt2() {
        return envOpt2Property().get();
    }

    public void setEnvOpt2(String fName) {
        envOpt2Property().set(fName);
    }
    
    public SimpleStringProperty envOpt2Property() {
        return envOpt2;
    }
    
    // Environment Option 3
    public String getEnvOpt3() {
        return envOpt3Property().get();
    }

    public void setEnvOpt3(String fName) {
        envOpt3Property().set(fName);
    }
    
    public SimpleStringProperty envOpt3Property() {
        return envOpt3;
    }
    
    // Environment Option 4
    public String getEnvOpt4() {
        return envOpt4Property().get();
    }

    public void setEnvOpt4(String fName) {
        envOpt4Property().set(fName);
    }
    
    public SimpleStringProperty envOpt4Property() {
        return envOpt4;
    }
    
    // Environment Option 5
    public String getEnvOpt5() {
        return envOpt5Property().get();
    }

    public void setEnvOpt5(String fName) {
        envOpt5Property().set(fName);
    }
    
    public SimpleStringProperty envOpt5Property() {
        return envOpt5;
    }
    
    
    
    @FXML
    void initialize() {
        // check for empty options and remove them from view and make sure they're never selected
    }   
    
}


