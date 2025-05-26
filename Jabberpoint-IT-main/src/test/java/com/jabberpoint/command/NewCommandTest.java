package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.composite.items.TextItem;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewCommandTest {

    @Test
    void execute_shouldClearPresentationAndAddNewSlideWithItems() {
        Presentation mockPresentation = mock(Presentation.class);
        JFrame mockFrame = mock(JFrame.class);
        NewCommand command = new NewCommand(mockPresentation, mockFrame);

        command.execute();

        verify(mockPresentation, times(1)).clear();
        verify(mockPresentation, times(1)).append(any(Slide.class));
    }
} 