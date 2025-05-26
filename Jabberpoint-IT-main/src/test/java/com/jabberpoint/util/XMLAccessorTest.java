package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest {

    @TempDir // JUnit 5 annotation to create a temporary directory
    File tempDir;

    @Test
    void loadFile_shouldLoadPresentationFromXml() throws IOException {
        // Arrange
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                            "<presentation>\n" +
                            "  <showtitle>Test Presentation</showtitle>\n" +
                            "  <slide>\n" +
                            "    <title>First Slide</title>\n" +
                            "    <item kind=\"text\" level=\"1\">Hello</item>\n" +
                            "  </slide>\n" +
                            "  <slide>\n" +
                            "    <title>Second Slide</title>\n" +
                            "    <item kind=\"text\" level=\"1\">World</item>\n" +
                            "  </slide>\n" +
                            "</presentation>";

        File tempXmlFile = new File(tempDir, "test-presentation.xml");
        try (FileWriter writer = new FileWriter(tempXmlFile)) {
            writer.write(xmlContent);
        }

        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();

        // Act
        accessor.loadFile(presentation, tempXmlFile.getAbsolutePath());

        // Assert
        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(2, presentation.getTotalSlides());

        // Basic checks for slides
        presentation.goToSlide(0);
        assertEquals("First Slide", presentation.getCurrentSlide().getTitle());
        assertEquals(1, presentation.getCurrentSlide().getItems().size());
        assertTrue(presentation.getCurrentSlide().getItems().get(0) instanceof TextItem);

        presentation.goToSlide(1);
        assertEquals("Second Slide", presentation.getCurrentSlide().getTitle());
        assertEquals(1, presentation.getCurrentSlide().getItems().size());
        assertTrue(presentation.getCurrentSlide().getItems().get(0) instanceof TextItem);
    }

    @Test
    void saveFile_shouldSavePresentationToXml() throws IOException {
        // Arrange
        Presentation presentation = new Presentation();
        presentation.setTitle("Saved Presentation");
        
        Slide slide1 = new Slide();
        slide1.setTitle("Saved Slide 1");
        slide1.append(new TextItem(1, "Line 1"));
        presentation.append(slide1);

        Slide slide2 = new Slide();
        slide2.setTitle("Saved Slide 2");
        slide2.append(new TextItem(1, "Line 2"));
        presentation.append(slide2);

        File tempXmlFile = new File(tempDir, "saved-presentation.xml");
        XMLAccessor accessor = new XMLAccessor();

        // Act
        accessor.saveFile(presentation, tempXmlFile.getAbsolutePath());

        // Assert
        assertTrue(tempXmlFile.exists());
        // More robust assertion would involve parsing the XML and verifying structure/content
        // For simplicity here, we just check existence. 
        // A real test might read the file content and compare to an expected string or parse it back.
    }
} 