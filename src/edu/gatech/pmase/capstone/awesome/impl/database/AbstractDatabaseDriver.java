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
package edu.gatech.pmase.capstone.awesome.impl.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Abstract super class to all the database drivers. For this project the
 * databases are Excel spreadsheets. They are expected to be located a specific
 * location.
 */
public class AbstractDatabaseDriver {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AbstractDatabaseDriver.class);

    /**
     * Name of the properties file.
     */
    private static final String PROPERTIES_FILE = "disasterResponseTradeStudy.properties";

    /**
     * Name of the property to load the workbook directory name from.
     */
    private static final String WORKBOOK_DIR_PROPERTY_NAME = "workbook.dir";

    /**
     * Regex to split String lists with.
     */
    protected static final String LIST_STRING_SPLIT_REGEX = ",";

    /**
     * Properties file to load with file names.
     */
    protected static final Properties props = new Properties();

    /**
     * Name of the workbook directory.
     */
    private static String WORKBOOK_DIR;

    static {
        try {
            props.load(new FileInputStream(PROPERTIES_FILE));
            final String dir = props.getProperty(WORKBOOK_DIR_PROPERTY_NAME);

            LOGGER.debug("Workbook Base Directory set at: " + dir);
            WORKBOOK_DIR = dir;
        } catch (IOException ex) {
            LOGGER.error("Cannot load required properties file.", ex);
        }
    }

    /**
     * Loads a database workbook file with the given filename from the workbook
     * directory specified in the project properties file.
     *
     * @param workbookName the file name of the workbook to load
     * @return the loaded workbook, or null if cannot load
     * @throws IOException if error occurs loading file
     * @throws InvalidFormatException if cannot load file
     */
    public Workbook loadDatabase(final String workbookName) throws IOException, InvalidFormatException {
        final Path path = Paths.get(WORKBOOK_DIR, workbookName);
        LOGGER.debug("Testing Workbok at " + path.toAbsolutePath());
        final File workbook = path.toFile();

        if (workbook.exists() && !workbook.isDirectory() && workbook.canRead()) {
            LOGGER.debug("Loading Workbok at " + path.toAbsolutePath());
            return WorkbookFactory.create(workbook);
        } else {
            throw new IOException("Cannot load Workbok at " + path.toAbsolutePath() + " it is not valid.");
        }
    }

}
