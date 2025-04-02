package com.jabberpoint.observer;

import com.jabberpoint.composite.Slide;

/**
 * Interface for the Observer pattern implementation.
 * Classes implementing this interface will be notified of changes to a Slide.
 */
public interface SlideObserver {
    /**
     * Called when the observed slide changes.
     * @param slide The updated slide
     */
    void update(Slide slide);
} 