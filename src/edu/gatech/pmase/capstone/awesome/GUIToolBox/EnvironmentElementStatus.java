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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class - EnvironmentElementStatus
 * 
 * This class / controller is meant to provide the user a way of opening an
 * Environment Option Panel selection tool and then reflect their selection
 * choice once it has been made.
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentElementStatus extends AnchorPane {

    @FXML    private Label lblEnvOptWeight;
    @FXML    private Button btnEnvOpt;
    
    // -------------------------------------------------------------------------
    // These expose the controls that contain the questions and the related 
    // user preferences
    // -------------------------------------------------------------------------    
    
    // None needed
    
    // -------------------------------------------------------------------------
    // These values control what the user selection 'is'.  It should be mapped
    // to some kind of value systems such "Least", "Less", "Equal", "More", "Most"
    // -------------------------------------------------------------------------
    // None needed
    
    
    // -------------------------------------------------------------------------
    // These are what set the properties from the FXML file.  They should be
    // be set there.
    // -------------------------------------------------------------------------
    private final SimpleStringProperty environmentOption;
    private final SimpleStringProperty environmentOptionPanel;
    
    private EnvironmentOptionPanel eop;
    
    
    public EnvironmentElementStatus() {
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/EnvironmentElementStatus.fxml"));

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
        lblEnvOptWeight.setStyle("-fx-font-weight: bold; -fx-underline: true;");
        
//        tbEnvironmentOption1.setStyle("-fx-alignment: BASELINE_LEFT; -fx-font: 20px \"Agency FB Bold\"; -fx-font-weight: bold;");
        
        // Initialize the properties to read from the FXML file
        this.environmentOption      = new SimpleStringProperty("Effect");
        this.environmentOptionPanel = new SimpleStringProperty("");
        
        // bind the XML properties to the text properties
        btnEnvOpt.textProperty().bind(environmentOptionProperty());
        btnEnvOpt.textProperty().bind(environmentOptionPanelProperty());
        
        eop = null;
        
    }
    
    
// -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------
    @FXML
    private void handleOptionButtonAction(ActionEvent event) {
        // Update caller that we've returned
        // TODO: possibly fire an event...could require object registration or
        //       dependency injection.
        
        
        // Turns off the panel.
        //this.setVisible(false);
        // TODO: trick main panel into opening up the necessary window
        //       possibly fire an event...could require object registration or
        //       dependency injection.
        
        try {
            eop.setVisible(true);
        } catch (RuntimeException exception) {
            System.out.println("The eop is not valid!!");
        }
        
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
        // TODO: return the selection?
        return (Integer) 0;
    }
    // -------------------------------------------------------------------------
    // This stores an EOP node based on it's given name
    // -------------------------------------------------------------------------
    public void setDisasterEffectPanel(String eopName)
    {
        Scene scene = this.getScene();

        try {
            eop = (EnvironmentOptionPanel) scene.lookup("#"+eopName);
        } catch (RuntimeException exception) {
            System.out.println("The eop specific '" + eopName +"' was not found.");
        }
    }
    
    // -------------------------------------------------------------------------
    // This property sets the name on the button
    // -------------------------------------------------------------------------
    public String getEnvironmentOption() {
        return environmentOptionProperty().get();
    }

    public void setEnvironmentOption(String fName) {
        environmentOptionProperty().set(fName);
    }
    
    public SimpleStringProperty environmentOptionProperty() {
        return environmentOption;
    }
    
    // -------------------------------------------------------------------------
    // This property informs this controller of related Envrionment Option
    // Panel that it will open and bind to.
    // -------------------------------------------------------------------------
    public String getEnvironmentOptionPanel() {
        return environmentOptionPanelProperty().get();
    }

    public void setEnvironmentOptionPanel(String fName) {
        environmentOptionPanelProperty().set(fName);
    }
    
    public SimpleStringProperty environmentOptionPanelProperty() {
        return environmentOptionPanel;
    }
    
    
    @FXML
    void initialize() {
        // check for empty options and remove them from view and make sure they're never selected
        System.out.print("We're in the initialization stage.");
    }     
    
}
