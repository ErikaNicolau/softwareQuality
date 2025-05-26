package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NextSlideCommandTest {

    @Mock
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldGoToNextSlideWhenPossible() {
        when(presentation.getTotalSlides()).thenReturn(5);
        when(presentation.getCurrentSlideNumber()).thenReturn(2);

        NextSlideCommand command = new NextSlideCommand(presentation);
        command.execute();

        verify(presentation).nextSlide();
    }

    @Test
    void execute_shouldCallNextSlideEvenOnLastSlide() {
        when(presentation.getTotalSlides()).thenReturn(3);
        when(presentation.getCurrentSlideNumber()).thenReturn(2);

        NextSlideCommand command = new NextSlideCommand(presentation);
        command.execute();

        verify(presentation).nextSlide();
    }
} 