package com.jabberpoint.command;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
    }

    @Test
    void testNextSlideCommand() {
        presentation.append(new Slide());
        presentation.append(new Slide());
        
        new NextSlideCommand(presentation).execute();
        assertEquals(1, presentation.getCurrentSlideNumber());
    }

    @Test
    void testPrevSlideCommand() {
        presentation.append(new Slide());
        presentation.append(new Slide());
        presentation.append(new Slide());
        
        presentation.goToSlide(2);
        new PrevSlideCommand(presentation).execute();
        assertEquals(1, presentation.getCurrentSlideNumber());
    }
} 