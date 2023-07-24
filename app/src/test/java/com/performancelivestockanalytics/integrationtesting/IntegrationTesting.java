package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IntegrationTesting {


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void test() throws Exception {
        PRWebLogin login = new PRWebLogin();
        login.logIn("https://ranch.***REMOVED***", "***REMOVED***", "***REMOVED***");

        //new PRWebAddAnimal(login.getDriver()).addAnimalTransfer("2023-05-28", "This is a Test", "Steer", "Wagyu");

        /**
         * Empty Strings = herd , pasture , birthPasture
         */
        new PRWebAddAnimal(login.getDriver()).addCalf("2023-05-28", "Testing 00001", "", "BRANDTATTOO", "",
                "Bull", "", "", "", "", "testbull",
                "OnlyVidDam", "5", "Wagyu", "WHITE", "Horned",
                "1000kg", "", "Dam only", "Assisted, Easy", "Checking ah ah ah");

        login.tearDown();
    }
}