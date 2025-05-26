package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class GotoCommandTest {

    @Mock
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldCallGoToSlideOnPresentationWithCorrectSlideNumber() {
        int targetSlideNumber = 5;
        GotoCommand gotoCommand = new GotoCommand(presentation, targetSlideNumber);

        gotoCommand.execute();

        verify(presentation, times(1)).goToSlide(targetSlideNumber);
    }
} 