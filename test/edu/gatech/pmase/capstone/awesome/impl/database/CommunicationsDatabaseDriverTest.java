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
package edu.gatech.pmase.capstone.awesome.impl.database;

import edu.gatech.pmase.capstone.awesome.objects.CommunicationOption;
import edu.gatech.pmase.capstone.awesome.objects.PlatformOption;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests the CommunicationsDatabaseDriver class.
 */
public class CommunicationsDatabaseDriverTest {

    /**
     * Test of getCommOptions method, of class CommunicationsDatabaseDriver.
     */
    @Test
    public void testGetCommOptions() {
        final List<PlatformOption> platformOptions = new ArrayList<>(2);

        final PlatformOption one = new PlatformOption();
        one.setId(1);
        platformOptions.add(one);

        final PlatformOption two = new PlatformOption();
        two.setId(2);
        platformOptions.add(two);

        final PlatformOption three = new PlatformOption();
        three.setId(3);
        platformOptions.add(three);

        final PlatformOption four = new PlatformOption();
        four.setId(4);
        platformOptions.add(four);

        final PlatformOption five = new PlatformOption();
        five.setId(5);
        platformOptions.add(five);

        final PlatformOption six = new PlatformOption();
        six.setId(6);
        platformOptions.add(six);

        final PlatformOption sev = new PlatformOption();
        sev.setId(7);
        platformOptions.add(sev);

        final PlatformOption eight = new PlatformOption();
        eight.setId(8);
        platformOptions.add(eight);

        final PlatformOption nine = new PlatformOption();
        nine.setId(9);
        platformOptions.add(nine);

        final CommunicationsDatabaseDriver instance = new CommunicationsDatabaseDriver();
        List<CommunicationOption> result = instance.getCommOptionsFromDatabase(
                platformOptions);

        assertEquals(10, result.size());

    }

}
