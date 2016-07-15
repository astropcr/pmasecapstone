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
package edu.gatech.pmase.capstone.awesome.util;

import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the GUIValuesUtil class.
 */
public class GUIValuesUtilTest {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(GUIValuesUtilTest.class);

    /**
     * Platform options from db.
     */
    private static final List<PlatformOption> loadedPlatformOptions = new ArrayList<>();

    @BeforeClass
    public static void setUpClass() {
        final PlatformDatabaseDriver platformDb = new PlatformDatabaseDriver();
        loadedPlatformOptions.addAll(platformDb.getPlatformOptionsFromDatabase());
        LOGGER.debug("Loaded " + loadedPlatformOptions.size() + " PlatformOptions.");
    }

    /**
     * Test of getComponentWeightingOptions method, of class GUIValuesUtil.
     */
    @Test
    public void testGetComponentWeightingOptions() {
        final List<WeightingChoice> result = GUIValuesUtil.getComponentWeightingOptions(loadedPlatformOptions);
        assertEquals(6, result.size());
        LOGGER.debug(result);
    }

}
