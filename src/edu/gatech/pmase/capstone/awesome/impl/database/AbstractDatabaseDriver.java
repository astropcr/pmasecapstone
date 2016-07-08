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

import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
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

    /**
     * Reads in a List of IDs contained within a Cell.
     *
     * @param cell the Cell to read from
     * @return a List of ID's contained within the given Cell.
     */
    protected List<Long> getListFromCell(final Cell cell) {
        List<Long> result = new ArrayList<>(1);
        if (null != cell && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                final String[] split = cell.getStringCellValue().replaceAll(" ", "").split(LIST_STRING_SPLIT_REGEX);
                // check if single value
                if (null != split && split.length > 0) {
                    result = Arrays.asList(split).stream().map((String str) -> Long.parseLong(str)).collect(Collectors.toList());
                } else {
                    LOGGER.error("Invalid List of values found in DB in cell: [" + cell.getRowIndex() + "," + cell.getColumnIndex() + "]");
                }
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                result.add((long) cell.getNumericCellValue());
            }
        }
        return result;
    }

    /**
     * Reads in the TerrainEffect list from a given Cell.
     *
     * @param cell the cell to read from
     * @param terrainCodeNum the terrain code of the given column for the Cell.
     * @return A List of TerrainEffects specified in the given Cell
     */
    protected List<TerrainEffect> getTerrainEffectsFromCell(final Cell cell, final int terrainCodeNum) {
        final List<TerrainEffect> effects = new ArrayList<>();
        for (final Long terrainId : this.getListFromCell(cell)) {
            final TerrainEffect effect = TerrainEffect.getEffectByIdAndCode(terrainId.intValue(), terrainCodeNum);
            if (TerrainEffect.UNKNWON == effect) {
                LOGGER.error("Unknown TerrainEffect with id: " + terrainId + " and code: " + terrainCodeNum
                        + " found in DB for cell: [" + cell.getRowIndex() + "," + cell.getColumnIndex() + "]");
            } else {
                effects.add(effect);
            }
        }
        LOGGER.debug("Found " + effects.size() + " Terrian Effect Restrictions with code num: " + terrainCodeNum);
        return effects;
    }

    /**
     * Load the DisasterEffects listed in the given Cell.
     *
     * @param cell the Cell to read DisasterEffects from
     * @return a List of DisasterEffects contained in the given Cell
     */
    protected List<DisasterEffect> getDisasterEffectRestrictFromCell(final Cell cell) {
        final List<DisasterEffect> effects = new ArrayList<>();
        for (final Long effectId : this.getListFromCell(cell)) {
            effects.add(DisasterEffect.getEffectById(effectId.intValue()));
        }
        LOGGER.debug("Found " + effects.size() + " disaster effects");

        return effects;
    }

    /**
     * Reads a list of Platforms from the given Cell.
     *
     * @param cell the Cell to read the Platform list from.
     * @param platformOptions list of available PlatformOptions
     * @return a List of PlatformOptions specified in the given Cell.
     */
    protected List<PlatformOption> getPlatFormRestrictFromCell(final Cell cell, final Map<Long, PlatformOption> platformOptions) {
        final List<PlatformOption> platList = new ArrayList<>();
        for (final Long platId : this.getListFromCell(cell)) {
            final PlatformOption plat = platformOptions.get(platId);
            if (null != plat) {
                platList.add(plat);
            } else {
                LOGGER.warn("Could not find Platform with id: " + platId + " in DB.");
            }
        }
        LOGGER.debug("Found " + platList.size() + " platforms");
        return platList;
    }

}
