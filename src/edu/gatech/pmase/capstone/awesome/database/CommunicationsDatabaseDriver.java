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
package edu.gatech.pmase.capstone.awesome.database;

import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Loads the Communications Database file.
 */
public class CommunicationsDatabaseDriver extends AbstractDatabaseDriver {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(CommunicationsDatabaseDriver.class);

    /**
     * Communications Workbook Property.
     */
    private static final String COMM_WORKBOOK_PROPERTY_NAME = "comms.workbook";

    /**
     * 
     * @return 
     */
    public List<CommunicationOption> getCommOptions() {
        final List<CommunicationOption> options = new ArrayList<>();
        Workbook workbook = null;
        final String filename = props.getProperty(COMM_WORKBOOK_PROPERTY_NAME);

        try {
            LOGGER.debug("Reading Communications Options from filename: " + filename);
            workbook = loadDatabase(filename);
        } catch (IOException | InvalidFormatException ex) {
            LOGGER.error("Error loading workbook with filename: " + filename, ex);
        }

        if (null != workbook) {
            LOGGER.info(" Communications Options read.");
            // get options from workbook
        } else {
            LOGGER.error("Unable to load Comm workbook with filename: " + filename);
        }

        return options;

    }
}
