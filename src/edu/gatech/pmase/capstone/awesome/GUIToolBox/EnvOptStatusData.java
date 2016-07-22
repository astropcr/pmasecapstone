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

import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
class EnvOptStatusData {

    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(EnvOptStatusData.class);

    @FXML
    private HBox hBox;
    @FXML
    private EnvironmentElementStatus ees;

    private String strData;

    public EnvOptStatusData(ToggleGroup tg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/envOptStatusCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(TerrainEffect te) {
        ees.setUserData(te);
        ees.setEnvironmentOption(te.terrainLabel);

        // We have to connect the EES custom controller to the model here since we have easy access to it.
        ees.connectToModel();
    }

    @FXML
    public void handleToggleButton(ActionEvent event) {
        LOGGER.info("The " + strData + " was selected.");
    }

    public HBox getBox() {
        return hBox;
    }
}
