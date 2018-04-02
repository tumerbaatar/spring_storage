package com.github.tumerbaatar.storage.service;

import com.github.tumerbaatar.storage.persistence.model.Box;
import com.github.tumerbaatar.storage.persistence.model.Part;
import com.github.tumerbaatar.storage.persistence.repository.BoxRepository;
import com.github.tumerbaatar.storage.persistence.repository.PartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PermanentHashProviderTest {
    @MockBean
    private PartRepository partRepository;
    @MockBean
    private BoxRepository boxRepository;
    @InjectMocks
    private PermanentHashProvider hashProvider;

    private List<Part> partList = new ArrayList<>();
    private List<Box> boxList = new ArrayList<>();

    private final int partCount = 5;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        for (int i = 1; i <= partCount; i++) {
            Part part = new Part();
            part.setId(i);
            part.setName("part name " + i);
            part.setDescription("description " + i);
            partList.add(part);

            Box box = new Box();
            box.setId(i);
            box.setName(i + "box name ");
            boxList.add(box);
        }
    }

    @Test
    public void setPermanentHashForPart() {
        Part part = partList.get(0);
        when(partRepository.findByPermanentHash(part.getPermanentHash())).thenReturn(part);
        part = hashProvider.setPermanentHash(part);
        assertNotNull(part.getPermanentHash());
    }

    @Test
    public void setPermanentHashForBox() {
        Box box = boxList.get(0);
        when(boxRepository.findByPermanentHash(box.getPermanentHash())).thenReturn(box);
        box = hashProvider.setPermanentHash(box);
        assertNotNull(box.getPermanentHash());
    }

    @Test
    public void setPermanentHashForPartWithCollision() {
        Part part = partList.get(0);
        when(partRepository.findByPermanentHash(part.getPermanentHash())).thenReturn(part);
        part = hashProvider.setPermanentHash(part);
        String permanentHash = part.getPermanentHash();
        assertNotNull(part.getPermanentHash());

        when(partRepository.findByPermanentHash(part.getPermanentHash())).thenReturn(part);
        when(partRepository.findByPermanentHash(permanentHash+"0")).thenReturn(null);
        when(partRepository.findByPermanentHash(permanentHash+"01")).thenReturn(null);
        Part partWithCollision = hashProvider.setPermanentHash(part);

        assertNotEquals(permanentHash, partWithCollision.getPermanentHash());
    }

}