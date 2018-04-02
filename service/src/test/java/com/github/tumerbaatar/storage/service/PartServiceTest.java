package com.github.tumerbaatar.storage.service;

import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.repository.BoxRepository;
import com.github.tumerbaatar.storage.repository.PartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PartServiceTest {
    @InjectMocks
    private PartService partService;
    @MockBean
    private PartRepository partRepository;
    @MockBean
    private BoxRepository boxRepository;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPart() throws Exception {
        String partNumber = "LP3930393002";
        Part part = new Part(partNumber);
//        Part created = partRepository.

    }

    @Test
    public void addInventory() throws Exception {

    }

    @Test
    public void removeInventory() throws Exception {
    }

    @Test
    public void moveInventory() throws Exception {
    }

}