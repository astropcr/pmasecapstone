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
import java.util.Optional;

/**
 * Effect that a disaster can create.
 */
public enum DisasterEffect {

    /**
     *
     */
    UNKNOWN("Unknown", 0),
    /**
     *
     */
    FIRE("Fire", 1),
    /**
     *
     */
    FLOOD("Flood", 2),
    /**
     *
     */
    DEBRIS("Debris", 3),
    /**
     *
     */
    SMOKE_DUST("Smoke and Dust", 4),
    /**
     *
     */
    GROUND_INSTABILITY("Ground Instability", 5),
    /**
     *
     */
    LAND_SLIDE("Land Slide", 6),
    /**
     *
     */
    MUD_SLIDE("Mud Slide", 7),
    /**
     *
     */
    STRUCTURAL_DAMAGE("Structural Damage", 8),
    /**
     *
     */
    HIGH_WIND("High Wind", 9),
    /**
     *
     */
    HAZARDOUS_MATERIAL_SPILL("Hazardous Material Spill", 10),
    /**
     *
     */
    RADIOLOGICAL_SPILL("Radiological Spill", 11),
    /**
     *
     */
    LAVA("Lava", 12),
    /**
     *
     */
    ASH("Ash", 13),
    /**
     *
     */
    INFRASTRUCTURE_BREAKDOWN("Infrastructure Breakdown", 14);

    /**
     * Reader Friendly Label.
     */
    public final String label;

    /**
     * The ID of the disaster effect.
     */
    public int id;

    /**
     * Constructor
     *
     * @param inLabel the reader friendly label to use
     * @param inId    the id of the disaster effect
     */
    private DisasterEffect(final String inLabel, final int inId) {
        this.label = inLabel;
        this.id = inId;
    }

    /**
     * Given the ID, returns the associated DisasterEffect.
     *
     * @param inId the ID to find by
     *
     * @return the given Disaster Effect. If none found to match ID, returns
     *         {@link DisasterEffect#UNKNOWN}.
     */
    public static DisasterEffect getEffectById(final int inId) {
        DisasterEffect effect = DisasterEffect.UNKNOWN;

        final Optional<DisasterEffect> result = Arrays.asList(DisasterEffect.
                values())
                .stream()
                .filter(eff -> (eff.id == inId))
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }

    /**
     * Given the label, returns the associated DisasterEffect.
     *
     * @param inLabel the label
     *
     * @return the given Disaster Effect. If none found to match label, returns
     *         {@link DisasterEffect#UNKNOWN}.
     */
    public static DisasterEffect getEffectByLabel(final String inLabel) {
        DisasterEffect effect = DisasterEffect.UNKNOWN;

        final Optional<DisasterEffect> result = Arrays.asList(DisasterEffect.
                values())
                .stream()
                .filter(eff -> eff.label.equals(inLabel))
                .findFirst();
        if (result.isPresent()) {
            effect = result.get();
        }

        return effect;
    }
}
