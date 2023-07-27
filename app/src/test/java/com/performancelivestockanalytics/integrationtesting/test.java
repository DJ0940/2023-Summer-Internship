package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class test {

    PBiOSEditAnimal edit = new PBiOSEditAnimal();
    PBiOSLogin in = new PBiOSLogin();

    @Before
    public void setUp() throws Exception {
        in.setUp();
        edit.setUp(in.getDriver());
    }

    @After
    public void tearDown() {
        in.tearDown();
    }

    @Test
    public void test() throws Exception {
        // Master branch leaves this empty for now -PG

        in.login("beta\n", "***REMOVED***", "***REMOVED***");
        edit.changeWeight(50);
    }
}
