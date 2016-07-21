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

import edu.gatech.pmase.capstone.awesome.GUIToolBox.DisasterEffectCheckBoxData;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EffectsOptionsPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.WeightingOptionPanel;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * This file is meant to keep the model that drives the GUI and allows all of
 * the pieces to interact and modify the same data set. This could possibly be
 * under the auspice of a single JFXML controller but each file gets its own
 * instance copy of the controller during initialization. Perhaps the single
 * controller could provide the interfaces to this model.
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class DRTSGUIModel {
    
    private final static DRTSGUIModel instance = new DRTSGUIModel();
    
    // -------------------------------------------------------------------------
    // Collections of Enumeration to GUI Objects (both views and controllers)
    // -------------------------------------------------------------------------
    private final HashMap<String, EnvironmentElementStatus> eesCollection = new HashMap<>();
    private final HashMap<String, WeightingOptionPanel> weightingsOptionsCollection = new HashMap<>();
    private final HashMap<String, CheckBox> weightingsOptionsCheckBoxenCollection = new HashMap<>();
    private final HashMap<String, Boolean> weightingsCompletedCollection = new HashMap<>();


    // -------------------------------------------------------------------------
    // Temp variables used by the methods below for each functional GUI area
    // -------------------------------------------------------------------------
    // Disaster Effects
    Label lblDisasterEffects;
    ObservableList<DisasterEffectCheckBoxData> disasterEffects;
    
    /**
     * Grabs the singleton instance of the model.  Is meant to be seen by all
     * members of the top level package.  This is effectively the glue that ties
     * the MVC components together.  Quite literally, this is the rug.
     * @return 
     */
    public static DRTSGUIModel getInstance()
    {
        return instance;
    }
    
    
    
    // -------------------------------------------------------------------------
    // Weighting Options controllers and viewers
    // -------------------------------------------------------------------------
    public void addWeightingOptionPanel(String ID, WeightingOptionPanel wopToAdd)
    {
        this.weightingsOptionsCollection.put(ID, wopToAdd);
    }
    
    public void addWeightingOptionCompletedCheckBox(String weightOptID, CheckBox cbToAdd)
    {
        this.weightingsOptionsCheckBoxenCollection.put(weightOptID, cbToAdd);
    }
    
    public void setWeightingOptionComplete(String weightOptID, Boolean complete)
    {
        Boolean temp = this.weightingsCompletedCollection.get(weightOptID);
        
        if (temp != null) {
            temp = complete;
        }
        
        CheckBox cbTemp = this.weightingsOptionsCheckBoxenCollection.get(weightOptID);
        
        if (cbTemp != null) {
            cbTemp.selectedProperty().setValue(complete);
        }
    }
    
    public void updateWopChecked(String wopToUpdate, Boolean checkedVal) {
        Boolean temp = this.weightingsCompletedCollection.get(wopToUpdate);
        

        if (temp != null) {
            temp = checkedVal;
            
        }
    }
    
    // -------------------------------------------------------------------------
    // Environment Effect Status controllers and viewers
    // -------------------------------------------------------------------------
    
    public void addEes(TerrainEffect te, EnvironmentElementStatus eesToAdd){
        this.eesCollection.put(te.terrainLabel, eesToAdd);
    }
    
    public void updateEesTooltip(TerrainEffect te, String toolTip) {
        EnvironmentElementStatus eesTemp = this.eesCollection.get(te.terrainLabel);
        if(eesTemp != null) {
            eesTemp.setToolTip(toolTip);
        } else {
            System.out.println("EES not found for tooltip update!");
        }
    }
    
    public void updateEesStatus(TerrainEffect te, String weight) {
        EnvironmentElementStatus eesTemp = this.eesCollection.get(te.terrainLabel);
        if(eesTemp != null) { 
            eesTemp.setEnvOptWeight(weight);
        } else {
            System.out.println("EES not found for status update!");
        }
    }
    

    
    
// -------------------------------------------------------------------------
    // Disaster Effect controllers and viewers
    // -------------------------------------------------------------------------
    
    
    public void setDisasterEffectsStatus(Label lblDisasterEffectsToSet) {
        this.lblDisasterEffects = lblDisasterEffectsToSet;
    }

    public void updateDisasterEffectsStatus(String status) {
        this.lblDisasterEffects.setText(status);
    }
}
