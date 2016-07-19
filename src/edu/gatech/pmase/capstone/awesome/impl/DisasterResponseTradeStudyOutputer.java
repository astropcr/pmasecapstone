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

import edu.gatech.pmase.capstone.awesome.objects.DRTSArchitectureResult;
import edu.gatech.pmase.capstone.awesome.util.DisasterResponseTradeStudyPropertiesSingleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

/**
 * Outputs the results of Trade Study into a file.
 */
public class DisasterResponseTradeStudyOutputer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DisasterResponseTradeStudyOutputer.class);

    /**
     * Creates the output file from the results of the Trade Study.
     *
     * @param results the resulting architectures
     * @return the filename created
     * @throws IOException if cannot create new file or read old file
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public String createOutputFile(final List<DRTSArchitectureResult> results) throws IOException, InvalidFormatException {
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
            final XWPFTableCell platLabel = tables.get(0).getRow(1).getCell(0);
            platLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            platLabel.setText(results.get(0).getPlatform().getLabel());

            final XWPFTableCell platDetails = tables.get(0).getRow(1).getCell(2);
            platDetails.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            platDetails.setText(results.get(0).getPlatform().getOutputString());

            final XWPFTableCell commLabel = tables.get(0).getRow(2).getCell(0);
            commLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            commLabel.setText(results.get(0).getComms().getLabel());

            final XWPFTableCell sensorLabel = tables.get(0).getRow(3).getCell(0);
            sensorLabel.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            sensorLabel.setText(results.get(0).getSensor().getLabel());

            // write out result
            try (final FileOutputStream outStream = new FileOutputStream(resultPath.toFile())) {
                xdoc.write(outStream);
            }
        }

        return resultPath.toString();
    }

}
