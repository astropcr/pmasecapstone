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

import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
     * Workbook cell numbers to load from.
     */
    private static final int DISASTER_EFFECT_RESTRICT_CELL_NUM = 13;
    private static final int TERRAIN_FOUR_RESTRICT_CELL_NUM = 12;
    private static final int TERRAIN_THREE_RESTRICT_CELL_NUM = 11;
    private static final int TERRAIN_TWO_RESTRICT_CELL_NUM = 10;
    private static final int TERRAIN_ONE_RESTRICT_CELL_NUM = 9;
    private static final int PLAT_RESTRICT_CELL_NUM = 6;
    private static final int WEIGHT_CELL_NUM = 5;
    private static final int COST_RANK_CELL_NUM = 4;
    private static final int DATA_RATE_CELL_NUM = 3;
    private static final int RANGE_CELL_NUM = 2;
    private static final int LABEL_CELL_NUM = 1;
    private static final int ID_CELL_NUM = 0;

    /**
     * List of Platform Options to Use.
     */
    private final Map<Long, PlatformOption> platformOptions = new HashMap<>();

    /**
     * Gets all the Communications Options specified in the Communications
     * database.
     *
     * @param inPlatformOptions list of PlatformOptions. Must be generated
     * before creating listing of Comm options due to the Platform restrictions.
     * @return a List of CommunicationOptions in the database.
     */
    public List<CommunicationOption> getCommOptions(final List<PlatformOption> inPlatformOptions) {
        this.mapPlatformOptions(inPlatformOptions);

        List<CommunicationOption> options = new ArrayList<>();
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
            options = this.readOptionsFromWorkbook(workbook);
        } else {
            LOGGER.error("Unable to load Comm workbook with filename: " + filename);
        }

        return options;
    }

    /**
     * Reads the options from the workbook.
     *
     * @param wb the workbook to read from
     * @return the List of options in the workbook
     */
    private List<CommunicationOption> readOptionsFromWorkbook(final Workbook wb) {
        final List<CommunicationOption> options = new ArrayList<>();
        // get first sheet
        final Sheet commSheet = wb.getSheetAt(0);

        // max rows
        int maxRows = commSheet.getPhysicalNumberOfRows();
        if (maxRows > 1) {
            for (int rowIter = 1; rowIter < maxRows; rowIter++) {
                final Row row = commSheet.getRow(rowIter);
                if (null != row) {
                    final CommunicationOption opt = this.getCommOptionFromRow(row);
                    if (null != opt) {
                        options.add(opt);
                    } else {
                        LOGGER.trace("Could not make Communications Option from row " + rowIter);
                    }
                } else {
                    LOGGER.debug("Loaded Invalid Row: " + rowIter);
                }
            }
        } else {
            LOGGER.error("Communications Database does not have the expected number of rows. Must have more than one row.");
        }

        return options;
    }

    /**
     * Creates a CommunicationOption from a row.
     *
     * @param row the row to transform
     * @return the created CommunicationOption, or null if cannot read the row.
     */
    private CommunicationOption getCommOptionFromRow(final Row row) {
        CommunicationOption option = null;

        // load required info
        final Cell idCell = row.getCell(ID_CELL_NUM);
        final Cell labelCell = row.getCell(LABEL_CELL_NUM);
        final Cell rangeCell = row.getCell(RANGE_CELL_NUM);
        final Cell dataRateCell = row.getCell(DATA_RATE_CELL_NUM);
        final Cell costRankCell = row.getCell(COST_RANK_CELL_NUM);
        final Cell weightCell = row.getCell(WEIGHT_CELL_NUM);

        if (null == idCell || null == labelCell || null == rangeCell || null == dataRateCell || null == costRankCell
                || null == weightCell) {
            LOGGER.trace("Comm Database Row " + row.getRowNum() + " missing required CommunicationsOption information.");
        } else {
            option = new CommunicationOption();
            final long idNum = (long) idCell.getNumericCellValue();
            LOGGER.debug("Parsing CommunicationsOption with ID: " + idNum);

            option.setId(idNum);
            option.setLabel(labelCell.getStringCellValue());
            option.setRange(rangeCell.getNumericCellValue());
            option.setDataRate(dataRateCell.getNumericCellValue());
            option.setCostRanking((int) costRankCell.getNumericCellValue());
            option.setWeight(weightCell.getNumericCellValue());

            // load optional info
            // platform restrictions
            option.setPlatformLimitations(this.getPlatFormRestrictFromCell(row.getCell(PLAT_RESTRICT_CELL_NUM), platformOptions));

            // terrain effects
            final List<TerrainEffect> terrainLimitation = new ArrayList<>();
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_ONE_RESTRICT_CELL_NUM), 1));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_TWO_RESTRICT_CELL_NUM), 2));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_THREE_RESTRICT_CELL_NUM), 3));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(TERRAIN_FOUR_RESTRICT_CELL_NUM), 4));

            LOGGER.debug("Found " + terrainLimitation.size() + " total Terrian Effect Restrictions for CommunicationsOption");

            option.setTerrainLimitation(terrainLimitation);

            // disaster effect restrictions
            option.setDisasterLimitations(this.getDisasterEffectRestrictFromCell(row.getCell(DISASTER_EFFECT_RESTRICT_CELL_NUM)));
        }

        return option;
    }

    /**
     * Creates mapping of platform options for use in lookup.
     *
     * @param inPlatformOptions passed in list of PlatformOption loaded from
     * Platform DB.
     */
    private void mapPlatformOptions(final List<PlatformOption> inPlatformOptions) {
        inPlatformOptions.stream().forEach((option) -> {
            platformOptions.put(option.getId(), option);
        });
    }

}