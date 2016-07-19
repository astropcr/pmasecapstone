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

import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.SortOrderEnum;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.util.DisasterResponseTradeStudyPropertiesSingleton;
import java.io.File;
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
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Abstract super class to all the database drivers. For this project the
 * databases are Excel spreadsheets. They are expected to be located a specific
 * location.
 *
 * @param <T> type of option the database creates
 */
public abstract class AbstractDatabaseDriver<T extends AbstractArchitectureOption> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AbstractDatabaseDriver.class);

    /**
     * Column number for label attribute in custom column description.
     */
    private static final int CUSTOM_LABEL_COL_NUM = 0;

    /**
     * Column number for column number attribute in custom column description.
     */
    private static final int COL_NUM_COL_NUM = 1;

    /**
     * Column number for units attribute in custom column description.
     */
    private static final int UNITS_COL_NUM = 2;

    /**
     * Column number for type attribute in custom column description.
     */
    private static final int TYPE_COL_NUM = 3;

    /**
     * Column number for sorting attribute in custom column description.
     */
    private static final int SORTING_COL_NUM = 4;

    /**
     * Sheet number for the customer attribute sheet.
     */
    private static final int CUSTOM_ATTR_SHEET_NUM = 1;

    /**
     * Regex to split String lists with.
     */
    protected static final String LIST_STRING_SPLIT_REGEX = ",";

    /**
     * Properties file to load with file names.
     */
    protected static final Properties props = new Properties();

    /**
     * List of custom attributes for database.
     */
    protected static final List<ArchitectureOptionAttribute> customAttributes = new ArrayList<>();

    /**
     * Creates a Attribute Description object from the given row. Does not set
     * the value.
     *
     * @param row the row to read from
     * @return the created ArchitectureOptionAttribute
     * @throws ClassNotFoundException if cannot load the class needed.
     */
    private static ArchitectureOptionAttribute getAttributeDescriptionFromRow(final Row row)
            throws ClassNotFoundException {
        final ArchitectureOptionAttribute attr = new ArchitectureOptionAttribute();

        attr.setLabel(row.getCell(CUSTOM_LABEL_COL_NUM).getStringCellValue());
        attr.setColNum((int) row.getCell(COL_NUM_COL_NUM).getNumericCellValue());
        attr.setUnits(row.getCell(UNITS_COL_NUM).getStringCellValue());
        attr.setSorting(SortOrderEnum.valueOf((row.getCell(SORTING_COL_NUM).getStringCellValue())));
        attr.setType(Class.forName(row.getCell(TYPE_COL_NUM).getStringCellValue()));

        LOGGER.debug("Loaded custom attribute with name: " + attr.getLabel());

        return attr;
    }

    /**
     * Creates a option from a row.
     *
     * @param row the row to transform
     * @return the created option, or null if cannot read the row.
     */
    protected abstract T getOptionFromRow(final Row row);

    /**
     * Loads a database workbook file with the given filename from the workbook
     * directory specified in the project properties file.
     *
     * @param workbookName the file name of the workbook to load
     * @return the loaded workbook, or null if cannot load
     */
    protected List<T> loadOptionsFromDatabase(final String workbookName) {
        List<T> options = new ArrayList<>();

        if (null != workbookName) {
            LOGGER.debug("Reading options from filename: " + workbookName);

            final Path path = Paths.get(
                    DisasterResponseTradeStudyPropertiesSingleton.getInstance().getWorkbookDirectory(), workbookName);
            LOGGER.debug("Testing Workbok at " + path.toAbsolutePath());
            final File workbookFile = path.toFile();

            if (workbookFile.exists() && !workbookFile.isDirectory() && workbookFile.canRead()) {
                LOGGER.debug("Loading Workbok at " + path.toAbsolutePath());
                try (final Workbook workbook = WorkbookFactory.create(workbookFile)) {
                    this.setCustomAttributes(workbook);

                    // get options from workbook
                    options.addAll(this.readOptionsFromWorkbook(workbook));
                } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
                    LOGGER.error("Could not read Communication filename from properties.", ex);
                }
            } else {
                LOGGER.error("Unable to load Platform workbook with filename: " + workbookName);
            }
        } else {
            LOGGER.error("Could not read platform filename from properties.");
        }

        return options;
    }

    /**
     * Reads the custom attribute values from the custom attribute sheet.
     *
     * @param wb the workbook to read the custom attributes from
     */
    protected synchronized void setCustomAttributes(final Workbook wb) {
        LOGGER.info("Loading custom attribute list");
        customAttributes.clear();

        final Sheet customSheet = wb.getSheetAt(CUSTOM_ATTR_SHEET_NUM);
        if (null != customSheet) {
            LOGGER.debug("Custom attribute list stored in sheet: " + customSheet.getSheetName());

            final int maxRows = customSheet.getPhysicalNumberOfRows();
            if (maxRows > 1) {
                for (int rowIter = 1; rowIter < maxRows; rowIter++) {
                    final Row row = customSheet.getRow(rowIter);
                    if (null != row) {
                        try {
                            customAttributes.add(AbstractDatabaseDriver.getAttributeDescriptionFromRow(row));
                        } catch (ClassNotFoundException ex) {
                            LOGGER.error("Could not load custom attribute for row: " + row.getRowNum(), ex);
                        }
                    }
                }
            }
        } else {
            LOGGER.warn("Could not load customer sheet");
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
        LOGGER.debug("Found " + platList.size() + " platform restrictions.");
        return platList;
    }

    /**
     * Gets the custom attributes from the given row.
     *
     * @param row the row to read from
     * @return the list of gathered custom attributes.
     */
    protected List<ArchitectureOptionAttribute> getCustomAttributes(final Row row) {
        final List<ArchitectureOptionAttribute> attrList = new ArrayList<>(customAttributes.size());
        final StringBuilder attrLabels = new StringBuilder();

        for (final ArchitectureOptionAttribute attr : customAttributes) {
            final Cell cell = row.getCell(attr.getColNum());
            try {
                final ArchitectureOptionAttribute cpy = new ArchitectureOptionAttribute(attr);

                if (cpy.getType().equals(String.class
                )) {
                    cpy.setValue(cell.getStringCellValue());

                } else if (cpy.getType().equals(Double.class
                )) {
                    cpy.setValue(cell.getNumericCellValue());
                } else {
                    LOGGER.warn("Unable to read custom attributes for row: " + row.getRowNum()
                            + ". Unknown cell type: " + cpy.getType().getName());
                }

                attrList.add(cpy);

                if (attrList.isEmpty()) {
                    attrLabels.append("--");
                } else {
                    attrLabels.append(", ");
                }
                attrLabels.append(cpy.getLabel());
            } catch (ClassNotFoundException ex) {
                LOGGER.warn("Unable to read custom attributes for row: " + row.getRowNum(), ex);
            }
        }

        LOGGER.debug("Read " + attrList.size() + " custom attributes for row: " + row.getRowNum() + attrLabels.toString());
        return attrList;
    }

    /**
     * Reads the options from the workbook.
     *
     * @param workbook the workbook to read from
     * @return the List of options in the workbook
     */
    private List<T> readOptionsFromWorkbook(final Workbook workbook) {
        final List<T> options = new ArrayList<>();

        // get first sheet
        final Sheet sheet = workbook.getSheetAt(0);

        // max rows
        int maxRows = sheet.getPhysicalNumberOfRows();
        if (maxRows > 1) {
            for (int rowIter = 1; rowIter < maxRows; rowIter++) {
                final Row row = sheet.getRow(rowIter);
                if (null != row) {
                    final T opt = this.getOptionFromRow(row);
                    if (null != opt) {
                        options.add(opt);
                    } else {
                        LOGGER.trace("Could not make option from row " + rowIter);
                    }
                } else {
                    LOGGER.debug("Loaded Invalid Row: " + rowIter);
                }
            }
        } else {
            LOGGER.error("Database does not have the expected number of rows. Must have more than one row.");
        }

        return options;
    }

}
