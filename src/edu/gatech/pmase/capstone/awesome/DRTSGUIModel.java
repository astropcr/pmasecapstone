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

import edu.gatech.pmase.capstone.awesome.GUIToolBox.EffectsOptionsPanel;
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.HashMap;
import javafx.scene.control.Label;


/**
 * This file is meant to keep the model that drives the GUI and allows all of
 * the pieces to interact and modify the same data set.  This could possibly
 * be under the auspice of a single JFXML controller but each file gets its
 * own instance copy of the controller during initialization.  Perhaps the single
 * controller could provide the interfaces to this model.
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class DRTSGUIModel {
//    private HashMap<DisasterEffect> disasterEffects;
//    private HashMap<TerrainEffect> terrainEffects;
    
    private final static DRTSGUIModel instance = new DRTSGUIModel();
    
    
    // Environment Options
    private String eesBeachSelOpt = "";
    private EnvironmentElementStatus eesBeach;
    
    // Disaster Effects
    EffectsOptionsPanel eop;
    Label lblDisasterEffects;
    
    public static DRTSGUIModel getInstance()
    {
        return instance;
    }
    
    public String getEesBeachSelOpt()
    {
        return this.eesBeachSelOpt;
    }
    
    public void setEesBeachSelOpt(String eesBeachSelOpt)
    {
        this.eesBeachSelOpt = eesBeachSelOpt;
    }
    
    public void setEesBeach(EnvironmentElementStatus eesToSet) {
        this.eesBeach = eesToSet;
    }
    
    public void updateEesBeachTooltip(String tooltip) {
        this.eesBeach.setToolTip(tooltip);
    }
    public void updateEesBeachStatus(String tooltip) {
        this.eesBeach.setToolTip(tooltip);
    }
    
    
    public void setDisasterEffectsStatus(Label lblDisasterEffectsToSet) {
        this.lblDisasterEffects = lblDisasterEffectsToSet;
    }
    
    public void updateDisasterEffectsStatus(String status) {
        this.lblDisasterEffects.setText(status);
    }
    
    
    
    
}
