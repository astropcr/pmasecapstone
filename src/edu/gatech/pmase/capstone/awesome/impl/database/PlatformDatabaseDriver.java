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
import edu.gatech.pmase.capstone.awesome.objects.enums.PlatformType;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Loads the Platform Database file.
 */
public class PlatformDatabaseDriver extends AbstractDatabaseDriver {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PlatformDatabaseDriver.class);

    /**
     * Platform Workbook Property.
     */
    private static final String PLATFORM_WORKBOOK_PROPERTY_NAME = "platform.workbook";

    /**
     * Workbook cell numbers to load from.
     */
    private static final int DISASTER_EFFECT_RESTRICT_CELL_NUM = 13;
    private static final int TERRAIN_FOUR_RESTRICT_CELL_NUM = 12;
    private static final int TERRAIN_THREE_RESTRICT_CELL_NUM = 11;
    private static final int TERRAIN_TWO_RESTRICT_CELL_NUM = 10;
    private static final int TERRAIN_ONE_RESTRICT_CELL_NUM = 9;
    private static final int PAYLOAD_CELL_NUM = 7;
    private static final int COST_REAL_CELL_NUM = 6;
    private static final int COST_RANKING_CELL_NUM = 5;
    private static final int OPS_HOURS_CELL_NUM = 4;
    private static final int TYPE_CELL_NUM = 3;
    private static final int RANGE_CELL_NUM = 2;
    private static final int LABEL_CELL_NUM = 1;
    private static final int ID_CELL_NUM = 0;

    /**
     * Gets all the Platform Options specified in the Platform database.
     *
     * @return a List of PlatformOption in the database.
     */
    public List<PlatformOption> getPlatformOptions() {
        List<PlatformOption> options = new ArrayList<>();
        Workbook workbook = null;
        final String filename = props.getProperty(PLATFORM_WORKBOOK_PROPERTY_NAME);

        try {
            LOGGER.debug("Reading PlatformOption from filename: " + filename);
            workbook = loadDatabase(filename);
        } catch (IOException | InvalidFormatException ex) {
            LOGGER.error("Error loading workbook with filename: " + filename, ex);
        }

        if (null != workbook) {
            LOGGER.info(" Platform Options read.");
            // get options from workbook
            options = this.readOptionsFromWorkbook(workbook);
        } else {
            LOGGER.error("Unable to load Platform workbook with filename: " + filename);
        }

        return options;
    }

    /**
     * Reads the options from the workbook.
     *
     * @param wb the workbook to read from
     * @return the List of options in the workbook
     */
    private List<PlatformOption> readOptionsFromWorkbook(final Workbook wb) {
        final List<PlatformOption> options = new ArrayList<>();
        // get first sheet
        final Sheet platSheet = wb.getSheetAt(0);

        // max rows
        int maxRows = platSheet.getPhysicalNumberOfRows();
        if (maxRows > 1) {
            for (int rowIter = 1; rowIter < maxRows; rowIter++) {
                final Row row = platSheet.getRow(rowIter);
                if (null != row) {
                    final PlatformOption opt = this.getPlatformOptionFromRow(row);
                    if (null != opt) {
                        options.add(opt);
                    } else {
                        LOGGER.trace("Could not make Platform Option from row " + rowIter);
                    }
                } else {
                    LOGGER.debug("Loaded Invalid Row: " + rowIter);
                }
            }
        } else {
            LOGGER.error("Platform Database does not have the expected number of rows. Must have more than one row.");
        }

        return options;
    }

    /**
     * Creates a PlatformOption from a row.
     *
     * @param row the row to transform
     * @return the created PlatformOption, or null if cannot read the row.
     */
    private PlatformOption getPlatformOptionFromRow(final Row row) {
        PlatformOption option = null;

        // load required info
        final Cell idCell = row.getCell(ID_CELL_NUM);
        final Cell labelCell = row.getCell(LABEL_CELL_NUM);
        final Cell typeCell = row.getCell(TYPE_CELL_NUM);
        final Cell rangeCell = row.getCell(RANGE_CELL_NUM);
        final Cell opsHoursCell = row.getCell(OPS_HOURS_CELL_NUM);
        final Cell costRankCell = row.getCell(COST_RANKING_CELL_NUM);
        final Cell costRealCell = row.getCell(COST_REAL_CELL_NUM);
        final Cell payloadCell = row.getCell(PAYLOAD_CELL_NUM);

        if (null == idCell || null == labelCell || null == typeCell || null == rangeCell || null == opsHoursCell
                || null == costRankCell || null == costRealCell || null == payloadCell
                || idCell.getCellType() == Cell.CELL_TYPE_BLANK) {
            LOGGER.trace("Platform Database Row " + row.getRowNum() + " missing required PlatformOption information.");
        } else {
            option = new PlatformOption();
            final long idNum = (long) idCell.getNumericCellValue();
            LOGGER.debug("Parsing PlatformOption with ID: " + idNum);

            option.setId(idNum);
            option.setLabel(labelCell.getStringCellValue());
            option.setRange(rangeCell.getNumericCellValue());
            option.setOpsDuration(opsHoursCell.getNumericCellValue());
            option.setCostRanking((int) costRankCell.getNumericCellValue());
            option.setActualCost(costRealCell.getNumericCellValue());
            option.setPayload(payloadCell.getNumericCellValue());

            // set Platform Type
            option.setPlatformType(PlatformDatabaseDriver.getPlatformType(typeCell));

            // load optional info
            // terrain effects
            final List<TerrainEffect> terrainLimitation = new ArrayList<>();
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_ONE_RESTRICT_CELL_NUM), 1));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_TWO_RESTRICT_CELL_NUM), 2));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_THREE_RESTRICT_CELL_NUM), 3));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_FOUR_RESTRICT_CELL_NUM), 4));

            LOGGER.debug("Found " + terrainLimitation.size() + " total Terrian Effect Restrictions for PlatformOption");

            option.setTerrainLimitation(terrainLimitation);

            // disaster effect restrictions
            option.setDisasterLimitations(this.getDisasterEffectRestrictFromCell(row.getCell(DISASTER_EFFECT_RESTRICT_CELL_NUM)));
        }

        return option;
    }

    /**
     * Gets the Platform type from the typeCell
     *
     * @param typeCell theCell to get the Platform Type from
     * @return the Platform Type
     */
    private static PlatformType getPlatformType(final Cell typeCell) {
        PlatformType t;

        if (null != typeCell) {
            if (typeCell.getCellType() == Cell.CELL_TYPE_STRING) {
                final String val = typeCell.getStringCellValue();

                switch (val) {
                    case "A":
                        t = PlatformType.AIR;
                        break;
                    case "G":
                        t = PlatformType.GROUND;
                        break;
                    case "W":
                        t = PlatformType.WATER;
                        break;
                    default:
                        t = PlatformType.UNKNOWN;
                }
            } else {
                LOGGER.warn("Could not read platform type for cell: [" + typeCell.getRowIndex() + ","
                        + typeCell.getColumnIndex() + "]");
                t = PlatformType.UNKNOWN;
            }
        } else {
            LOGGER.warn("Could not read platform type for cell");
            t = PlatformType.UNKNOWN;
        }

        return t;
    }

}
