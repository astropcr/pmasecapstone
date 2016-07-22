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
import edu.gatech.pmase.capstone.awesome.GUIToolBox.EnvironmentElementStatus;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.WeightingAreasOfConcern;
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
    private final HashMap<String, CheckBox> weightingsOptionsCheckBoxenCollection = new HashMap<>();
    private final HashMap<String, Boolean> weightingsCompletedCollection = new HashMap<>();

    // -------------------------------------------------------------------------
    // Temp variables used by the methods below for each functional GUI area
    // -------------------------------------------------------------------------
    // Disaster Effects
    Label lblDisasterEffects;
    ObservableList<DisasterEffectCheckBoxData> disasterEffects;

    /**
     * Grabs the singleton instance of the model. Is meant to be seen by all
     * members of the top level package. This is effectively the glue that ties
     * the MVC components together. Quite literally, this is the rug.
     *
     * @return
     */
    public static DRTSGUIModel getInstance() {
        return instance;
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

    // -------------------------------------------------------------------------
    // Weighting Criteria controllers and viewers
    // -------------------------------------------------------------------------
    /**
     * Adds a checkbox for an Weighting Area of Concern. The checkbox is meant
     * to indicate to the user that they have finished selecting their weighting
     * preferences for the Weighting Area of Concern.
     *
     * @param waoc
     * @param cbToAdd
     */
    public void addWaoccb(WeightingAreasOfConcern waoc, CheckBox cbToAdd) {
        this.weightingsOptionsCheckBoxenCollection.put(waoc.label, cbToAdd);
    }

    /**
     * Updates the checkbox for the a Weighting Area of Concern to indicate that
     * the WAOC has been set.
     *
     * @param waoc
     * @param update
     */
    public void updateWoccb(WeightingAreasOfConcern waoc, Boolean update) {
        CheckBox cbTemp = this.weightingsOptionsCheckBoxenCollection.get(waoc.label);
        if (cbTemp != null) {
            cbTemp.setSelected(update);
        } else {
            System.out.println("Weighting Area of Concern CheckBox not found update!");
        }
    }

    /**
     *
     * @param waoc
     * @param complete
     */
    public Boolean determineIfAllWaocSelectionsHaveBeenMade(WeightingAreasOfConcern waoc, Boolean complete) {
        Boolean determination = true;

        // .....................................................................
        // The decision to use the CheckBox collection vs the 
        // WeightingAreasOfConceren enumeration is predicated on the logic that 
        // if the program fails to load a CheckBox collection, it's a logic
        // error on the part programmer and not necessarily the user (assuming
        // the user can defined the number of areas of concern). The validity of
        // the user's inputs for defining the areas of concern should have
        // already been checked prior to this function being reached. The
        // {@link updateWoccb} function does warn for null pointers to checkboxen.
        // .....................................................................
        // .....................................................................
        // peforms the following operation more efficiently 
        //
        //  for(CheckBox cb : this.weightingsOptionsCheckBoxenCollection.values()){
        //      if (cb != null) { determination &= cb.isSelected(); }
        //  }
        // .....................................................................
        determination = weightingsOptionsCheckBoxenCollection
                .values()
                .stream()
                .filter((cb) -> (cb != null))
                .map((cb) -> cb.isSelected())
                .reduce(determination, (accumulator, _item) -> accumulator & _item);

        return determination;
    }

    // -------------------------------------------------------------------------
    // Environment Effect Status controllers and viewers
    // -------------------------------------------------------------------------
    public void addEes(TerrainEffect te, EnvironmentElementStatus eesToAdd) {
        this.eesCollection.put(te.terrainLabel, eesToAdd);
    }

    public void updateEesTooltip(TerrainEffect te, String toolTip) {
        EnvironmentElementStatus eesTemp = this.eesCollection.get(te.terrainLabel);
        if (eesTemp != null) {
            eesTemp.setToolTip(toolTip);
        } else {
            System.out.println("EES not found for tooltip update!");
        }
    }

    public void updateEesStatus(TerrainEffect te, String weight) {
        EnvironmentElementStatus eesTemp = this.eesCollection.get(te.terrainLabel);
        if (eesTemp != null) {
            eesTemp.setEnvOptWeight(weight);
        } else {
            System.out.println("EES not found for status update!");
        }
    }
}
