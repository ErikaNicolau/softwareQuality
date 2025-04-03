package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest {
    private XMLAccessor accessor;
    private Presentation presentation;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        accessor = new XMLAccessor();
        presentation = new Presentation();
        
        // Create a sample presentation with two slides
        Slide slide1 = new Slide();
        slide1.setTitle("Test Slide 1");
        presentation.append(slide1);
        
        Slide slide2 = new Slide();
        slide2.setTitle("Test Slide 2");
        presentation.append(slide2);
    }

    @Test
    void testSaveAndLoadPresentation() throws IOException {
        File tempFile = tempDir.resolve("test.xml").toFile();
        
        // Save the presentation
        accessor.saveFile(presentation, tempFile.getPath());
        assertTrue(tempFile.exists());
        
        // Load the presentation
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, tempFile.getPath());
        assertNotNull(loadedPresentation);
        assertEquals(2, loadedPresentation.getTotalSlides());
    }

    @Test
    void testLoadNonExistentFile() {
        Presentation loadedPresentation = new Presentation();
        assertThrows(IOException.class, () -> {
            accessor.loadFile(loadedPresentation, "nonexistent.xml");
        });
    }

    @Test
    void testSaveToInvalidPath() {
        assertThrows(IOException.class, () -> {
            accessor.saveFile(presentation, "/invalid/path/test.xml");
        });
    }
} 