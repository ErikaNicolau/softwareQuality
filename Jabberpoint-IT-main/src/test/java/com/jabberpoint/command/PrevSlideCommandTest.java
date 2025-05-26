package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class PrevSlideCommandTest {

    @Test
    void execute_shouldCallPreviousSlideOnPresentation() {
        Presentation mockPresentation = mock(Presentation.class);
        PrevSlideCommand command = new PrevSlideCommand(mockPresentation);

        command.execute();

        verify(mockPresentation, times(1)).previousSlide();
    }
} 