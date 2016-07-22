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
package edu.gatech.pmase.capstone.awesome.impl.ahp;

import edu.gatech.pmase.capstone.awesome.impl.database.CommunicationsDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.impl.database.PlatformDatabaseDriver;
import edu.gatech.pmase.capstone.awesome.objects.ArchitectureOptionAttribute;
import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import edu.gatech.pmase.capstone.awesome.objects.WeightingChoice;
import edu.gatech.pmase.capstone.awesome.util.PrioritizationUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the ComponentAHPOptimator class.
 */
public class ComponentAHPOptimatorTest {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ComponentAHPOptimatorTest.class);

    /**
     * Test attributes.
     */
    private static final List<CommunicationOption> comms = new ArrayList<>();

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
        final PlatformDatabaseDriver platDriver = new PlatformDatabaseDriver();
        final CommunicationsDatabaseDriver commDriver = new CommunicationsDatabaseDriver();

        comms.addAll(commDriver.getCommOptionsFromDatabase(platDriver.getPlatformOptionsFromDatabase()));
    }

    /**
     * Test of generateOptimizedOption method, of class ComponentAHPOptimator.
     */
    @Test
    public void testGenerateOptimizedOption() {
        final List<ArchitectureOptionAttribute> priAttrs = comms.get(0).getPrioritizationAttributess();
        final List<WeightingChoice> options = PrioritizationUtil.getWeightingChoice(priAttrs);

        int size = options.size();
        for (WeightingChoice opt : options) {
            LOGGER.debug("Weighting Option: " + opt.getOptionOneLabel() + "---" + opt.getOptionTwoLabel() + " = " + size);
            opt.setResult(size);
            size--;
        }

        ComponentAHPOptimator instance = new ComponentAHPOptimator();
        List<ArchitectureOptionAttribute> prioritizes = PrioritizationUtil.getPriorityWeightingsForAttributes(options, priAttrs);
        List<PlatformOption> result = instance.generateOptimizedOption(comms, prioritizes);

    }

}
