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
package edu.gatech.pmase.capstone.awesome.objects.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the TerrainEffect class.
 */
public class TerrainEffectTest {

    /**
     * Logger. Use to log all things
     */
    private static final Logger LOGGER = LogManager.getLogger(TerrainEffectTest.class);

    /**
     * Test of getEffectById method, of class TerrainEffect.
     */
    @Test
    public void testGetEffectById() {
        int inId = 1;
        int inCode = 0;
        TerrainEffect expResult = TerrainEffect.ELEVATION_0;
        TerrainEffect result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = 2;
        inCode = 2;
        expResult = TerrainEffect.SLOPE_2;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = 3;
        inCode = 3;
        expResult = TerrainEffect.ROADS_3;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = 4;
        inCode = 1;
        expResult = TerrainEffect.TRAFFICABILITY_1;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = 6;
        inCode = 4;
        expResult = TerrainEffect.FOLIAGE_4;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = 50;
        inCode = 0;
        expResult = TerrainEffect.UNKNWON;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);

        inId = -1;
        inCode = -1;
        expResult = TerrainEffect.UNKNWON;
        result = TerrainEffect.getEffectByIdAndCode(inId, inCode);
        LOGGER.info("Found terrain effect: " + result.terrainLabel + " with code: " + result.codeNum);
        assertEquals(expResult, result);
    }

}
