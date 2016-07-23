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
package edu.gatech.pmase.capstone.awesome.impl;

import edu.gatech.pmase.capstone.awesome.IDisasterResponseTradeStudyOutputter;
import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.util.DisasterResponseTradeStudyPropertiesSingleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

/**
 * Outputs the results of Trade Study into a file.
 */
public class DisasterResponseTradeStudyOutputer implements IDisasterResponseTradeStudyOutputter {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            DisasterResponseTradeStudyOutputer.class);

    /**
     * Index for the architecture results table in the template file.
     */
    private static final int ARCH_RESULT_TABLE_INDEX = 0;

    /**
     * Index for the selected disaster table in the template file.
     */
    private static final int SELECTED_DISASTER_TABLE_INDEX = 1;

    /**
     * Index for the selected terrain table in the template file.
     */
    private static final int SELECTED_TERRAIN_TABLE_INDEX = 2;

    /**
     * Index for the platform weighting table in the template file.
     */
    private static final int PLATFORM_WEIGHTING_TABLE_INDEX = 3;

    /**
     * Index for the comms weighting table in the template file.
     */
    private static final int COMM_WEIGHTING_TABLE_INDEX = 4;

    /**
     * Index for the sensor weighting table in the template file.
     */
    private static final int SENSOR_WEIGHTING_TABLE_INDEX = 5;

    /**
     * Index for the report details paragraph in the template file.
     */
    private static final int REPORT_DETAILS_ROW_INDEX = 2;

    /**
     * Unicode character to use for checkmark.
     */
    private static final String CHECKMARK_UNICODE = "\u2713";

    /**
     * Decimal format to print the attribute weighting results with.
     */
    private static final DecimalFormat weightingFormat = new DecimalFormat(
            "#.###");

    /**
     * Date formatter to print the report generated date with.
     */
    private static final DateTimeFormatter fileNameFormatter = DateTimeFormatter.
            ofPattern("yyyy-MM-dd-kk-mm-ss-A");

    /**
     * Date formatter to add time and date to output filename. fileNameFormatter
     */
    private static final DateTimeFormatter outputFileFormatter = DateTimeFormatter.
            ofLocalizedDateTime(FormatStyle.FULL);

    /**
     * Mapping of disaster effects to the table column index in the disaster
     * effect table.
     */
    private static final Map<DisasterEffect, Integer> disEffectColsMap = new HashMap<>();

    /**
     * Mapping of terrain effects to the table column index in the terrain
     * effects table.
     */
    private static final Map<String, Integer> terEffectColsMap = new HashMap<>();

    /**
     * Time and date the calculation was started.
     */
    private ZonedDateTime now;

    /**
     * Populate map with col indices.
     */
    static {
        // map disaster effects
        disEffectColsMap.put(DisasterEffect.FLOOD, 1);
        disEffectColsMap.put(DisasterEffect.DEBRIS, 2);
        disEffectColsMap.put(DisasterEffect.SMOKE_DUST, 3);
        disEffectColsMap.put(DisasterEffect.GROUND_INSTABILITY, 4);
        disEffectColsMap.put(DisasterEffect.LAND_SLIDE, 5);
        disEffectColsMap.put(DisasterEffect.MUD_SLIDE, 6);
        disEffectColsMap.put(DisasterEffect.STRUCTURAL_DAMAGE, 7);
        disEffectColsMap.put(DisasterEffect.HIGH_WIND, 8);
        disEffectColsMap.put(DisasterEffect.HAZARDOUS_MATERIAL_SPILL, 9);
        disEffectColsMap.put(DisasterEffect.RADIOLOGICAL_SPILL, 10);
        disEffectColsMap.put(DisasterEffect.LAVA, 11);
        disEffectColsMap.put(DisasterEffect.ASH, 12);
        disEffectColsMap.put(DisasterEffect.FIRE, 13);
        disEffectColsMap.put(DisasterEffect.INFRASTRUCTURE_BREAKDOWN, 14);

        // map terrain effects
        terEffectColsMap.put("Elevation", 1);
        terEffectColsMap.put("Roads", 2);
        terEffectColsMap.put("Bridges", 3);
        terEffectColsMap.put("Foliage", 4);
        terEffectColsMap.put("Wetness", 5);
        terEffectColsMap.put("Streams", 6);
        terEffectColsMap.put("Beach", 7);
        terEffectColsMap.put("Water Ways", 8);
        terEffectColsMap.put("Urbanization", 9);
        terEffectColsMap.put("Range", 10);
        terEffectColsMap.put("Population", 11);
        terEffectColsMap.put("Persistance", 12);
        terEffectColsMap.put("Trafficability", 13);
        terEffectColsMap.put("Slope", 14);
    }

    @Override
    public Path createOutputFile(final List<DRTSArchitectureResult> results,
            final List<DisasterEffect> selectedDisasterEffects,
            final List<TerrainEffect> selectedTerrainEffects)
            throws IOException, InvalidFormatException {
        // set time
        now = ZonedDateTime.now();

        LOGGER.info("Creating results architecture file.");
        final String filename = "DRTS-Results-" + fileNameFormatter.format(now) + ".docx";

        // create paths
        final Path templatePath = Paths.get(
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().
                getResultsDirectory(),
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().
                getResultsTemplate());

        final Path resultsDir = Paths.get(
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().
                getResultsDirectory());

        Path resultPath = Paths.get(
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().
                getResultsDirectory(),
                filename);

        // copy template file
        final File workbookFile = templatePath.toFile();

        if (workbookFile.exists() && !workbookFile.isDirectory() && workbookFile.canRead()) {
            LOGGER.debug("Using results template file: " + workbookFile.getAbsolutePath());
            final XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(
                    workbookFile));

            // get table
            final List<XWPFTable> tables = xdoc.getTables();

            // set values
            final DRTSArchitectureResult result = results.get(0);

            // create arch table
            this.createArchTable(result, tables.get(ARCH_RESULT_TABLE_INDEX));

            // create disaster effect table
            this.createSelectedDisasterTable(selectedDisasterEffects, tables.
                    get(SELECTED_DISASTER_TABLE_INDEX));

            // create selected terrain table
            this.createSelectedTerrainTable(selectedTerrainEffects, tables.get(
                    SELECTED_TERRAIN_TABLE_INDEX));

            // create platform weightings table
            this.createOptionWeightingTable(result.getPlatform(), tables.get(
                    PLATFORM_WEIGHTING_TABLE_INDEX));

            // create comm weighting table
            this.createOptionWeightingTable(result.getComms(), tables.get(
                    COMM_WEIGHTING_TABLE_INDEX));

            // create sensor weighting table
            this.createOptionWeightingTable(result.getSensor(), tables.get(
                    SENSOR_WEIGHTING_TABLE_INDEX));

            // create details
            this.createReportDetails(xdoc);

            // get result file
            final File resultFile = resultPath.toFile();
            LOGGER.debug("Trying to use result file: " + resultFile.getAbsolutePath());
            LOGGER.debug("Result file is in result directory: " + resultsDir.toAbsolutePath());

            if (!resultFile.isDirectory() && Files.isDirectory(resultsDir) && Files.isWritable(resultsDir)) {
                LOGGER.debug("Creating result file: " + resultFile.getAbsolutePath());
                // write out result
                try (final FileOutputStream outStream = new FileOutputStream(resultFile)) {
                    xdoc.write(outStream);
                }
            } else {
                LOGGER.error("Cannot create output result file at path: " + resultFile.getAbsolutePath());
                resultPath = null;
            }
        } else {
            LOGGER.error("Cannot read input workbook file at path: " + templatePath.toString());
            resultPath = null;
        }

        return resultPath;
    }

    /**
     * Creates the architecture result table.
     *
     * @param result the top architecture result.
     * @param table the table to create the architecture in
     */
    private void createArchTable(final DRTSArchitectureResult result,
            final XWPFTable table) {
        LOGGER.debug("Adding architecture result to output file: " + result.toString());

        final XWPFTableCell platLabel = table.getRow(1).getCell(0);
        platLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        platLabel.setText(result.getPlatform().getLabel());

        final XWPFTableCell platDetails = table.getRow(1).getCell(2);
        platDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.createArchitectureAttributeDescription(platDetails, result.
                getPlatform().
                getPrioritizationAttributess());

        final XWPFTableCell commLabel = table.getRow(2).getCell(0);
        commLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        commLabel.setText(result.getComms().getLabel());

        final XWPFTableCell commDetails = table.getRow(2).getCell(2);
        commDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.createArchitectureAttributeDescription(commDetails, result.
                getComms().
                getPrioritizationAttributess());

        final XWPFTableCell sensorLabel = table.getRow(3).getCell(0);
        sensorLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        sensorLabel.setText(result.getSensor().getLabel());

        final XWPFTableCell sensorDetails = table.getRow(3).getCell(2);
        sensorDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.createArchitectureAttributeDescription(sensorDetails, result.
                getSensor().
                getPrioritizationAttributess());
    }

    /**
     * Creates the selected disaster effects table.
     *
     * @param selectedDisasterEffects the disaster effects selected by the user.
     * @param table the table to write the selected disaster effects to
     */
    private void createSelectedDisasterTable(
            final List<DisasterEffect> selectedDisasterEffects,
            final XWPFTable table) {
        for (final DisasterEffect effect : selectedDisasterEffects) {
            final Integer index = disEffectColsMap.get(effect);

            if (null != index) {
                LOGGER.debug("Adding DisasterEffect " + effect.name() + " to result document at table index: " + index);
                final XWPFTableCell effectIndicatorCell = table.getRow(index).
                        getCell(1);
                effectIndicatorCell.setVerticalAlignment(
                        XWPFTableCell.XWPFVertAlign.CENTER);
                effectIndicatorCell.setText(CHECKMARK_UNICODE);
            } else {
                LOGGER.warn("Could not find index to place DisasterEffect: " + effect.name() + " in output file.");
            }

        }
    }

    /**
     * Creates the terrain effects table.
     *
     * @param selectedTerrainEffects the terrain effects selected but the user
     * @param table the table to write the terrain effects in.
     */
    private void createSelectedTerrainTable(
            final List<TerrainEffect> selectedTerrainEffects,
            final XWPFTable table) {
        for (final TerrainEffect eff : selectedTerrainEffects) {
            final Integer index = terEffectColsMap.get(eff.terrainLabel);

            if (null != index) {
                LOGGER.debug("Adding TerrainEffect " + eff.name() + " to result document at table index: " + index);

                final XWPFTableCell effectValueCell = table.getRow(index).getCell(1);
                effectValueCell.setVerticalAlignment(
                        XWPFTableCell.XWPFVertAlign.CENTER);
                effectValueCell.setText(Integer.toString(eff.codeNum));

                final XWPFTableCell effectDescCell = table.getRow(index).getCell(2);
                effectDescCell.setVerticalAlignment(
                        XWPFTableCell.XWPFVertAlign.CENTER);
                effectDescCell.setText(eff.codeMeaning);
            } else {
                LOGGER.warn("Could not find index to place TerrainEffect: " + eff.name() + " in output file.");
            }

        }
    }

    /**
     * Creates an options weighting table for the provided option.
     *
     * @param option the option to create the table for
     * @param table the table to create the option in
     */
    private void createOptionWeightingTable(
            final AbstractArchitectureOption option, final XWPFTable table) {
        LOGGER.debug("Adding architeacture option weighting table for option: " + option.getLabel());
        for (final ArchitectureOptionAttribute attr : option.
                getPrioritizationAttributess()) {
            LOGGER.debug("Adding architeacture option weighting value: " + attr.getLabel() + " with priority value: "
                    + Double.toString(attr.getPriority()));
            LOGGER.debug(attr.toString());

            final XWPFTableRow row = table.createRow();

            final XWPFTableCell attrLabel = row.getCell(0);
            attrLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            attrLabel.setText(attr.getLabel() + " (" + attr.getUnits() + ")");

            final XWPFTableCell attrWeighting = row.getCell(1);
            attrWeighting.setVerticalAlignment(
                    XWPFTableCell.XWPFVertAlign.CENTER);
            attrWeighting.setText(weightingFormat.format(
                    attr.getPriority() * 100.00) + "%");
            final CTTcPr pr = attrWeighting.getCTTc().addNewTcPr();
            final CTVerticalJc alng = pr.addNewVAlign();
            alng.setVal(STVerticalJc.BOTH);
        }
    }

    /**
     * Creates the architecture attribute description cell.
     *
     * @param detailsCell the cell to create the description in
     * @param attrs the attributes to put into the description
     */
    private void createArchitectureAttributeDescription(
            final XWPFTableCell detailsCell,
            final List<ArchitectureOptionAttribute> attrs) {

        for (int x = 0; x < attrs.size(); x++) {
            final ArchitectureOptionAttribute attr = attrs.get(x);
            LOGGER.debug("Creating architecture option description for attribute: " + attr.getLabel());

            final XWPFParagraph para;
            if (x == 0) {
                para = detailsCell.getParagraphs().get(0);
            } else {
                para = detailsCell.addParagraph();
            }

            final XWPFRun rh = para.createRun();
            rh.setText(DisasterResponseTradeStudyOutputer.createAttribtueString(
                    attr));
            para.setAlignment(ParagraphAlignment.CENTER);
        }
    }

    /**
     * Creates the report details paragraph.
     *
     * @param xdoc the document to create the paragraph in
     */
    private void createReportDetails(final XWPFDocument xdoc) {
        final Locale currentLocale = Locale.getDefault();
        LOGGER.debug("Creating report details");

        final XWPFParagraph para = xdoc.getParagraphs().get(
                REPORT_DETAILS_ROW_INDEX);
        final XWPFRun run1 = para.createRun();
        run1.setBold(true);
        run1.setText("Date Report Generated: ");

        final XWPFRun run2 = para.createRun();
        run2.setBold(false);
        run2.setText(outputFileFormatter.format(now));
        run2.addBreak();

        final XWPFRun run3 = para.createRun();
        run3.setBold(true);
        run3.setText("Country Report Generated: ");

        final XWPFRun run4 = para.createRun();
        run4.setBold(false);
        run4.setText(currentLocale.getDisplayCountry());
    }

    /**
     * Creates the string to use for attribute descriptions.
     *
     * @param attr the attribute to create the string for
     *
     * @return the String to use.
     */
    private static String createAttribtueString(
            final ArchitectureOptionAttribute attr) {
        final StringBuilder sb = new StringBuilder();

        final Class clazz = attr.getType();

        if (Number.class.isAssignableFrom(clazz)) {
            final Number num = (Number) attr.getOriginalValue();
            sb.append(attr.getLabel())
                    .append(": ")
                    .append(num.toString());
        }

        if (attr.getUnits() != null) {
            sb.append(" ").append(attr.getUnits());
        }

        return sb.toString();
    }

}
