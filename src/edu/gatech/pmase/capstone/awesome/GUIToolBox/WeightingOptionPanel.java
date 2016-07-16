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
    private final SimpleStringProperty question1Opt1;
    private final SimpleStringProperty question1Opt2;
    private final SimpleStringProperty question2Opt1;
    private final SimpleStringProperty question2Opt2;
    private final SimpleStringProperty question3Opt1;
    private final SimpleStringProperty question3Opt2;
    private final SimpleStringProperty question4Opt1;
    private final SimpleStringProperty question4Opt2;
    private final SimpleStringProperty question5Opt1;
    private final SimpleStringProperty question5Opt2;
    private final SimpleStringProperty question6Opt1;
    private final SimpleStringProperty question6Opt2;
    
    private final SimpleStringProperty weightingOption1;
    private final SimpleStringProperty weightingOption2;
    private final SimpleStringProperty weightingOption3;
    private final SimpleStringProperty weightingOption4;
    private final SimpleStringProperty weightingOption5;
    
    
    
    public WeightingOptionPanel() {
        
        
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
        
        tWeightingOption1 = new Text("Rating 1");
        tWeightingOption2 = new Text("Rating 2");
        tWeightingOption3 = new Text("Rating 3");
        tWeightingOption4 = new Text("Rating 4");
        tWeightingOption5 = new Text("Rating 5");
        
        
        tfWeightingOption1.getChildren().setAll(tWeightingOption1);
        tfWeightingOption1.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption2.getChildren().setAll(tWeightingOption2);
        tfWeightingOption2.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption3.getChildren().setAll(tWeightingOption3);
        tfWeightingOption3.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption4.getChildren().setAll(tWeightingOption4);
        tfWeightingOption4.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        tfWeightingOption5.getChildren().setAll(tWeightingOption5);
        tfWeightingOption5.setStyle("-fx-text-alignment: center; -fx-font-weight: bold;");
        
        
        // Initialize the properties to read from the FXML file
        this.title = new SimpleStringProperty("title");
        
        this.question1Opt1 = new SimpleStringProperty("Option 1");
        this.question1Opt2 = new SimpleStringProperty("Option 2");
        this.question2Opt1 = new SimpleStringProperty("Option 1");
        this.question2Opt2 = new SimpleStringProperty("Option 2");
        this.question3Opt1 = new SimpleStringProperty("Option 1");
        this.question3Opt2 = new SimpleStringProperty("Option 2");
        this.question4Opt1 = new SimpleStringProperty("Option 1");
        this.question4Opt2 = new SimpleStringProperty("Option 2");
        this.question5Opt1 = new SimpleStringProperty("Option 1");
        this.question5Opt2 = new SimpleStringProperty("Option 2");
        this.question6Opt1 = new SimpleStringProperty("Option 1");
        this.question6Opt2 = new SimpleStringProperty("Option 2");
        
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
        woqc1.optionOneProperty().bind(question1Opt1Property());
        woqc1.optionTwoProperty().bind(question1Opt2Property());
        woqc2.optionOneProperty().bind(question2Opt1Property());
        woqc2.optionTwoProperty().bind(question2Opt2Property());
        woqc3.optionOneProperty().bind(question3Opt1Property());
        woqc3.optionTwoProperty().bind(question3Opt2Property());
        woqc4.optionOneProperty().bind(question4Opt1Property());
        woqc4.optionTwoProperty().bind(question4Opt2Property());
        woqc5.optionOneProperty().bind(question5Opt1Property());
        woqc5.optionTwoProperty().bind(question5Opt2Property());
        woqc6.optionOneProperty().bind(question6Opt1Property());
        woqc6.optionTwoProperty().bind(question6Opt2Property());
        
        
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
    // Question 1
    // -------------------------------------------------------------------------
    public String getQuestion1Opt1() {
        return question1Opt1Property().get();
    }

    public void setQuestion1Opt1(String fName) {
        question1Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question1Opt1Property() {
        return question1Opt1;
    }
    
    public String getQuestion1Opt2() {
        return question1Opt2Property().get();
    }

    public void setQuestion1Opt2(String fName) {
        question1Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question1Opt2Property() {
        return question1Opt2;
    }
    
    // -------------------------------------------------------------------------
    // Question 2
    // -------------------------------------------------------------------------
    public String getQuestion2Opt1() {
        return question2Opt1Property().get();
    }

    public void setQuestion2Opt1(String fName) {
        question2Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question2Opt1Property() {
        return question2Opt1;
    }
    
    public String getQuestion2Opt2() {
        return question2Opt2Property().get();
    }

    public void setQuestion2Opt2(String fName) {
        question2Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question2Opt2Property() {
        return question2Opt2;
    }
    
    // -------------------------------------------------------------------------
    // Question 3
    // -------------------------------------------------------------------------
    public String getQuestion3Opt1() {
        return question3Opt1Property().get();
    }

    public void setQuestion3Opt1(String fName) {
        question3Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question3Opt1Property() {
        return question3Opt1;
    }
    
    public String getQuestion3Opt2() {
        return question3Opt2Property().get();
    }

    public void setQuestion3Opt2(String fName) {
        question3Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question3Opt2Property() {
        return question3Opt2;
    }
    
    // -------------------------------------------------------------------------
    // Question 4
    // -------------------------------------------------------------------------
    public String getQuestion4Opt1() {
        return question4Opt1Property().get();
    }

    public void setQuestion4Opt1(String fName) {
        question4Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question4Opt1Property() {
        return question4Opt1;
    }
    
    public String getQuestion4Opt2() {
        return question4Opt2Property().get();
    }

    public void setQuestion4Opt2(String fName) {
        question4Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question4Opt2Property() {
        return question4Opt2;
    }
    
    // -------------------------------------------------------------------------
    // Question 5
    // -------------------------------------------------------------------------
    public String getQuestion5Opt1() {
        return question5Opt1Property().get();
    }

    public void setQuestion5Opt1(String fName) {
        question5Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question5Opt1Property() {
        return question5Opt1;
    }
    
    public String getQuestion5Opt2() {
        return question5Opt2Property().get();
    }

    public void setQuestion5Opt2(String fName) {
        question5Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question5Opt2Property() {
        return question5Opt2;
    }
    
    // -------------------------------------------------------------------------
    // Question 6
    // -------------------------------------------------------------------------
    public String getQuestion6Opt1() {
        return question6Opt1Property().get();
    }

    public void setQuestion6Opt1(String fName) {
        question6Opt1Property().set(fName);
    }
    
    public SimpleStringProperty question6Opt1Property() {
        return question6Opt1;
    }
    
    public String getQuestion6Opt2() {
        return question6Opt2Property().get();
    }

    public void setQuestion6Opt2(String fName) {
        question6Opt2Property().set(fName);
    }
    
    public SimpleStringProperty question6Opt2Property() {
        return question6Opt2;
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


