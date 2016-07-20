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
    UNKNWON(0, "Unknown", 0, "Unknown"),
    /**
     * Elevation Options. - Terrain ID 1
     */
    ELEVATION_0(1, "Elevation", 0, "No Value"),
    ELEVATION_1(1, "Elevation", 1, "Low"),
    ELEVATION_2(1, "Elevation", 2, "Medium"),
    ELEVATION_3(1, "Elevation", 3, "High"),
    /**
     * Slope Options. - Terrain ID 2
     */
    SLOPE_0(2, "Slope", 0, "No Value"),
    SLOPE_1(2, "Slope", 1, "Low"),
    SLOPE_2(2, "Slope", 2, "Medium"),
    SLOPE_3(2, "Slope", 3, "High"),
    /**
     * Roads Options. - Terrain ID 3
     */
    ROADS_0(3, "Roads", 0, "No Value"),
    ROADS_1(3, "Roads", 1, "Paved"),
    ROADS_2(3, "Roads", 2, "Unimproved"),
    ROADS_3(3, "Roads", 3, "Few or None"),
    /**
     * Trafficability Options. - Terrain ID 4
     */
    TRAFFICABILITY_0(4, "Trafficability", 0, "No Value"),
    TRAFFICABILITY_1(4, "Trafficability", 1, "Clear"),
    TRAFFICABILITY_2(4, "Trafficability", 2, "Partially Obstructed"),
    TRAFFICABILITY_3(4, "Trafficability", 3, "Obstructed"),
    /**
     * Bridges Options. - Terrain ID 5
     */
    BRIDGES_0(5, "Bridges", 0, "No Value"),
    BRIDGES_1(5, "Bridges", 1, "Heavy Traffic"),
    BRIDGES_2(5, "Bridges", 2, "Medium Traffic"),
    BRIDGES_3(5, "Bridges", 3, "Foot"),
    /**
     * Foliage Options. - Terrain ID 6
     */
    FOLIAGE_0(6, "Foliage", 0, "No Value"),
    FOLIAGE_1(6, "Foliage", 1, "Open"),
    FOLIAGE_2(6, "Foliage", 2, "Partial"),
    FOLIAGE_3(6, "Foliage", 3, "Complete"),
    FOLIAGE_4(6, "Foliage", 4, "Triple Canopy"),
    /**
     * Wetness Options. - Terrain ID 7
     */
    WETNESS_0(7, "Wetness", 0, "No Value"),
    WETNESS_1(7, "Wetness", 1, "Dry"),
    WETNESS_2(7, "Wetness", 2, "Areas of Wetness"),
    WETNESS_3(7, "Wetness", 3, "Marsh "),
    /**
     * Water Ways Options. - Terrain ID 8
     */
    WATERWAYS_0(8, "Water Ways", 0, "No Value"),
    WATERWAYS_1(8, "Water Ways", 1, "Navigable"),
    WATERWAYS_2(8, "Water Ways", 2, "Unnavigable"),
    /**
     * Streams Options. - Terrain ID 9
     */
    STREAMS_0(9, "Streams", 0, "No Value"),
    STREAMS_1(9, "Streams", 1, "Bridged"),
    STREAMS_2(9, "Streams", 2, "Unbridged"),
    /**
     * Beach Options. - Terrain ID 10
     */
    BEACH_0(10, "Beach", 0, "No Value"),
    BEACH_1(10, "Beach", 1, "Gradual Sand or Pebble"),
    BEACH_2(10, "Beach", 2, "Steeper Sand or Pebble"),
    BEACH_3(10, "Beach", 3, "Steep Sand or Pebble"),
    BEACH_4(10, "Beach", 4, "Large Stone or Cliff "),
    /**
     * Population Options. - Terrain ID 11
     */
    POPULATION_0(11, "Population", 0, "No Value"),
    POPULATION_1(11, "Population", 1, "Low"),
    POPULATION_2(11, "Population", 2, "Medium"),
    POPULATION_3(11, "Population", 3, "High"),
    /**
     * Urbanization Options. - Terrain ID 12
     */
    URBANIZATION_0(12, "Urbanization", 0, "No Value"),
    URBANIZATION_1(12, "Urbanization", 1, "Open"),
    URBANIZATION_2(12, "Urbanization", 2, "Suburban"),
    URBANIZATION_3(12, "Urbanization", 3, "Urban"),
    URBANIZATION_4(12, "Urbanization", 4, "Dense Urban"),
    /**
     * Range Options. - Terrain ID 13
     */
    RANGE_0(13, "Range", 0, "No Value"),
    RANGE_1(13, "Range", 1, "Near"),
    RANGE_2(13, "Range", 2, "Medium"),
    RANGE_3(13, "Range", 3, "Far"),
    /**
     * Persistance Options. - Terrain ID 14
     */
    PERSISTANCE_0(14, "Persistance", 0, "No Value"),
    PERSISTANCE_1(14, "Persistance", 1, "Short"),
    PERSISTANCE_2(14, "Persistance", 2, "Medium"),
    PERSISTANCE_3(14, "Persistance", 3, "Long");

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
                .stream().filter(eff -> eff.terrainId != TerrainEffect.UNKNWON.terrainId)
                .map(eff -> eff.terrainLabel)
                .collect(Collectors.toSet());
    }

    /**
     * Given the ID and a code returns the associated TerrainEffect.
     *
     * @param inId the ID to find by
     * @param inCode the code to find by
     * @return the given Terrain Effect. If none found to match ID and code,
     * returns {@link TerrainEffect#UNKNWON}.
     */
    public static TerrainEffect getEffectByIdAndCode(final int inId, final int inCode) {
        TerrainEffect effect = TerrainEffect.UNKNWON;

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
