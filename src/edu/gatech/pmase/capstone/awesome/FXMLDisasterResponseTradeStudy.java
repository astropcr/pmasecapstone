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

import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionQuestion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class FXMLDisasterResponseTradeStudy implements Initializable {

    
    @FXML    private Label label;    
    @FXML    private Label title;
    
    @FXML    private WeightingOptionQuestion woqc1;    
    @FXML    private WeightingOptionQuestion woqc2;    
    @FXML    private WeightingOptionQuestion woqc3;    
    @FXML    private WeightingOptionQuestion woqc4;    
    @FXML    private WeightingOptionQuestion woqc5;    
    @FXML    private WeightingOptionQuestion woqc6;
    
    
    @FXML    private TextFlow tfWeightingOption1;    
    @FXML    private TextFlow tfWeightingOption2;    
    @FXML    private TextFlow tfWeightingOption3;    
    @FXML    private TextFlow tfWeightingOption4;    
    @FXML    private TextFlow tfWeightingOption5;
    
    @FXML    private WeightingOptionPanel wop1;
    
    @FXML    private EnvironmentOptionPanel eopElevation;
    
    
    @FXML   private EnvironmentElementStatus eesElevation;
    
    private Text weightingOption1;
    private Text weightingOption2;
    private Text weightingOption3;
    private Text weightingOption4;
    private Text weightingOption5;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        eopElevation.removeUnusedButtons();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        // ---------------------------------------------------------------------
        // Gather all of the options selected, package them, and send them off
        // ---------------------------------------------------------------------
//        label.setText(woqc1.getSelection());
        
//        eopElevation.removeUnusedButtons();
        attachControllersToEachOther();
    }
    
    // -------------------------------------------------------------------------
    // Wires all of the controllers together.  Can only be completed after they
    // have all been constructed, instatiated, and the scene is set.
    // -------------------------------------------------------------------------
    public void attachControllersToEachOther()
    {
        eesElevation.setDisasterEffectPanel("eopRoads");
    }
    
}
