package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class NextSlideCommandTest {

    @Test
    void execute_shouldCallNextSlideOnPresentation() {
        Presentation mockPresentation = mock(Presentation.class);
        NextSlideCommand command = new NextSlideCommand(mockPresentation);

        command.execute();

        verify(mockPresentation, times(1)).nextSlide();
    }
} 