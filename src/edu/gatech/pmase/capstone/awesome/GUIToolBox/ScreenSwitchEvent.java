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

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author Mike Shearin <mike.shearin@gtri.gatech.edu>
 */
public class ScreenSwitchEvent extends Event {

    /**
     *
     */
    public static final EventType<ScreenSwitchEvent> SCREEN_SELECTED = new EventType<>(
            Event.ANY, "SCREEN_SELECTED");

    /**
     *
     */
    public static final EventType<ScreenSwitchEvent> SCREEN2_SELECTED = new EventType<>(
            Event.ANY, "SCREEN2_SELECTED");

    /**
     *
     */
    public ScreenSwitchEvent() {
        this(SCREEN_SELECTED);
    }

    /**
     *
     * @param eventType
     */
    public ScreenSwitchEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    /**
     *
     * @param source
     * @param target
     * @param eventType
     */
    public ScreenSwitchEvent(Object source, EventTarget target,
                             EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    /**
     *
     * @param source
     * @param target
     */
    public ScreenSwitchEvent(Object source, EventTarget target) {
        super(source, target, SCREEN_SELECTED);
    }

    @Override
    public EventType<? extends ScreenSwitchEvent> getEventType() {
        return (EventType<? extends ScreenSwitchEvent>) super.getEventType();
    }

}
