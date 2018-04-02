package com.github.tumerbaatar.storage.persistence.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
public class PartTest {

    @Test
    public void testSparePartCreation() {
        assertTrue(true);
    }

    @Test
    public void testAddPartToBox() throws Exception {
        String partNumber = "Part number";
        Part part = new Part(partNumber);
        part.setPrice(new BigDecimal(300));

        String boxName = "New box name";
        Box box = new Box(boxName);

        // TODO: 27.02.2018 reimplement test
        /*
        assertThat(part.getBoxes(), hasItem(box));
        assertThat(box.getParts(), hasItem(part));
        */
    }

    @Test
    public void testRemovePartFromBox() throws Exception {
        String boxName = "New box name";
        Box box = new Box(boxName);

        String partNumber = "DC280009KS0";
        Part part = new Part(partNumber);

        int price = 300;
        part.setPrice(new BigDecimal(price));

        // TODO: 27.02.2018 reimplement test
        /*
        part.addBox(box);
        box.addPart(part);

        assertThat(box.getParts(), hasItem(part));
        part.removeBox(box);
        box.removePart(part);
        assertThat(box.getParts(), not(hasItem(part)));
        */
    }

}