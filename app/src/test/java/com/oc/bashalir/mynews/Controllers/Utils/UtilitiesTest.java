package com.oc.bashalir.mynews.Controllers.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilitiesTest {
    @Test
    public void DateShortFormatterTest(){

        assertEquals("20/06/2018",new Utilities().DateShortFormatter("2018-06-20T08:47:32-04:00","yyyy-MM-dd'T'HH:mm:ssZZZZZ"));

    }

}