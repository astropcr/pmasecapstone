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
import edu.gatech.pmase.capstone.awesome.util.DisasterResponseTradeStudyPropertiesSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Loads the Communications Database file.
 */
public class CommunicationsDatabaseDriver extends AbstractDatabaseDriver {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            CommunicationsDatabaseDriver.class);

    /**
     * Workbook cell numbers to load from.
     */
    private static final int DISASTER_EFFECT_RESTRICT_CELL_NUM = 9;
    private static final int TERRAIN_FOUR_RESTRICT_CELL_NUM = 8;
    private static final int TERRAIN_THREE_RESTRICT_CELL_NUM = 7;
    private static final int TERRAIN_TWO_RESTRICT_CELL_NUM = 6;
    private static final int TERRAIN_ONE_RESTRICT_CELL_NUM = 5;
    private static final int PLAT_RESTRICT_CELL_NUM = 4;
    private static final int WEIGHT_CELL_NUM = 3;
    private static final int COST_RANK_CELL_NUM = 2;
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
     *                          before creating listing Communications options
     *                          due to the Platform restrictions.
     *
     * @return a List of CommunicationOption in the database.
     */
    public List<CommunicationOption> getCommOptionsFromDatabase(
            final List<PlatformOption> inPlatformOptions) {
        LOGGER.info("Reading CommunicationOption from database.");

        // map platforms
        this.mapPlatformOptions(inPlatformOptions);

        // get options
        return this.loadOptionsFromDatabase(
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().
                getCommWorkbookFileName());
    }

    @Override
    protected CommunicationOption getOptionFromRow(final Row row) {
        CommunicationOption option = null;

        // load required info
        final Cell idCell = row.getCell(ID_CELL_NUM);
        final Cell labelCell = row.getCell(LABEL_CELL_NUM);
        final Cell costRankCell = row.getCell(COST_RANK_CELL_NUM);
        final Cell weightCell = row.getCell(WEIGHT_CELL_NUM);

        if (null == idCell || null == labelCell || null == costRankCell || null == weightCell) {
            LOGGER.trace(
                    "Comm Database Row " + row.getRowNum() + " missing required CommunicationsOption information.");
        } else {
            option = new CommunicationOption();
            final long idNum = (long) idCell.getNumericCellValue();
            LOGGER.debug("Parsing CommunicationsOption with ID: " + idNum);

            option.setId(idNum);
            option.setLabel(labelCell.getStringCellValue());
            option.setCostRanking((int) costRankCell.getNumericCellValue());
            option.setWeight(weightCell.getNumericCellValue());

            // get custom attributes
            option.setCustomAttributes(this.getCustomAttributes(row));

            // load optional info
            // platform restrictions
            option.setPlatformLimitations(this.getPlatFormRestrictFromCell(row.
                    getCell(PLAT_RESTRICT_CELL_NUM), platformOptions));

            // terrain effects
            final List<TerrainEffect> terrainLimitation = new ArrayList<>();
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(
                    TERRAIN_ONE_RESTRICT_CELL_NUM), 1));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(
                    TERRAIN_TWO_RESTRICT_CELL_NUM), 2));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(
                    TERRAIN_THREE_RESTRICT_CELL_NUM), 3));
            terrainLimitation.addAll(this.getTerrainEffectsFromCell(row.getCell(
                    TERRAIN_FOUR_RESTRICT_CELL_NUM), 4));

            LOGGER.debug(
                    "Found " + terrainLimitation.size() + " total Terrian Effect Restrictions for CommunicationsOption");

            option.setTerrainLimitation(terrainLimitation);

            // disaster effect restrictions
            option.setDisasterLimitations(this.
                    getDisasterEffectRestrictFromCell(row.getCell(
                            DISASTER_EFFECT_RESTRICT_CELL_NUM)));
        }

        return option;
    }

    /**
     * Creates mapping of platform options for use in lookup.
     *
     * @param inPlatformOptions passed in list of PlatformOption loaded from
     *                          Platform DB.
     */
    private void mapPlatformOptions(final List<PlatformOption> inPlatformOptions) {
        inPlatformOptions.stream().forEach((option) -> {
            platformOptions.put(option.getId(), option);
        });
    }

}
