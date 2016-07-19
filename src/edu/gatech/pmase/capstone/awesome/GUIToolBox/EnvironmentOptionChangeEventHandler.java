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

import javafx.event.EventHandler;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class EnvironmentOptionChangeEventHandler implements EventHandler<EnvironmentOptionChangeEvent> {

    ScreensController sc;
    String switchTargetScreen;
    String msg;
    
//    public EnvironmentOptionChangeEventHandler(EnvironmentOption eoData, String screeswitchTargetScreennTarget)
//    {
//        this.sc = sc;
//        this.switchTargetScreen = switchTargetScreen;
//        this.msg = "No message has been set.";
//    }


    public EnvironmentOptionChangeEventHandler()
    {
        this.msg = "the author is shy.";
    }    
    public EnvironmentOptionChangeEventHandler(String msg)
    {
        this.msg = msg;
    }
    
    @Override
    public void handle(EnvironmentOptionChangeEvent event) {
        System.out.println("EnvironmentOptionChangeEventHandler would like to say: " + msg);
        //sc.setScreen(switchTargetScreen);
    }
}