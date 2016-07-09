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
package edu.gatech.pmase.capstone.awesome.objects;

import java.util.Objects;

/**
 * Represents the resulting architecture as specified by the Disaster Response
 * Trade Study tool.
 */
public class DRTSArchitectureResult {

    /**
     * The selected Platform for the architecture.
     */
    private PlatformOption platform;

    /**
     * The selected Sensor for the architecture.
     */
    private SensorOption sensor;

    /**
     * The selected Communications for the architecture.
     */
    private CommunicationOption comms;

    /**
     * Default Constructor.
     */
    public DRTSArchitectureResult() {
        platform = null;
        sensor = null;
        comms = null;
    }

    /**
     * Overloaded Constructor.
     *
     * @param platform the platform to use
     * @param sensor the sensor to use
     * @param comms the communications option to use.
     */
    public DRTSArchitectureResult(final PlatformOption platform, final SensorOption sensor,
            final CommunicationOption comms) {
        this.platform = platform;
        this.sensor = sensor;
        this.comms = comms;
    }

    /**
     * Gets selected Platform for the architecture.
     *
     * @return selected Platform for the architecture.
     */
    public PlatformOption getPlatform() {
        return platform;
    }

    /**
     * Sets selected Platform for the architecture.
     *
     * @param platform selected Platform for the architecture.
     */
    public void setPlatform(final PlatformOption platform) {
        this.platform = platform;
    }

    /**
     * Gets selected Sensor for the architecture.
     *
     * @return selected Sensor for the architecture.
     */
    public SensorOption getSensor() {
        return sensor;
    }

    /**
     * Sets selected Sensor for the architecture.
     *
     * @param sensor selected Sensor for the architecture.
     */
    public void setSensor(final SensorOption sensor) {
        this.sensor = sensor;
    }

    /**
     * Gets selected Communications for the architecture.
     *
     * @return selected Communications for the architecture.
     */
    public CommunicationOption getComms() {
        return comms;
    }

    /**
     * Sets selected Communications for the architecture.
     *
     * @param comms selected Communications for the architecture.
     */
    public void setComms(final CommunicationOption comms) {
        this.comms = comms;
    }

    @Override
    public String toString() {
        return "DRTSArchitectureResult{" + "platform=" + platform.getLabel() + ", sensor=" + sensor.getLabel()
                + ", comms=" + comms.getLabel() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.platform);
        hash = 97 * hash + Objects.hashCode(this.sensor);
        hash = 97 * hash + Objects.hashCode(this.comms);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final DRTSArchitectureResult other = (DRTSArchitectureResult) obj;
        if (!Objects.equals(this.platform, other.platform)) {
            return false;
        }

        if (!Objects.equals(this.sensor, other.sensor)) {
            return false;
        }

        if (!Objects.equals(this.comms, other.comms)) {
            return false;
        }

        return true;
    }

}
