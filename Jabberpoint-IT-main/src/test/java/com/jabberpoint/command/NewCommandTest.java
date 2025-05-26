package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.Color;
import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewCommandTest {

    @Mock
    private Presentation presentation;
    @Mock
    private DialogService dialogService;

    private NewCommand newCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newCommand = new NewCommand(presentation, dialogService);
    }

    @Test
    void execute_shouldClearPresentationAndAddWelcomeSlide() {
        // Act
        newCommand.execute();

        // Assert
        // Verify that presentation was cleared
        verify(presentation).clear();

        // Verify that a new slide was appended
        verify(presentation).append(any(Slide.class));
    }

    @Test
    void constructor_shouldInitializeFields() {
        // Assert
        assertNotNull(newCommand);
        verifyNoInteractions(presentation, dialogService);
    }

    @Test
    void execute_shouldCreateWelcomeSlideWithCorrectContent() {
        // Arrange
        ArgumentCaptor<Slide> slideCaptor = ArgumentCaptor.forClass(Slide.class);

        // Act
        newCommand.execute();

        // Assert
        verify(presentation).append(slideCaptor.capture());
        Slide capturedSlide = slideCaptor.getValue();
        
        // Verify slide title
        assertEquals("Welcome to JabberPoint", capturedSlide.getTitle());
        
        // Verify slide items
        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("Welcome to JabberPoint") &&
                ((TextItem) item).getFontSize() == Constants.TITLE_FONT_SIZE));
        
        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("Create Professional Presentations") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));
        
        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("✦  Rich Text Support") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));

        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("✦  Image Integration") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));

        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("✦  Shape Elements") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));

        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("✦  Smooth Navigation") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));

        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("Press 'Edit' menu to start creating your presentation") &&
                ((TextItem) item).getFontSize() == Constants.DEFAULT_FONT_SIZE));

        assertTrue(capturedSlide.getItems().stream()
            .anyMatch(item -> item instanceof ShapeItem && 
                ((ShapeItem) item).getShapeType().equals("rectangle")));
    }

    @Test
    void execute_shouldHandleEmptyPresentation() {
        // Arrange
        when(presentation.getCurrentSlide()).thenReturn(null);

        // Act
        newCommand.execute();

        // Assert
        verify(presentation).clear();
        verify(presentation).append(any(Slide.class));
    }

    @Test
    void execute_shouldCreateSlideWithCorrectItemPositions() {
        // Arrange
        ArgumentCaptor<Slide> slideCaptor = ArgumentCaptor.forClass(Slide.class);

        // Act
        newCommand.execute();

        // Assert
        verify(presentation).append(slideCaptor.capture());
        Slide capturedSlide = slideCaptor.getValue();
        
        // Verify decorative shape position and size
        assertTrue(capturedSlide.getItems().stream()
            .filter(item -> item instanceof ShapeItem)
            .anyMatch(item -> {
                ShapeItem shapeItem = (ShapeItem) item;
                Rectangle bounds = shapeItem.getBoundingBox();
                return bounds.x == 100 && 
                       bounds.y == 100 && 
                       bounds.width == Constants.DEFAULT_WIDTH && 
                       bounds.height == Constants.DEFAULT_HEIGHT;
            }));
        
        // Verify title position
        assertTrue(capturedSlide.getItems().stream()
            .filter(item -> item instanceof TextItem && 
                ((TextItem) item).getContent().equals("Welcome to JabberPoint"))
            .anyMatch(item -> {
                TextItem textItem = (TextItem) item;
                Rectangle bounds = textItem.getBoundingBox();
                return bounds.x == 150 && bounds.y == 180;
            }));
    }
} 