package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class test {

    PRAndroidLogin login = new PRAndroidLogin();
    PRAndroidAddAnimal add =  new PRAndroidAddAnimal();
    PRAndroidTransferAnimal transfer = new PRAndroidTransferAnimal();

    PBiOSLogin in = new PBiOSLogin();
    PBiOSEditAnimal edit = new PBiOSEditAnimal();
    @Before
    public void setUp() throws Exception {

        login.setUp();
        in.setUp();

        add.setUp(login.getDriver());
        transfer.setUp(login.getDriver());

        edit.setUp(in.getDriver());



    }

    @After
    public void tearDown() {
        in.tearDown();
        login.tearDown();

    }

    @Test
    public void test() throws Exception {
        // Master branch leaves this empty for now -PG
       // login.login("https://ranch.***REMOVED***", "***REMOVED***", "***REMOVED***");
        //add.addAnimal("Pear");
       // transfer.transferAnimal("Pear");

        in.login("beta","***REMOVED***", "***REMOVED***");
       // edit.changeAnimalGender("Pear");
       // edit.deleteAnimal("Pear");







    }
}
