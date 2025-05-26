package com.jabberpoint.command;

import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.SlideItem;

public interface Receiver {
    Slide getCurrentSlide();
    void append(SlideItem item);
    void clear();
    void goToSlide(int slideNumber);
    int getTotalSlides();
} 