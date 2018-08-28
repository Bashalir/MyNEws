package com.oc.bashalir.mynews.Controllers.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {
    @Test
    public void DateShortFormatterTest1(){

       assertEquals("24/07/18",new Utilities().DateShortFormatter("2018-07-23T22:54:20+0000","yyyy-MM-dd'T'HH:mm:ssZZZZZ"));
    }
    @Test
    public void DateShortFormatterTest2(){

        assertEquals("24/08/12",new Utilities().DateShortFormatter("2012-08-24T00:00:00Z","yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    @Test
    public void TestofDateFormatterSearch(){

        assertEquals("20180828",new Utilities().DateFormatterSearch("2018-08-28T00:00:20+0000","yyyy-MM-dd'T'HH:mm:ssZZZZZ"));
    }


}