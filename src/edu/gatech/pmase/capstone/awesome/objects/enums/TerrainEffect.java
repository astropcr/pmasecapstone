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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Different types of Terrain effects.
 */
public enum TerrainEffect {

    /**
     * Unknown
     */
    UNKNOWN(0, "Unknown", 0, "Unknown"),
    /**
     * Elevation Options. - Terrain ID 1
     */
    ELEVATION_0(1, "Elevation", 0, "No Factor"),
    ELEVATION_1(1, "Elevation", 1, "Minimal elevation changes (+/- 1000 feet)"),
    ELEVATION_2(1, "Elevation", 2, "Moderate elevation change (+/- 5000 feet)"),
    ELEVATION_3(1, "Elevation", 3, "Severe elevation change (+/- 10,000 feet)"),
    /**
     * Slope Options. - Terrain ID 2
     */
    SLOPE_0(2, "Slope", 0, "No Factor"),
    SLOPE_1(2, "Slope", 1, "Level Terrain (≤ 10° )"),
    SLOPE_2(2, "Slope", 2, "Medium Terrain (10° < slope ≤ 45° )"),
    SLOPE_3(2, "Slope", 3, "Steep Terrain (> 45° )"),
    /**
     * Roads Options. - Terrain ID 3
     */
    ROADS_0(3, "Roads", 0, "No Factor"),
    ROADS_1(3, "Roads", 1, "Majority of roadways are paved"),
    ROADS_2(3, "Roads", 2, "Majority of roadways are unimproved"),
    ROADS_3(3, "Roads", 3, "Few or no roads exist in the disaster area"),
    /**
     * Trafficability Options. - Terrain ID 4
     */
    TRAFFICABILITY_0(4, "Trafficability", 0, "No Factor"),
    TRAFFICABILITY_1(4, "Trafficability", 1, "Roadways are clear"),
    TRAFFICABILITY_2(4, "Trafficability", 2, "Roadways are partially obstructed"),
    TRAFFICABILITY_3(4, "Trafficability", 3, "Roadways are nearly impassible"),
    /**
     * Bridges Options. - Terrain ID 5
     */
    BRIDGES_0(5, "Bridges", 0, "No Factor"),
    BRIDGES_1(5, "Bridges", 1, "Bridges handle heavy traffic"),
    BRIDGES_2(5, "Bridges", 2, "Bridges handle medium traffic"),
    BRIDGES_3(5, "Bridges", 3, "Bridges handle only foot traffic"),
    /**
     * Foliage Options. - Terrain ID 6
     */
    FOLIAGE_0(6, "Foliage", 0, "No Factor"),
    FOLIAGE_1(6, "Foliage", 1, "Completely open"),
    FOLIAGE_2(6, "Foliage", 2, "Partial coverage due to foliage"),
    FOLIAGE_3(6, "Foliage", 3, "Complete layer of canopy over disaster area"),
    FOLIAGE_4(6, "Foliage", 4, "Triple jungle-like canopy over disaster area"),
    /**
     * Wetness Options. - Terrain ID 7
     */
    WETNESS_0(7, "Wetness", 0, "No Factor"),
    WETNESS_1(7, "Wetness", 1, "Terrain is dry"),
    WETNESS_2(7, "Wetness", 2, "Terrain has some areas of saturated soil"),
    WETNESS_3(7, "Wetness", 3, "Terrain consists of extreme marsh-like conditions "),
    /**
     * Water Ways Options. - Terrain ID 8
     */
    WATERWAYS_0(8, "Water Ways", 0, "No Factor"),
    WATERWAYS_1(8, "Water Ways", 1, "Water ways are navigable"),
    WATERWAYS_2(8, "Water Ways", 2, "Water ways are unnavigable"),
    /**
     * Streams Options. - Terrain ID 9
     */
    STREAMS_0(9, "Streams", 0, "No Factor"),
    STREAMS_1(9, "Streams", 1, "Streams have bridge crossings"),
    STREAMS_2(9, "Streams", 2, "Streams have no bridges crossings"),
    /**
     * Beach Options. - Terrain ID 10
     */
    BEACH_0(10, "Beach", 0, "No Factor"),
    BEACH_1(10, "Beach", 1, "Gradual Sand or Pebble"),
    BEACH_2(10, "Beach", 2, "Steeper Sand or Pebble"),
    BEACH_3(10, "Beach", 3, "Steep Sand or Pebble"),
    BEACH_4(10, "Beach", 4, "Large Stone or Cliff "),
    /**
     * Population Options. - Terrain ID 11
     */
    POPULATION_0(11, "Population", 0, "No Factor"),
    POPULATION_1(11, "Population", 1, "Low population level"),
    POPULATION_2(11, "Population", 2, "Medium population level"),
    POPULATION_3(11, "Population", 3, "High population level"),
    /**
     * Urbanization Options. - Terrain ID 12
     */
    URBANIZATION_0(12, "Urbanization", 0, "No Factor"),
    URBANIZATION_1(12, "Urbanization", 1, "Open area (very sparsely populated)"),
    URBANIZATION_2(12, "Urbanization", 2, "Suburban area"),
    URBANIZATION_3(12, "Urbanization", 3, "Urban area"),
    URBANIZATION_4(12, "Urbanization", 4, "Dense urban area"),
    /**
     * Range Options. - Terrain ID 13
     */
    RANGE_0(13, "Range", 0, "No Factor"),
    RANGE_1(13, "Range", 1, "Distance from staging area to disaster < 50 nm"),
    RANGE_2(13, "Range", 2, "Distance from staging area to disaster 50-100 nm"),
    RANGE_3(13, "Range", 3, "Distance from staging area to disaster > 100 nm"),
    /**
     * Persistance Options. - Terrain ID 14
     */
    PERSISTANCE_0(14, "Persistance", 0, "No Factor"),
    PERSISTANCE_1(14, "Persistance", 1, "Short duration persistence over/at disaster area"),
    PERSISTANCE_2(14, "Persistance", 2, "Medium duration persistence over/at disaster area"),
    PERSISTANCE_3(14, "Persistance", 3, "Long duration persistence overhead/at disaster area");

    /**
     * ID of the Terrain Effect.
     */
    public int terrainId;

    /**
     * Reader-friendly terrainLabel.
     */
    public String terrainLabel;

    /**
     * The Code Number.
     */
    public int codeNum;

    /**
     * The meaning of the code.
     */
    public String codeMeaning;

    /**
     * Private Constructor for TerainEffect.
     *
     * @param terrainId the ID of the Terrain Effect.
     * @param terrainLabel Reader-friendly terrainLabel.
     * @param codeNum The Code Number.
     * @param meaning The meaning of the code.
     */
    private TerrainEffect(final int terrainId, final String terrainLabel, final int codeNum, final String meaning) {
        this.terrainId = terrainId;
        this.terrainLabel = terrainLabel;
        this.codeNum = codeNum;
        this.codeMeaning = meaning;
    }

    /**
     * Gets all TerrainEffects with the given label.
     *
     * @param label the label for the TerrainEffect
     * @return a List of TerrainEffects with the given label.
     */
    public static List<TerrainEffect> getEffectByLabel(final String label) {
        return Arrays.asList(TerrainEffect.values()).stream()
                .filter(eff -> eff.terrainLabel.equals(label))
                .collect(Collectors.toList());
    }

    /**
     * Returns a Set with each unique effect label.
     *
     * @return the Set of effect label
     */
    public static Set<String> getEffectLabels() {
        return Arrays.asList(TerrainEffect.values())
                .stream().filter(eff -> eff.terrainId != TerrainEffect.UNKNOWN.terrainId)
                .map(eff -> eff.terrainLabel)
                .collect(Collectors.toSet());
    }

    /**
     * Given the ID and a code returns the associated TerrainEffect.
     *
     * @param inId the ID to find by
     * @param inCode the code to find by
     * @return the given Terrain Effect. If none found to match ID and code,
     * returns {@link TerrainEffect#UNKNOWN}.
     */
    public static TerrainEffect getEffectByIdAndCode(final int inId, final int inCode) {
        TerrainEffect effect = TerrainEffect.UNKNOWN;

        final Optional<TerrainEffect> result = Arrays.asList(TerrainEffect.values())
                .stream()
                .filter(eff -> (eff.terrainId == inId && eff.codeNum == inCode))
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }

}
