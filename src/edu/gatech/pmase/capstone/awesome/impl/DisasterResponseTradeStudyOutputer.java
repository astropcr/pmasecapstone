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

import edu.gatech.pmase.capstone.awesome.objects.AbstractArchitectureOption;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import edu.gatech.pmase.capstone.awesome.util.DisasterResponseTradeStudyPropertiesSingleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class DisasterResponseTradeStudyOutputer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudyOutputer.class);

    /**
     *
     */
    private static final int ARCH_RESULT_TABLE_INDEX = 0;

    /**
     *
     */
    private static final int SELECTED_DISASTER_TABLE_INDEX = 1;

    /**
     *
     */
    private static final int SELECTED_TERRAIN_TABLE_INDEX = 2;

    /**
     *
     */
    private static final int PLATFORM_WEIGHTING_TABLE_INDEX = 3;

    /**
     *
     */
    private static final int COMM_WEIGHTING_TABLE_INDEX = 4;

    /**
     *
     */
    private static final int SENSOR_WEIGHTING_TABLE_INDEX = 5;

    /**
     *
     */
    private static final int REPORT_DETAILS_ROW_INDEX = 2;

    /**
     *
     */
    private static final String CHECKMARK_UNICODE = "\u2713";

    /**
     *
     */
    private static final DecimalFormat weightingFormat = new DecimalFormat("#.##");

    /**
     *
     */
    private static final DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);

    /**
     *
     */
    private static final Map<DisasterEffect, Integer> disEffectColsMap = new HashMap<>();

    /**
     *
     */
    private static final Map<String, Integer> terEffectColsMap = new HashMap<>();

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

    /**
     * Creates the output file from the results of the Trade Study.
     *
     * @param results the resulting architectures
     * @param selectedDisasterEffects the selected disaster effects
     * @param selectedTerrainEffects the values set for terrain effects
     * @return the filename created
     * @throws IOException if cannot create new file or read old file
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public String createOutputFile(final List<DRTSArchitectureResult> results,
            final List<DisasterEffect> selectedDisasterEffects, final List<TerrainEffect> selectedTerrainEffects)
            throws IOException, InvalidFormatException {
        LOGGER.info("Creating results architecture file.");
        final String filename = "DRTS-Results-" + System.currentTimeMillis() + ".docx";

        // create paths
        final Path templatePath = Paths.get(
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().getResultsDirectory(),
                DisasterResponseTradeStudyPropertiesSingleton.getInstance().getResultsTemplate());

        final Path resultPath = Paths.get(DisasterResponseTradeStudyPropertiesSingleton.getInstance().getResultsDirectory(),
                filename);

        // copy template file
        final File workbookFile = templatePath.toFile();

        if (workbookFile.exists() && !workbookFile.isDirectory() && workbookFile.canRead()) {
            LOGGER.debug("Using results template file: " + templatePath.toAbsolutePath());
            final XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(workbookFile));

            // get table
            final List<XWPFTable> tables = xdoc.getTables();

            // set values
            final DRTSArchitectureResult result = results.get(0);

            // create arch table
            this.createArchTable(result, tables.get(ARCH_RESULT_TABLE_INDEX));

            // create disaster effect table
            this.createSelectedDisasterTable(selectedDisasterEffects, tables.get(SELECTED_DISASTER_TABLE_INDEX));

            // create selected terrain table
            this.createSelectedTerrainTable(selectedTerrainEffects, tables.get(SELECTED_TERRAIN_TABLE_INDEX));

            // create platform weightings table
            this.createOptionWeightingTable(result.getPlatform(), tables.get(PLATFORM_WEIGHTING_TABLE_INDEX));

            // create comm weighting table
            this.createOptionWeightingTable(result.getComms(), tables.get(COMM_WEIGHTING_TABLE_INDEX));

            // create sensor weighting table
            this.createOptionWeightingTable(result.getSensor(), tables.get(SENSOR_WEIGHTING_TABLE_INDEX));

            // create details
            this.createReportDetails(xdoc);

            // write out result
            try (final FileOutputStream outStream = new FileOutputStream(resultPath.toFile())) {
                xdoc.write(outStream);
            }
        }

        return resultPath.toString();
    }

    /**
     *
     * @param result
     */
    private void createArchTable(final DRTSArchitectureResult result, final XWPFTable table) {
        final XWPFTableCell platLabel = table.getRow(1).getCell(0);
        platLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        platLabel.setText(result.getPlatform().getLabel());

        final XWPFTableCell platDetails = table.getRow(1).getCell(2);
        platDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.setAttrCell(platDetails, result.getPlatform().getPrioritizationAttributess());

        final XWPFTableCell commLabel = table.getRow(2).getCell(0);
        commLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        commLabel.setText(result.getComms().getLabel());

        final XWPFTableCell commDetails = table.getRow(2).getCell(2);
        commDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.setAttrCell(commDetails, result.getComms().getPrioritizationAttributess());

        final XWPFTableCell sensorLabel = table.getRow(3).getCell(0);
        sensorLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        sensorLabel.setText(result.getSensor().getLabel());

        final XWPFTableCell sensorDetails = table.getRow(3).getCell(2);
        sensorDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        this.setAttrCell(sensorDetails, result.getSensor().getPrioritizationAttributess());
    }

    /**
     *
     * @param result
     */
    private void createSelectedDisasterTable(final List<DisasterEffect> selectedDisasterEffects, final XWPFTable table) {
        for (final DisasterEffect effect : selectedDisasterEffects) {
            final Integer index = disEffectColsMap.get(effect);

            final XWPFTableCell effectIndicatorCell = table.getRow(index).getCell(1);
            effectIndicatorCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            effectIndicatorCell.setText(CHECKMARK_UNICODE);

            final XWPFTableCell effectDescriptionCell = table.getRow(index).getCell(2);
            effectDescriptionCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            effectDescriptionCell.setText(effect.label);
        }
    }

    /**
     *
     * @param selectedTerrainEffects
     * @param get
     */
    private void createSelectedTerrainTable(final List<TerrainEffect> selectedTerrainEffects, final XWPFTable table) {
        for (final TerrainEffect eff : selectedTerrainEffects) {
            final Integer index = terEffectColsMap.get(eff.terrainLabel);

            final XWPFTableCell effectValueCell = table.getRow(index).getCell(1);
            effectValueCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            effectValueCell.setText(Integer.toString(eff.codeNum));

            final XWPFTableCell effectDescCell = table.getRow(index).getCell(2);
            effectDescCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            effectDescCell.setText(eff.codeMeaning);
        }
    }

    /**
     *
     * @param option
     * @param get
     */
    private void createOptionWeightingTable(final AbstractArchitectureOption option, final XWPFTable table) {
        for (final ArchitectureOptionAttribute attr : option.getPrioritizationAttributess()) {
            final XWPFTableRow row = table.createRow();

            final XWPFTableCell attrLabel = row.getCell(0);
            attrLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            attrLabel.setText(attr.getLabel() + " (" + attr.getUnits() + ")");

            final XWPFTableCell attrWeighting = row.getCell(1);
            attrWeighting.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            attrWeighting.setText(weightingFormat.format(attr.getPriority() * 100) + "%");
            final CTTcPr pr = attrWeighting.getCTTc().addNewTcPr();
            final CTVerticalJc alng = pr.addNewVAlign();
            alng.setVal(STVerticalJc.BOTH);
        }
    }

    /**
     *
     * @param detailsCell
     * @param result
     * @return
     */
    private void setAttrCell(XWPFTableCell detailsCell, final List<ArchitectureOptionAttribute> attrs) {
        for (int x = 0; x < attrs.size(); x++) {
            final ArchitectureOptionAttribute attr = attrs.get(x);

            final XWPFParagraph para;
            if (x == 0) {
                para = detailsCell.getParagraphs().get(0);
            } else {
                para = detailsCell.addParagraph();
            }

            final XWPFRun rh = para.createRun();
            rh.setText(DisasterResponseTradeStudyOutputer.getOutputString(attr));
            para.setAlignment(ParagraphAlignment.CENTER);
        }
    }

    /**
     * Creates the string to use to print in the output file
     *
     * @return the String to use.
     */
    private static String getOutputString(final ArchitectureOptionAttribute attr) {
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

    /**
     *
     * @param xdoc
     */
    private void createReportDetails(final XWPFDocument xdoc) {
        final ZonedDateTime now = ZonedDateTime.now();
        final Locale currentLocale = Locale.getDefault();

        final XWPFParagraph para = xdoc.getParagraphs().get(REPORT_DETAILS_ROW_INDEX);
        final XWPFRun run1 = para.createRun();
        run1.setBold(true);
        run1.setText("Date Report Generated: ");

        final XWPFRun run2 = para.createRun();
        run2.setBold(false);
        run2.setText(formater.format(now));
        run2.addBreak();

        final XWPFRun run3 = para.createRun();
        run3.setBold(true);
        run3.setText("Country Report Generated: ");

        final XWPFRun run4 = para.createRun();
        run4.setBold(false);
        run4.setText(currentLocale.getDisplayCountry());
    }

}
