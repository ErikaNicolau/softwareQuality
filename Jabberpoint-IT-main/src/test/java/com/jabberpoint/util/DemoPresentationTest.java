package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.composite.items.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {

    private Presentation demoPresentation;

    @BeforeEach
    void setUp() {
        demoPresentation = DemoPresentation.createDemoPresentation();
    }

    @Test
    void createDemoPresentation_shouldReturnPresentationWithCorrectTitle() {
        assertEquals("Welcome to JabberPoint", demoPresentation.getTitle());
    }

    @Test
    void createDemoPresentation_shouldReturnPresentationWithCorrectNumberOfSlides() {
        assertEquals(3, demoPresentation.getTotalSlides());
    }

    @Test
    void firstSlide_shouldHaveCorrectTitleAndItems() {
        demoPresentation.goToSlide(0);
        Slide firstSlide = demoPresentation.getCurrentSlide();

        assertNotNull(firstSlide);
        assertEquals("Welcome", firstSlide.getTitle());
        assertEquals(7, firstSlide.getItems().size());

        List<SlideItem> items = firstSlide.getItems();

        assertTrue(items.get(0) instanceof ShapeItem);
        assertTrue(items.get(1) instanceof TextItem);
        assertEquals("Welcome to", ((TextItem) items.get(1)).getContent());
        assertTrue(items.get(2) instanceof TextItem);
        assertEquals("JabberPoint", ((TextItem) items.get(2)).getContent());
        assertTrue(items.get(3) instanceof TextItem);
        assertEquals("The Modern Java Presentation Tool", ((TextItem) items.get(3)).getContent());
        assertTrue(items.get(4) instanceof TextItem);
        assertEquals("Version 2.0", ((TextItem) items.get(4)).getContent());
        assertTrue(items.get(5) instanceof TextItem);
        assertEquals("Originally created by Ian Darwin (1996-2000)", ((TextItem) items.get(5)).getContent());
        assertTrue(items.get(6) instanceof TextItem);
        assertEquals("Enhanced by Mihaela and Erika, Pro IT Specialists", ((TextItem) items.get(6)).getContent());
    }

    @Test
    void secondSlide_shouldHaveCorrectTitleAndItems() {
        demoPresentation.goToSlide(1);
        Slide secondSlide = demoPresentation.getCurrentSlide();

        assertNotNull(secondSlide);
        assertEquals("Features", secondSlide.getTitle());
        assertEquals(8, secondSlide.getItems().size());

        List<SlideItem> items = secondSlide.getItems();

        assertTrue(items.get(0) instanceof ShapeItem);
        assertTrue(items.get(1) instanceof TextItem);
        assertEquals("Powerful Features", ((TextItem) items.get(1)).getContent());
        assertTrue(items.get(2) instanceof TextItem);
        assertEquals("Everything you need for great presentations", ((TextItem) items.get(2)).getContent());

        assertEquals("✦  Modern Interface", ((TextItem) items.get(3)).getContent());
        assertEquals("✦  Rich Text Support", ((TextItem) items.get(4)).getContent());
        assertEquals("✦  Image Integration", ((TextItem) items.get(5)).getContent());
        assertEquals("✦  Shape Elements", ((TextItem) items.get(6)).getContent());
        assertEquals("Press → or Page Down for next slide", ((TextItem) items.get(7)).getContent());
    }

    @Test
    void thirdSlide_shouldHaveCorrectTitleAndItems() {
        demoPresentation.goToSlide(2);
        Slide thirdSlide = demoPresentation.getCurrentSlide();

        assertNotNull(thirdSlide);
        assertEquals("Getting Started", thirdSlide.getTitle());
        assertEquals(5, thirdSlide.getItems().size());

        List<SlideItem> items = thirdSlide.getItems();

        assertTrue(items.get(0) instanceof ShapeItem);
        assertTrue(items.get(1) instanceof TextItem);
        assertEquals("Create Your First Presentation", ((TextItem) items.get(1)).getContent());

        assertEquals("1. Click File → New for a fresh start", ((TextItem) items.get(2)).getContent());
        assertEquals("2. Use Edit menu to add content", ((TextItem) items.get(3)).getContent());
        assertEquals("3. Save your work with File → Save", ((TextItem) items.get(4)).getContent());
    }
} 