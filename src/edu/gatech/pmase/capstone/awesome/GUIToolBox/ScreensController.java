/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package edu.gatech.pmase.capstone.awesome.GUIToolBox;

import edu.gatech.pmase.capstone.awesome.DisasterResponseTradeStudy;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Angie
 */
public class ScreensController extends StackPane {
    //Holds the screens to be displayed

    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(
            ScreensController.class);

    private HashMap<String, Node> screens = new HashMap<>();

    /**
     * The following constants and variables are used during initialization to
     * hide the screens being loaded from the user.
     */
    private static final int FADE_OUT_TIME_DURING_INIT = 5;
    private static final int FADE_IN_TIME_DURING_INIT = 5;
    private static final double FADE_OUT_OPACITY_DURING_INIT = 0.01;
    private static final double FADE_IN_OPACITY_DURING_INIT = 0.01;

    private static final int FADE_OUT_TIME = 500;
    private static final int FADE_IN_TIME = 400;
    private static final double FADE_OUT_OPACITY = 1.0;
    private static final double FADE_IN_OPACITY = 1.0;

    private static int fadeOutLeadTime = FADE_OUT_TIME;
    private static int fadeInLeadTime = FADE_IN_TIME;
    private static double fadeInOpacity = FADE_IN_OPACITY;
    private static double fadeOutOpacity = FADE_OUT_OPACITY;

    /**
     *
     */
    public ScreensController() {
        super();
    }

    //Add the screen to the collection
    /**
     *
     * @param name
     * @param screen
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    /**
     *
     * @param name
     *
     * @return
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    //public boolean loadScreen(String name, String resource) {
    /**
     *
     * @param name
     * @param resource
     *
     * @return
     */
    public ControlledScreen loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().
                    getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.
                                                  getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return myScreenControler;
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    /**
     *
     * @param name
     *
     * @return
     */
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity,
                                                                 fadeOutOpacity)), //1.0
                        new KeyFrame(new Duration(fadeOutLeadTime), //500
                                     new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent t) {
                                     getChildren().remove(0);                    //remove the displayed screen
                                     getChildren().add(0, screens.get(name));     //add the screen
                                     Timeline fadeIn = new Timeline(
                                             new KeyFrame(Duration.ZERO,
                                                          new KeyValue(opacity,
                                                                       0.0)),
                                             new KeyFrame(new Duration(
                                                     fadeInLeadTime), // 400
                                                          new KeyValue(opacity,
                                                                       fadeInOpacity))); // 1.0
                                     fadeIn.play();
                                 }
                             }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(fadeInLeadTime), new KeyValue(
                                     opacity, // 500
                                     fadeInOpacity)));
                fadeIn.play();
            }
            return true;
        } else {
            LOGGER.debug("screen hasn't been loaded!!! \n");
            return false;
        }


        /*Node screenToRemove;
         if(screens.get(name) != null){   //screen loaded
         if(!getChildren().isEmpty()){    //if there is more than one screen
         getChildren().add(0, screens.get(name));     //add the screen
         screenToRemove = getChildren().get(1);
         getChildren().remove(1);                    //remove the displayed screen
         }else{
         getChildren().add(screens.get(name));       //no one else been displayed, then just show
         }
         return true;
         }else {
         System.out.println("screen hasn't been loaded!!! \n");
         return false;
         }*/
    }

    //This method will remove the screen with the given name from the collection of screens
    /**
     *
     * @param name
     *
     * @return
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            LOGGER.debug("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

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
    // -------------------------------------------------------------------------
    // Fancy pants initialization code used to allow default setting to work.
    // This is a very complicated and sophisticated kluge that was added by
    // Mike Shearin  which is goverend by the afore mentioned MIT license
    // -------------------------------------------------------------------------
    private static int curScreen = 0;

    /**
     *
     * Loads each screen so that it's members can be accessed by the model. Each
     * screen is set to 1% opacity to prevent screen flashing.
     *
     * @return
     */
    public boolean cycleThroughAllScreens() {
        boolean finished = true;

        if (curScreen < screens.size()) {
            // -----------------------------------------------------------------
            // Load each screen so that it's members can be accessed by the model
            // -----------------------------------------------------------------
            setScreen((String) screens.keySet().toArray()[curScreen]);
            curScreen++;

            finished = false;
        } else {
            // -----------------------------------------------------------------
            // Now let's fade in the main screen
            // -----------------------------------------------------------------
            fadeInLeadTime = FADE_IN_TIME;
            fadeInOpacity = FADE_IN_OPACITY;

            setScreen(DisasterResponseTradeStudy.screenMainID);

            // set afterwat to prevent flashing
            fadeOutLeadTime = FADE_OUT_TIME;
            fadeOutOpacity = FADE_OUT_OPACITY;
        }
        return finished;
    }

    public void initializeAllScreens(Stage theStage, String strTitle) {

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (!cycleThroughAllScreens()) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                    }
                }
                theStage.setTitle(strTitle);        // currently doesn't work
                theStage.show();
                return null;
            }
        };

        new Thread(sleeper).start();
    }
}
