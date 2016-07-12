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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class WeightingOptionPanel extends AnchorPane {
    
    @FXML   private Label titleLabel;
    @FXML   private Button btnDone;
    
    // -------------------------------------------------------------------------
    // These exposd the controls that contain the questions and the related 
    // user preferences
    // -------------------------------------------------------------------------
    @FXML   private WeightingOptionQuestion woqc1;
    @FXML   private WeightingOptionQuestion woqc2;
    @FXML   private WeightingOptionQuestion woqc3;
    @FXML   private WeightingOptionQuestion woqc4;
    @FXML   private WeightingOptionQuestion woqc5;
    @FXML   private WeightingOptionQuestion woqc6;
    
    // -------------------------------------------------------------------------
    // These are part of the labels the 5 criteria for weighting
    // -------------------------------------------------------------------------
    @FXML   private TextFlow tfWeightingOption1;
    @FXML   private TextFlow tfWeightingOption2;
    @FXML   private TextFlow tfWeightingOption3;
    @FXML   private TextFlow tfWeightingOption4;
    @FXML   private TextFlow tfWeightingOption5;
    
    private final Text weightingOption1;
    private final Text weightingOption2;
    private final Text weightingOption3;
    private final Text weightingOption4;
    private final Text weightingOption5;
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty title;
    
    
    public WeightingOptionPanel() {
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/GUIToolBox/WeightingOptionPanel.fxml"));

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
        
        System.out.println("The weighting option panel loader got called!!!");
        
        weightingOption1 = new Text("Extremely More Important");
        weightingOption2 = new Text("Significantly More Important");
        weightingOption3 = new Text("Equally Important");
        weightingOption4 = new Text("Significantly Less Important");
        weightingOption5 = new Text("Extremely Less Important");
        
        
        tfWeightingOption1.getChildren().setAll(weightingOption1);
        tfWeightingOption1.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption2.getChildren().setAll(weightingOption2);
        tfWeightingOption2.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption3.getChildren().setAll(weightingOption3);
        tfWeightingOption3.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption4.getChildren().setAll(weightingOption4);
        tfWeightingOption4.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption5.getChildren().setAll(weightingOption5);
        tfWeightingOption5.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        
        
        // Initialize the properties to read from the FXML file
        this.title = new SimpleStringProperty("title");
        
        // bind the properties to the text properties
        titleLabel.textProperty().bind(titleProperty());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-underline: true;");
    }
    
    // -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------
    @FXML
    private void handleDoneButtonAction(ActionEvent event) {
        // Update caller that we've returned
        // TODO: possibly fire an event...could require object registration or
        //       dependency injection.
        
        
        // Turns off the panel.
        this.setVisible(false);
    }
    
    // -------------------------------------------------------------------------
    // Getters and setters
    // Includes some exposed to the FXML file through NetBeans
    // -------------------------------------------------------------------------
    
    public String getSelection()
    {
        // TODO: return all of the selections.
//        return questionSet.getSelectedToggle().getUserData().toString();
        return "";
    }
    
    
    
    public String getTitle() {
        return titleProperty().get();
    }

    public void setTitle(String fName) {
        titleProperty().set(fName);
    }
    
    public SimpleStringProperty titleProperty() {
        return title;
    }
    
    
    @FXML
    void initialize() {

    }   
    
}


