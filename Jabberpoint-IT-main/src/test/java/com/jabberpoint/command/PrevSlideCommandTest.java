package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PrevSlideCommandTest {

    @Mock
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldGoToPreviousSlideWhenPossible() {
        when(presentation.getCurrentSlideNumber()).thenReturn(2);

        PrevSlideCommand command = new PrevSlideCommand(presentation);
        command.execute();

        verify(presentation).previousSlide();
    }

    @Test
    void execute_shouldCallPreviousSlideEvenOnFirstSlide() {
        when(presentation.getCurrentSlideNumber()).thenReturn(0);

        PrevSlideCommand command = new PrevSlideCommand(presentation);
        command.execute();

        verify(presentation).previousSlide();
    }
} 