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
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */

public class WeightingOptionQuestion extends AnchorPane {

    // -------------------------------------------------------------------------
    // These expose the controls to the FXML Loader and are used for the user
    // to select thier preferences.
    // -------------------------------------------------------------------------
    @FXML    ToggleGroup questionSet;
    @FXML    RadioButton rb1;
    @FXML    RadioButton rb2;
    @FXML    RadioButton rb3;
    @FXML    RadioButton rb4;
    @FXML    RadioButton rb5;
    @FXML    TextFlow tfQuestion;
    
    // -------------------------------------------------------------------------
    // These values control what the user selection 'is'.  It should be mapped
    // to some kind of value systems such "Least", "Less", "Equal", "More", "Most"
    // -------------------------------------------------------------------------
    private final int RB_VAL_1 = 1;
    private final int RB_VAL_2 = 2;
    private final int RB_VAL_3 = 3;
    private final int RB_VAL_4 = 4;
    private final int RB_VAL_5 = 5;
    
    // -------------------------------------------------------------------------
    // These variables ared used to build the question the user is being asked.
    // The template is: Is option2 more important than option2?
    // These are filled in with the setComparisonOptions() method.
    // -------------------------------------------------------------------------
    private final Text text1;
    private final Text textOpt1;
    private final Text text2;
    private final Text textOpt2;
    private final Text text3;
       
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    private final SimpleStringProperty optionOne;
    private final SimpleStringProperty optionTwo;
   
    
    public WeightingOptionQuestion() {
        
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/WeightingOptionQuestion.fxml"));

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
        
        // Setup the toggle group that handles the radio buttons
        questionSet = new ToggleGroup();
        questionSet.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (questionSet.getSelectedToggle() != null) {
                System.out.println(questionSet.getSelectedToggle().getUserData().toString());
                // Do something here with the userData of newly selected radioButton
            } 
        });

        // Setup the radio buttons
        rb1.setUserData(RB_VAL_1); rb1.setToggleGroup(questionSet);
        rb2.setUserData(RB_VAL_2); rb2.setToggleGroup(questionSet);
        rb3.setUserData(RB_VAL_3); rb3.setToggleGroup(questionSet);
        rb4.setUserData(RB_VAL_4); rb4.setToggleGroup(questionSet);
        rb5.setUserData(RB_VAL_5); rb5.setToggleGroup(questionSet);
        
        
        // ---------------------------------------------------------------------
        // Now let's set up the question
        // The template is: Is option2 more important than option2?
        // ---------------------------------------------------------------------
        
        // Initialize the properties to read from the FXML file
        this.optionOne = new SimpleStringProperty("option1");
        this.optionTwo = new SimpleStringProperty("option2");
        
        // Now set up the internal variables to build the question in the TextFlow
        text1    = new Text("Is ");
        textOpt1 = new Text(this.getOptionOne()); textOpt1.setStyle("-fx-font-weight: bold");
        text2    = new Text(" more important than ");
        textOpt2 = new Text(this.getOptionTwo()); textOpt2.setStyle("-fx-font-weight: bold");
        text3    = new Text("?");
        
        // bind the properties to the text properties
        textOpt1.textProperty().bind(optionOneProperty());
        textOpt2.textProperty().bind(optionTwoProperty());
        
        this.tfQuestion.getChildren().setAll(text1, textOpt1, text2, textOpt2, text3);
    }
    
    // -------------------------------------------------------------------------
    // Getters and setters
    // Includes some exposed to the FXML file through NetBeans
    // -------------------------------------------------------------------------
    
    public String getSelection()
    {
        return questionSet.getSelectedToggle().getUserData().toString();
    }
    
    public void setQuestion(ObservableList<Text>questionText)
    {
        // TODO: allow for formatted text
        this.tfQuestion.getChildren().setAll(questionText);
    }
    
    public void setComparisonOptions(String opt1, String opt2)
    {
        this.setOptionOne(opt1);
        this.setOptionTwo(opt2);
    }
    
    
    public String getOptionOne() {
        return optionOneProperty().get();
    }

    public void setOptionOne(String fName) {
        optionOneProperty().set(fName);
    }
    
    public SimpleStringProperty optionOneProperty() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo.get();
    }

    public void setOptionTwo(String fName) {
        optionTwo.set(fName);
    }
    
    public SimpleStringProperty optionTwoProperty() {
        return optionTwo;
    }
    
    

    
    @FXML
    void initialize() {

    }

}

