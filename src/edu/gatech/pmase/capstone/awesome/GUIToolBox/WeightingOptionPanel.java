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

import edu.gatech.pmase.capstone.awesome.impl.DisasterResponseTradeStudySingleton;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingCategory;
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
    
    private final Text tWeightingOption1;
    private final Text tWeightingOption2;
    private final Text tWeightingOption3;
    private final Text tWeightingOption4;
    private final Text tWeightingOption5;
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty title;
    
    private final SimpleStringProperty weightingOption1;
    private final SimpleStringProperty weightingOption2;
    private final SimpleStringProperty weightingOption3;
    private final SimpleStringProperty weightingOption4;
    private final SimpleStringProperty weightingOption5;
    
    private final DisasterResponseTradeStudySingleton DRTSS;
    
    
    
    public WeightingOptionPanel() {
        DRTSS = DisasterResponseTradeStudySingleton.getInstance();
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/WeightingOptionPanel.fxml"));

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
        
        
        tfWeightingOption1.getChildren().setAll(tWeightingOption1);
//        tfWeightingOption1.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption2.getChildren().setAll(tWeightingOption2);
//        tfWeightingOption2.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption3.getChildren().setAll(tWeightingOption3);
//        tfWeightingOption3.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption4.getChildren().setAll(tWeightingOption4);
//        tfWeightingOption4.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption5.getChildren().setAll(tWeightingOption5);
//        tfWeightingOption5.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        
        
        // Initialize the properties to read from the FXML file
        this.title = new SimpleStringProperty("title");
        
        this.weightingOption1 = new SimpleStringProperty("Weighting Option 1");
        this.weightingOption2 = new SimpleStringProperty("Weighting Option 2");
        this.weightingOption3 = new SimpleStringProperty("Weighting Option 3");
        this.weightingOption4 = new SimpleStringProperty("Weighting Option 4");
        this.weightingOption5 = new SimpleStringProperty("Weighting Option 5");
        
        
        // bind the properties to the text properties
        titleLabel.textProperty().bind(titleProperty());
        tWeightingOption1.textProperty().bind(weightingOption1Property());
        tWeightingOption2.textProperty().bind(weightingOption2Property());
        tWeightingOption3.textProperty().bind(weightingOption3Property());
        tWeightingOption4.textProperty().bind(weightingOption4Property());
        tWeightingOption5.textProperty().bind(weightingOption5Property());
        
        
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
    
    

    
    
    
    // -------------------------------------------------------------------------
    // WeightingOption 1
    // -------------------------------------------------------------------------
    public String getWeightingOption1() {
        return weightingOption1Property().get();
    }

    public void setWeightingOption1(String fName) {
        weightingOption1Property().set(fName);
    }
    
    public SimpleStringProperty weightingOption1Property() {
        return weightingOption1;
    }
    
    // -------------------------------------------------------------------------
    // WeightingOption 2
    // -------------------------------------------------------------------------
    public String getWeightingOption2() {
        return weightingOption2Property().get();
    }

    public void setWeightingOption2(String fName) {
        weightingOption2Property().set(fName);
    }
    
    public SimpleStringProperty weightingOption2Property() {
        return weightingOption2;
    }
    
    // -------------------------------------------------------------------------
    // WeightingOption 3
    // -------------------------------------------------------------------------
    public String getWeightingOption3() {
        return weightingOption3Property().get();
    }

    public void setWeightingOption3(String fName) {
        weightingOption3Property().set(fName);
    }
    
    public SimpleStringProperty weightingOption3Property() {
        return weightingOption3;
    }
    
    // -------------------------------------------------------------------------
    // WeightingOption 4
    // -------------------------------------------------------------------------
    public String getWeightingOption4() {
        return weightingOption4Property().get();
    }

    public void setWeightingOption4(String fName) {
        weightingOption4Property().set(fName);
    }
    
    public SimpleStringProperty weightingOption4Property() {
        return weightingOption4;
    }
    
    // -------------------------------------------------------------------------
    // WeightingOption 5
    // -------------------------------------------------------------------------
    public String getWeightingOption5() {
        return weightingOption5Property().get();
    }

    public void setWeightingOption5(String fName) {
        weightingOption5Property().set(fName);
    }
    
    public SimpleStringProperty weightingOption5Property() {
        return weightingOption5;
    }
    
    
    @FXML
    void initialize() {

    }   
    
}


