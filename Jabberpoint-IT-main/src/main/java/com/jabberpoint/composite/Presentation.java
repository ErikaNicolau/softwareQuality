package com.jabberpoint.composite;

import com.jabberpoint.observer.SlideObserver;
import java.util.ArrayList;
import java.util.List;
import com.jabberpoint.util.Constants;

public class Presentation {
    private static final int INITIAL_SLIDE_NUMBER = 0;

    private String title;
    private final List<Slide> slides;
    private int currentSlideNumber;
    private final List<SlideObserver> observers;
    private boolean hasUnsavedChanges;
    private String currentFileName;

    public Presentation() {
        this.title = "";
        this.slides = new ArrayList<>();
        this.currentSlideNumber = INITIAL_SLIDE_NUMBER;
        this.observers = new ArrayList<>();
        this.hasUnsavedChanges = false;
        this.currentFileName = null;
    }

    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(observer -> observer.update(getCurrentSlide()));
    }

    public void setTitle(String title) {
        this.title = title;
        markAsChanged();
    }

    public String getTitle() {
        return title;
    }

    public void append(Slide slide) {
        slides.add(slide);
        slide.addObserver(s -> notifyObservers());
        currentSlideNumber = slides.size() - 1;
        notifyObservers();
        markAsChanged();
    }

    public Slide getCurrentSlide() {
        if (slides.isEmpty()) {
            return null;
        }
        return slides.get(currentSlideNumber);
    }

    public void nextSlide() {
        if (currentSlideNumber < slides.size() - 1) {
            currentSlideNumber++;
            notifyObservers();
        }
    }

    public void previousSlide() {
        if (currentSlideNumber > 0) {
            currentSlideNumber--;
            notifyObservers();
        }
    }

    public void goToSlide(int slideNumber) {
        if (slideNumber >= 0 && slideNumber < slides.size()) {
            currentSlideNumber = slideNumber;
            notifyObservers();
        }
    }

    public int getCurrentSlideNumber() {
        return currentSlideNumber;
    }

    public int getTotalSlides() {
        return slides.size();
    }

    public void clear() {
        slides.clear();
        currentSlideNumber = INITIAL_SLIDE_NUMBER;
        notifyObservers();
        markAsChanged();
    }

    public void markAsChanged() {
        this.hasUnsavedChanges = true;
    }

    public void markAsSaved(String filename) {
        this.hasUnsavedChanges = false;
        this.currentFileName = filename;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public String getCurrentFileName() {
        return currentFileName;
    }
} 