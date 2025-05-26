package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.service.DialogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddSlideCommandTest {

    @Mock
    private Presentation presentation;
    @Mock
    private DialogService dialogService;

    private AddSlideCommand addSlideCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldAddSlideAndNavigateToItAndShowMessage() {
        // Arrange
        addSlideCommand = new AddSlideCommand(presentation, dialogService);
        when(presentation.getTotalSlides()).thenReturn(5); // Assume 5 slides before adding a new one

        // Act
        addSlideCommand.execute();

        // Assert
        // Verify that a new slide was appended to the presentation
        verify(presentation).append(any(Slide.class));

        // Verify that goToSlide was called with the correct index (index of the new slide)
        verify(presentation).goToSlide(4);

        // Verify that the message dialog was shown
        verify(dialogService).showMessageDialog("New blank slide added!\nUse the Edit menu to add content.");
    }
} 