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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;


/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
class EnvOptData {
@FXML
    private HBox hBox;
    @FXML   private ToggleButton btn1;
    
    private ToggleGroup tgSelection;
    
    private String strData;

    public EnvOptData()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/gatech/pmase/capstone/awesome/GUIToolBox/envOptCellItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
        btn1.setToggleGroup(tgSelection);
        
        tgSelection = new ToggleGroup();
    }

    public void setInfo(String string)
    {
        strData = string;
        btn1.setText(strData);
        
    }
    
    public Object getSelection()
    {
        return tgSelection.getSelectedToggle().getUserData();
    }
    
    @FXML
    public void handleToggleButton(ActionEvent event)
    {
//        DRTSGUIModel.getInstance().printStringThing(strData);
    }

    public HBox getBox()
    {
        return hBox;
    }
}
