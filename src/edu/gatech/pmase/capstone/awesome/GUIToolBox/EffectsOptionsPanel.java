/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.pmase.capstone.awesome.GUIToolBox;

import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
public class EffectsOptionsPanel extends AnchorPane {

    @FXML   private Label titleLabel;
    @FXML   private BorderPane bpMain;
    //@FXML   private VBox vbChoices;
    @FXML   private ListView<DisasterEffect> lvChoices;
    @FXML   private Button btnDone;
    
    // -------------------------------------------------------------------------
    // These expose the controls that contain the questions and the related 
    // user preferences
    // -------------------------------------------------------------------------    
    
    // None, they are generated automatically by code below.  This control
    // consists of a ListView containing Check Boxen.
    
    
    // -------------------------------------------------------------------------
    // List of Effects the user can choose.  Also modified by setDisasterEffects
    // -------------------------------------------------------------------------
    private final ObservableList<DisasterEffect> disasterEffects = FXCollections.observableArrayList(); 
    
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty question;
    
    
    public EffectsOptionsPanel() {
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/GUIToolBox/EffectsOptionsPanel.fxml"));

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
    
    
    // ---------------------------------------------------------------------
    // A utility procedure that creates the disaster effects list.
    // ---------------------------------------------------------------------
    private void createTheDisasterEffectList()
    {   
        disasterEffects.add(new DisasterEffect("Flood", false));
        disasterEffects.add(new DisasterEffect("Debris", false));
        disasterEffects.add(new DisasterEffect("Smoke/Dush/Ash (airborne)", false));
        disasterEffects.add(new DisasterEffect("Ground Instability", false));
        disasterEffects.add(new DisasterEffect("Land Slide", false));
        disasterEffects.add(new DisasterEffect("Mud Slide", false));
        disasterEffects.add(new DisasterEffect("Structural", false));
        disasterEffects.add(new DisasterEffect("High Wind (near term)", false));
        disasterEffects.add(new DisasterEffect("Hazardous Material Spill", false));
        disasterEffects.add(new DisasterEffect("Radiological Spill", false));
        disasterEffects.add(new DisasterEffect("Lava", false));
        disasterEffects.add(new DisasterEffect("Ash <---- does this mean something different than airborne???", false));
    }
    
    
    // -------------------------------------------------------------------------
    // Getters and setters
    // Includes some exposed to the FXML file through NetBeans
    // -------------------------------------------------------------------------
    
    public ObservableList<DisasterEffect> getSelection()
    {
        return disasterEffects;
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

