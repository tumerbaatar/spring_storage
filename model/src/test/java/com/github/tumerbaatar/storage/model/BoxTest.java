package com.github.tumerbaatar.storage.persistence.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BoxTest {

    @Test
    public void testBoxWithSinglePartCreation() throws Exception {
        String boxName = "New box name";
        Box box = new Box(boxName);
        assertEquals(boxName, box.getName());
    }

    @Test
    public void testMovePartFromOneBoxToAnother() throws Exception {

    }

    @Test
    public void testMarkBoxAsSinglePartContainer() throws Exception {

    }

    @Test
    public void testAddMultiplePartsToSinglePartBox() throws Exception {

    }


}