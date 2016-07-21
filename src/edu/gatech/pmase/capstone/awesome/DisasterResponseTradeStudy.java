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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.ScreensController;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main GUI Application File.
 */
public class DisasterResponseTradeStudy extends Application {

    /**
     * These setup the windows that will be shown.
     * This is inspired by the ScreenController example found at the following site:
     * https://blogs.oracle.com/acaicedo/entry/managing_multiple_screens_in_javafx1
     * 
     */
    public static String classPath = "/edu/gatech/pmase/capstone/awesome/";
    
    // Main Screen
    public static String screenMainID                   = classPath + "MainWindow";
    public static String screenMainFile                 = classPath + "MainWindow.fxml";
    
    // Disaster Effects Screens
    public static String screenEffectsOptID             = classPath + "DisasterEffectsOptions";
    public static String screenEffectsFile              = classPath + "DisasterEffectsOptions.fxml";
    
    // Environment Option Screens
    // None needed...generated automagically based on enumerations.
    
    // Solution Creiteria Weighting Screens
    public static String screenPlatformWeightingID      = classPath + "WeightingOptionsPlatforms";
    public static String screenPlatformsWeightingFile   = classPath + "WeightingOptionsPlatforms.fxml";
    public static String screenCommsWeightingID         = classPath + "WeightingOptionsComms";
    public static String screenCommsWeightingFile       = classPath + "WeightingOptionsComms.fxml";
    public static String screenSensorsWeightingID       = classPath + "WeightingOptionsSensors";
    public static String screenSensorsWeightingFile     = classPath + "WeightingOptionsSensors.fxml";
    
    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudy.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Hello World!");
        LOGGER.debug("Testing Logger");

        ScreensController mainContainer = new ScreensController();
        
        // ---------------------------------------------------------------------
        // Load the screens
        // ---------------------------------------------------------------------
        
        // Main Screen
        ((MainWindowController)mainContainer.loadScreen(DisasterResponseTradeStudy.screenMainID, DisasterResponseTradeStudy.screenMainFile)).connectToModel();
            
        
        // Disaster Effect Screen
        mainContainer.loadScreen(DisasterResponseTradeStudy.screenEffectsOptID, DisasterResponseTradeStudy.screenEffectsFile);
        
        // Environment Factors Screeens
        Set<String> strLabels = TerrainEffect.getEffectLabels();
        for(String label : strLabels)
        {
            ((EnvironmentOptionsController)(mainContainer.loadScreen(label, 
                                                                        classPath + 
                                                                        "EnvironmentOptions.fxml")))
                                               .setupEnvOpts(label);
        }

        // Weighting Criteria Screens
        mainContainer.loadScreen(DisasterResponseTradeStudy.screenPlatformWeightingID, DisasterResponseTradeStudy.screenPlatformsWeightingFile);
        mainContainer.loadScreen(DisasterResponseTradeStudy.screenCommsWeightingID, DisasterResponseTradeStudy.screenCommsWeightingFile);
        mainContainer.loadScreen(DisasterResponseTradeStudy.screenSensorsWeightingID, DisasterResponseTradeStudy.screenSensorsWeightingFile);
        
        
        
        // Environmental Options Screens


    
        
        // Test Code
        
        

        // ---------------------------------------------------------------------
        // Now let's start the show
        // ---------------------------------------------------------------------
        mainContainer.setScreen(DisasterResponseTradeStudy.screenMainID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);      
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Disaster Response Trade Study Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
        

        
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
