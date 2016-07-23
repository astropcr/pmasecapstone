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
package edu.gatech.pmase.capstone.awesome.objects;

import edu.gatech.pmase.capstone.awesome.objects.enums.DisasterEffect;
import edu.gatech.pmase.capstone.awesome.objects.enums.SortOrderEnum;
import edu.gatech.pmase.capstone.awesome.objects.enums.TerrainEffect;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all Architecture options read in from Tech Market Survey
 * Based Spreadsheets.
 */
public abstract class AbstractArchitectureOption implements Comparable<AbstractArchitectureOption> {

    /**
     * Sep. used between output attributes.
     */
    protected static final String OUTPUT_STRING_ATTR_SEP = ", ";

    /**
     * The ranking (?-?) of the cost.
     */
    private int costRanking = -1;

    /**
     * Name of the option.
     */
    private String label = "";

    /**
     * ID of option.
     */
    private long id = -1;

    /**
     * Prioritized score.
     */
    private double score = 0.0;

    /**
     * Limitations of the architecture option based upon the terrain.
     */
    private List<TerrainEffect> terrainLimitation = new ArrayList<>(0);

    /**
     * Limitations of the architecture based upon the disaster.
     */
    private List<DisasterEffect> disasterLimitations = new ArrayList<>(0);

    /**
     * List of custom attributes.
     */
    private List<ArchitectureOptionAttribute> customAttributes = new ArrayList<>(0);

    /**
     * Base Prioritization Attributes.
     */
    private List<ArchitectureOptionAttribute> basePrioritizationAttributes = null;

    /**
     * Gets a list of attributes to prioritize the option with.
     *
     * @return the list of object to prioritize the option with
     */
    public abstract List<ArchitectureOptionAttribute> getPrioritizationAttributess();

    /**
     * Gets the ranking of cost. Ranked from value 1 to 5 with 1 being lowest
     * and 5 highest cost.
     *
     * @return the ranking of cost.
     */
    public int getCostRanking() {
        return costRanking;
    }

    /**
     * Sets the ranking of cost.
     *
     * @param costRanking the ranking of cost.
     */
    public void setCostRanking(int costRanking) {
        this.costRanking = costRanking;
    }

    /**
     * Gets the label, must be unique.
     *
     * @return the label, must be unique.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label, must be unique.
     *
     * @param label the label, must be unique.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the ID, must be unique.
     *
     * @return the ID, must be unique.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID, must be unique.
     *
     * @param id the ID, must be unique.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the list of Terrain Limitations for the architecture option.
     *
     * @return the list of Terrain Limitations for the architecture option.
     */
    public List<TerrainEffect> getTerrainLimitation() {
        return terrainLimitation;
    }

    /**
     * Sets the list of Terrain Limitations for the architecture option.
     *
     * @param terrainLimitation the list of Terrain Limitations for the
     *                          architecture option.
     */
    public void setTerrainLimitation(List<TerrainEffect> terrainLimitation) {
        this.terrainLimitation = terrainLimitation;
    }

    /**
     * Gets the disaster limitations for the architecture option.
     *
     * @return the disaster limitations for the architecture option.
     */
    public List<DisasterEffect> getDisasterLimitations() {
        return disasterLimitations;
    }

    /**
     * Sets the disaster limitations for the architecture option.
     *
     * @param disasterLimitations the disaster limitations for the architecture
     *                            option.
     */
    public void setDisasterLimitations(List<DisasterEffect> disasterLimitations) {
        this.disasterLimitations = disasterLimitations;
    }

    /**
     * Gets a list of custom attributes as defined in the database.
     *
     * @return a list of custom attributes as defined in the database.
     */
    public List<ArchitectureOptionAttribute> getCustomAttributes() {
        return customAttributes;
    }

    /**
     * Sets a list of custom attributes as defined in the database.
     *
     * @param customAttributes a list of custom attributes as defined in the
     *                         database.
     */
    public void setCustomAttributes(
            List<ArchitectureOptionAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    /**
     * Gets the calculated score for the architecture option.
     *
     * @return the calculated score for the architecture option.
     */
    public double getScore() {
        return score;
    }

    /**
     * Set the calculated score for the architecture option.
     *
     * @param score the calculated score for the architecture option.
     */
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ArchitectureOption{" + "label=" + label + ", id=" + id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final AbstractArchitectureOption other = (AbstractArchitectureOption) obj;
        if (this.id != other.id) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(final AbstractArchitectureOption other) {
        if (this.score == other.getScore()) {
            return 0;
        } else {
            return this.score < other.getScore() ? 1 : -1;
        }
    }

    /**
     * Returns the default attributes to prioritize upon.
     *
     * @return the list of default attributes
     */
    protected List<ArchitectureOptionAttribute> getBasePrioritizationAttributes() {
        if (null == basePrioritizationAttributes) {
            final List<ArchitectureOptionAttribute> attrs = new ArrayList<>();

            // get cost
            final ArchitectureOptionAttribute costAttr = new ArchitectureOptionAttribute();
            costAttr.setColNum(-1);
            costAttr.setLabel("Cost Rank");
            costAttr.setSorting(SortOrderEnum.ASCENDING);
            costAttr.setType(Integer.class);
            costAttr.setUnits("[1 < 5]");
            costAttr.setValue(this.getCostRanking());
            costAttr.setOriginalValue(this.getCostRanking());
            attrs.add(costAttr);

            // add custom attributes
            attrs.addAll(this.getCustomAttributes());

            // set
            basePrioritizationAttributes = attrs;
        }

        return basePrioritizationAttributes;
    }

}
