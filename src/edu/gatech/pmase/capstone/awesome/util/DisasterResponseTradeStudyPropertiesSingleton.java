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
package edu.gatech.pmase.capstone.awesome.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages Property File.
 */
public class DisasterResponseTradeStudyPropertiesSingleton {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            DisasterResponseTradeStudyPropertiesSingleton.class);

    /**
     * Name of the properties file.
     */
    private static final String PROPERTIES_FILE = "disasterResponseTradeStudy.properties";

    /**
     * Name of the property to load the workbook directory name from.
     */
    private static final String WORKBOOK_DIR_PROPERTY_NAME = "workbook.dir";

    /**
     * Directory name that the results should be created in.
     */
    private static final String RESULTS_DIR_PROPERTY_NAME = "result.dir";

    /**
     * Results template property.
     */
    private static final String RESULTS_TEMPLATE_FILE_NAME = "result.template";

    /**
     * Communications Workbook Property.
     */
    private static final String COMM_WORKBOOK_PROPERTY_NAME = "comms.workbook";

    /**
     * Platform Workbook Property.
     */
    private static final String PLATFORM_WORKBOOK_PROPERTY_NAME = "platform.workbook";

    /**
     * Sensor Workbook Property.
     */
    private static final String SENSOR_WORKBOOK_PROPERTY_NAME = "sensor.workbook";

    /**
     * Static instance.
     */
    private static final DisasterResponseTradeStudyPropertiesSingleton instance
                                                                       = new DisasterResponseTradeStudyPropertiesSingleton();

    /**
     * Properties file to load with file names.
     */
    private final Properties props = new Properties();

    /**
     * Workbook directory.
     */
    private String workbookDirectory;

    /**
     * Comms workbook name.
     */
    private String commWorkbookFileName;

    /**
     * Platform workbook name.
     */
    private String platformWorkbookFileName;

    /**
     * Sensors workbook name.
     */
    private String sensorsWorkbookFileName;

    /**
     * Results directory.
     */
    private String resultsDirectory;

    /**
     * Results template.
     */
    private String resultsTemplate;

    /**
     * Private constructor.
     */
    private DisasterResponseTradeStudyPropertiesSingleton() {
        try {
            props.load(new FileInputStream(PROPERTIES_FILE));

            final String dir = props.getProperty(WORKBOOK_DIR_PROPERTY_NAME);
            final String resultsDir = props.getProperty(
                    RESULTS_DIR_PROPERTY_NAME);
            final String comms = props.getProperty(COMM_WORKBOOK_PROPERTY_NAME);
            final String platform = props.getProperty(
                    PLATFORM_WORKBOOK_PROPERTY_NAME);
            final String sensors = props.getProperty(
                    SENSOR_WORKBOOK_PROPERTY_NAME);
            final String resultTemp = props.getProperty(
                    RESULTS_TEMPLATE_FILE_NAME);

            LOGGER.debug("Workbook Base Directory set at: " + dir);
            workbookDirectory = dir;

            LOGGER.debug("Results Base Directory set at: " + resultsDir);
            resultsDirectory = resultsDir;

            LOGGER.debug("Results Template set as: " + resultTemp);
            resultsTemplate = resultTemp;

            LOGGER.debug("Comms Workbook: " + comms);
            commWorkbookFileName = comms;

            LOGGER.debug("Platform Workbook: " + platform);
            platformWorkbookFileName = platform;

            LOGGER.debug("Sensor Workbook: " + sensors);
            sensorsWorkbookFileName = sensors;
        } catch (IOException ex) {
            LOGGER.error("Cannot load required properties file.", ex);
        }
    }

    /**
     * Returns the one instance of Singleton
     *
     * @return the static instance of the singleton.
     */
    public static DisasterResponseTradeStudyPropertiesSingleton getInstance() {
        return instance;
    }

    /**
     * The directory name of the workbook folder where the workbook database
     * files are located.
     *
     * @return directory name of the workbook folder where the workbook database
     *         files are located.
     */
    public String getWorkbookDirectory() {
        return workbookDirectory;
    }

    /**
     * Gets the filename for the Communication options technology database
     * workbook.
     *
     * @return the filename for the Communication options technology database
     *         workbook.
     */
    public String getCommWorkbookFileName() {
        return commWorkbookFileName;
    }

    /**
     * Gets the filename for the Platform options technology database workbook.
     *
     * @return the filename for the Platform options technology database
     *         workbook.
     */
    public String getPlatformWorkbookFileName() {
        return platformWorkbookFileName;
    }

    /**
     * Gets the filename for the Sensor options technology database workbook.
     *
     * @return the filename for the Sensor options technology database workbook.
     */
    public String getSensorsWorkbookFileName() {
        return sensorsWorkbookFileName;
    }

    /**
     * Gets the directory to store the result files within.
     *
     * @return the directory to store the result files within.
     */
    public String getResultsDirectory() {
        return resultsDirectory;
    }

    /**
     * Gets the filename for the result template file.
     *
     * @return the filename for the result template file.
     */
    public String getResultsTemplate() {
        return resultsTemplate;
    }

}
