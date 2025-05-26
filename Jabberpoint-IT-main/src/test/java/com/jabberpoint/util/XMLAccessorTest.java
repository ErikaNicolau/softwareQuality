package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.composite.items.SlideItem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.StringReader;
import org.xml.sax.InputSource;

import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest {

    @TempDir
    File tempDir;

    @Test
    void loadFile_shouldLoadPresentationFromXml() throws IOException {
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

        accessor.loadFile(presentation, tempXmlFile.getAbsolutePath());

        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(2, presentation.getTotalSlides());

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

        accessor.saveFile(presentation, tempXmlFile.getAbsolutePath());

        assertTrue(tempXmlFile.exists());
    }

    @Test
    void loadSlideItem_shouldLoadTextItemWithLevelAndContent() throws Exception {
        String xmlSnippet = "<item kind=\"text\" level=\"2\">Test Text Content</item>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Slide mockSlide = Mockito.mock(Slide.class);
        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("loadSlideItem", Slide.class, Element.class);
        method.setAccessible(true);

        method.invoke(accessor, mockSlide, itemElement);

        Mockito.verify(mockSlide, Mockito.times(1)).append(Mockito.any(TextItem.class));
    }

    @Test
    void loadSlideItem_shouldLoadBitmapItemWithLevelAndContent() throws Exception {
        String xmlSnippet = "<item kind=\"image\" level=\"3\">path/to/image.png</item>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Slide mockSlide = Mockito.mock(Slide.class);
        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("loadSlideItem", Slide.class, Element.class);
        method.setAccessible(true);

        method.invoke(accessor, mockSlide, itemElement);

        Mockito.verify(mockSlide, Mockito.times(1)).append(Mockito.any(BitmapItem.class));
    }

    @Test
    void loadSlideItem_shouldLoadShapeItemWithLevelKindColorAndPosition() throws Exception {
        String xmlSnippet = "<item kind=\"shape\" level=\"1\" shapeType=\"rectangle\" color=\"#FF0000\" x=\"10\" y=\"20\" width=\"50\" height=\"50\">Shape Content</item>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Slide mockSlide = Mockito.mock(Slide.class);
        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("loadSlideItem", Slide.class, Element.class);
        method.setAccessible(true);

        method.invoke(accessor, mockSlide, itemElement);

        Mockito.verify(mockSlide, Mockito.times(1)).append(Mockito.any(ShapeItem.class));
    }

    @Test
    void getItemType_shouldReturnCorrectType() {
        XMLAccessor accessor = new XMLAccessor();
        TextItem textItem = new TextItem(1, "");
        BitmapItem bitmapItem = new BitmapItem(1, "");
        ShapeItem shapeItem = new ShapeItem(1, "rectangle", java.awt.Color.RED);

        assertEquals("text", accessor.getItemType(textItem));
        assertEquals("image", accessor.getItemType(bitmapItem));
        assertEquals("shape", accessor.getItemType(shapeItem));
    }

    @Test
    void getElementText_shouldReturnTextContentForExistingTag() throws Exception {
        String xmlSnippet = "<root><tag>Some Text</tag></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element rootElement = doc.getDocumentElement();

        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("getElementText", Element.class, String.class);
        method.setAccessible(true);

        String textContent = (String) method.invoke(accessor, rootElement, "tag");

        assertEquals("Some Text", textContent);
    }

    @Test
    void getElementText_shouldReturnEmptyStringForNonExistingTag() throws Exception {
        String xmlSnippet = "<root><tag>Some Text</tag></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element rootElement = doc.getDocumentElement();

        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("getElementText", Element.class, String.class);
        method.setAccessible(true);

        String textContent = (String) method.invoke(accessor, rootElement, "nonexistent");

        assertEquals("", textContent);
    }

    @Test
    void loadFile_shouldThrowExceptionForInvalidXml() {
        String invalidXml = "<?xml version=\"1.0\"?>\n" +
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
                          "</presentatio"; // Missing closing bracket

        File tempXmlFile = new File(tempDir, "invalid-presentation.xml");
        try (FileWriter writer = new FileWriter(tempXmlFile)) {
            writer.write(invalidXml);
        } catch (IOException e) {
            fail("Failed to write test file: " + e.getMessage());
        }

        Presentation presentation = new Presentation();
        XMLAccessor accessor = new XMLAccessor();

        assertThrows(IOException.class, () -> 
            accessor.loadFile(presentation, tempXmlFile.getAbsolutePath())
        );
    }

    @Test
    void loadSlideItem_shouldHandleMissingAttributes() throws Exception {
        String xmlSnippet = "<item kind=\"text\">Test Text Content</item>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Slide mockSlide = Mockito.mock(Slide.class);
        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("loadSlideItem", Slide.class, Element.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->
            method.invoke(accessor, mockSlide, itemElement)
        );
        Throwable cause = exception.getCause();
        assertTrue(cause instanceof IllegalArgumentException);
        assertNull(cause.getCause());
    }

    @Test
    void getItemType_shouldReturnUnknownForUnknownItemType() {
        XMLAccessor accessor = new XMLAccessor();
        SlideItem unknownItem = new SlideItem(1) {
            @Override
            public String getContent() {
                return "";
            }

            @Override
            protected Rectangle getDefaultBoundingBox() {
                return new Rectangle(0, 0, 0, 0);
            }

            @Override
            public void draw(java.awt.Graphics g, int x, int y, float scale, java.awt.image.ImageObserver observer) {
            }
        };

        assertEquals("unknown", accessor.getItemType(unknownItem));
    }

    @Test
    void loadSlideItem_shouldHandleInvalidLevel() throws Exception {
        String xmlSnippet = "<item kind=\"text\" level=\"invalid\">Test Text Content</item>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlSnippet));
        Document doc = builder.parse(is);
        Element itemElement = doc.getDocumentElement();

        Slide mockSlide = Mockito.mock(Slide.class);
        XMLAccessor accessor = new XMLAccessor();

        Method method = XMLAccessor.class.getDeclaredMethod("loadSlideItem", Slide.class, Element.class);
        method.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->
            method.invoke(accessor, mockSlide, itemElement)
        );
        Throwable cause = exception.getCause();
        assertTrue(cause instanceof IllegalArgumentException);
        IllegalArgumentException illegalArgumentException = (IllegalArgumentException) cause;
        assertTrue(illegalArgumentException.getCause() instanceof NumberFormatException);
    }
} 