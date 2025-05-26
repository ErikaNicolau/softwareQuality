package com.jabberpoint.composite;

import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.observer.SlideObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class PresentationTest {
    private Presentation presentation;
    private List<Boolean> observerCalled;
    private SlideObserver observer;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        observerCalled = new ArrayList<>();
        observer = slide -> observerCalled.add(true);
        presentation.addObserver(observer);
    }

    @Test
    void testAppendSlide() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        
        assertEquals(1, presentation.getTotalSlides());
        assertEquals("Test Slide", presentation.getCurrentSlide().getTitle());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testAppendMultipleSlides() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        
        assertEquals(2, presentation.getTotalSlides());
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        assertEquals(2, observerCalled.size());
    }

    @Test
    void testNextSlide() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.goToSlide(0);
        observerCalled.clear();
        
        presentation.nextSlide();
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testPreviousSlide() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.goToSlide(1);
        observerCalled.clear();
        
        presentation.previousSlide();
        assertEquals("Slide 1", presentation.getCurrentSlide().getTitle());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testGoToSlide() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        Slide slide3 = new Slide();
        slide3.setTitle("Slide 3");
        
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.append(slide3);
        observerCalled.clear();
        
        presentation.goToSlide(1);
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testGoToSlideOutOfBounds() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        
        // Try to go to a non-existent slide
        presentation.goToSlide(1);
        assertEquals("Test Slide", presentation.getCurrentSlide().getTitle());
    }

    @Test
    void testClear() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        observerCalled.clear();
        
        presentation.clear();
        assertEquals(0, presentation.getTotalSlides());
        assertNull(presentation.getCurrentSlide());
        assertTrue(observerCalled.get(0));
    }

    @Test
    void testMultipleObservers() {
        List<Boolean> secondObserverCalled = new ArrayList<>();
        presentation.addObserver(slide -> secondObserverCalled.add(true));
        
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        
        assertTrue(observerCalled.get(0));
        assertTrue(secondObserverCalled.get(0));
    }

    @Test
    void testRemoveObserver() {
        presentation.removeObserver(observer);
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        
        assertTrue(observerCalled.isEmpty());
    }

    @Test
    void testSlideContent() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(new TextItem(1, "Test Text"));
        presentation.append(slide);
        
        assertEquals(1, presentation.getCurrentSlide().getItems().size());
        assertEquals("Test Text", presentation.getCurrentSlide().getItem(0).getContent());
    }

    @Test
    void testNavigationAtBoundaries() {
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        
        // Test at first slide
        presentation.goToSlide(0);
        presentation.previousSlide();
        assertEquals("Slide 1", presentation.getCurrentSlide().getTitle());
        
        // Test at last slide
        presentation.goToSlide(1);
        presentation.nextSlide();
        assertEquals("Slide 2", presentation.getCurrentSlide().getTitle());
    }

    @Test
    void testSetTitle() {
        presentation.setTitle("New Title");
        assertEquals("New Title", presentation.getTitle());
        assertTrue(presentation.hasUnsavedChanges());
    }

    @Test
    void testMarkAsChanged() {
        presentation.markAsChanged();
        assertTrue(presentation.hasUnsavedChanges());
    }

    @Test
    void testMarkAsSaved() {
        presentation.markAsChanged(); // First, mark as changed
        presentation.markAsSaved("testfile.xml");
        assertFalse(presentation.hasUnsavedChanges());
        assertEquals("testfile.xml", presentation.getCurrentFileName());
    }

    @Test
    void testInitialState() {
        assertEquals("", presentation.getTitle());
        assertEquals(0, presentation.getTotalSlides());
        assertEquals(0, presentation.getCurrentSlideNumber());
        assertNull(presentation.getCurrentSlide());
        assertFalse(presentation.hasUnsavedChanges());
        assertNull(presentation.getCurrentFileName());
    }

    @Test
    void testGetCurrentSlideWhenEmpty() {
        assertNull(presentation.getCurrentSlide());
    }

    @Test
    void testGoToSlideNegative() {
        Slide slide = new Slide();
        presentation.append(slide);
        presentation.goToSlide(-1);
        assertEquals(0, presentation.getCurrentSlideNumber()); // Should remain at the current slide
    }

    @Test
    void testAddAndRemoveObserver() {
        List<Boolean> testObserverCalled = new ArrayList<>();
        SlideObserver testObserver = slide -> testObserverCalled.add(true);

        presentation.addObserver(testObserver);
        Slide slide = new Slide();
        presentation.append(slide);
        assertTrue(testObserverCalled.get(0));

        testObserverCalled.clear();
        presentation.removeObserver(testObserver);
        Slide slide2 = new Slide();
        presentation.append(slide2);
        assertTrue(testObserverCalled.isEmpty()); // Observer should not be called after removal
    }
} 