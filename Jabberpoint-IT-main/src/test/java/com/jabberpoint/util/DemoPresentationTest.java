package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoPresentationTest {
    
    @Test
    void testCreateDemoPresentation() {
        Presentation demoPresentation = DemoPresentation.createDemoPresentation();
        
        assertNotNull(demoPresentation);
        assertTrue(demoPresentation.getTotalSlides() > 0);
        
        // Check if the first slide exists and has a title
        Slide firstSlide = demoPresentation.getCurrentSlide();
        assertNotNull(firstSlide);
        assertNotNull(firstSlide.getTitle());
        assertFalse(firstSlide.getTitle().isEmpty());
    }
    
    @Test
    void testDemoPresentationContent() {
        Presentation demoPresentation = DemoPresentation.createDemoPresentation();
        
        // Verify that the demo presentation has the expected number of slides
        assertTrue(demoPresentation.getTotalSlides() >= 3);
        
        // Check if slides have content
        for (int i = 0; i < demoPresentation.getTotalSlides(); i++) {
            demoPresentation.goToSlide(i);
            Slide slide = demoPresentation.getCurrentSlide();
            assertNotNull(slide);
            assertNotNull(slide.getTitle());
            assertFalse(slide.getTitle().isEmpty());
        }
    }
    
    @Test
    void testDemoPresentationNavigation() {
        Presentation demoPresentation = DemoPresentation.createDemoPresentation();
        
        // Test navigation
        demoPresentation.goToSlide(0);
        assertEquals(0, demoPresentation.getCurrentSlideNumber());
        
        demoPresentation.goToSlide(demoPresentation.getTotalSlides() - 1);
        assertEquals(demoPresentation.getTotalSlides() - 1, demoPresentation.getCurrentSlideNumber());
    }
} 