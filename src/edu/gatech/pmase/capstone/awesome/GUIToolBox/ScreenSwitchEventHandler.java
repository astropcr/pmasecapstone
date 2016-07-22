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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class ScreenSwitchEventHandler implements EventHandler<ScreenSwitchEvent> {

    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(ScreenSwitchEventHandler.class);

    ScreensController sc;
    String switchTargetScreen;
    String msg;

    public ScreenSwitchEventHandler() {
        this.sc = null;
        this.switchTargetScreen = "";
        this.msg = "No message has been set.";
    }

    public ScreenSwitchEventHandler(ScreensController sc, String switchTargetScreen) {
        this.sc = sc;
        this.switchTargetScreen = switchTargetScreen;
        this.msg = "No message has been set.";
    }

    public ScreenSwitchEventHandler(ScreensController sc, String switchTargetScreen, String msg) {
        this.sc = sc;
        this.switchTargetScreen = switchTargetScreen;
        this.msg = msg;
    }

    @Override
    public void handle(ScreenSwitchEvent event) {
        LOGGER.debug("ScreenSwitchEventHandler(event) would like to say: " + msg);
    }

    public void handle(ScreenSwitchEvent event, ScreensController sc, String switchTargetScreen) {
        LOGGER.debug("ScreenSwitchEventHandler(event, ScreensController, String) would like to say: " + msg);
        sc.setScreen(switchTargetScreen);

    }

}
