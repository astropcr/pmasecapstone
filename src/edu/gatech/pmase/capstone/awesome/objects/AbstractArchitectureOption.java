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
     * Gets a list of attributes to prioritize the option with.
     *
     * @return the list of object to prioritize the option with
     */
    public abstract List<ArchitectureOptionAttribute> getPrioritizationAttributess();

    /**
     *
     * @return
     */
    public int getCostRanking() {
        return costRanking;
    }

    /**
     *
     * @param costRanking
     */
    public void setCostRanking(int costRanking) {
        this.costRanking = costRanking;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public List<TerrainEffect> getTerrainLimitation() {
        return terrainLimitation;
    }

    /**
     *
     * @param terrainLimitation
     */
    public void setTerrainLimitation(List<TerrainEffect> terrainLimitation) {
        this.terrainLimitation = terrainLimitation;
    }

    /**
     *
     * @return
     */
    public List<DisasterEffect> getDisasterLimitations() {
        return disasterLimitations;
    }

    /**
     *
     * @param disasterLimitations
     */
    public void setDisasterLimitations(List<DisasterEffect> disasterLimitations) {
        this.disasterLimitations = disasterLimitations;
    }

    /**
     *
     * @return
     */
    public List<ArchitectureOptionAttribute> getCustomAttributes() {
        return customAttributes;
    }

    /**
     *
     * @param customAttributes
     */
    public void setCustomAttributes(List<ArchitectureOptionAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    /**
     *
     * @return
     */
    public double getScore() {
        return score;
    }

    /**
     *
     * @param score
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
        final List<ArchitectureOptionAttribute> attrs = new ArrayList<>();

        // get cost
        final ArchitectureOptionAttribute costAttr = new ArchitectureOptionAttribute();
        costAttr.setColNum(-1);
        costAttr.setLabel("Cost");
        costAttr.setSorting(SortOrderEnum.ASCENDING); // TODO: fix if wrong.
        costAttr.setType(Integer.class);
        costAttr.setUnits("Dollars");
        costAttr.setValue(this.getCostRanking());
        attrs.add(costAttr);

        // add custom attributes
        attrs.addAll(this.getCustomAttributes());

        return attrs;
    }

}
