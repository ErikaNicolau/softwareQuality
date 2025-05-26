package com.jabberpoint.test.mock;

import com.jabberpoint.service.DialogService;

import java.util.ArrayList;
import java.util.List;

public class MockDialogService implements DialogService {
    private String inputResponse = null;
    private boolean confirmResponse = true;
    private List<String> shownMessages = new ArrayList<>();
    private List<String> inputMessages = new ArrayList<>();
    private List<String> confirmMessages = new ArrayList<>();

    public void setInputResponse(String response) {
        this.inputResponse = response;
    }

    public void setConfirmResponse(boolean response) {
        this.confirmResponse = response;
    }

    public List<String> getShownMessages() {
        return shownMessages;
    }

     public List<String> getInputMessages() {
        return inputMessages;
    }

     public List<String> getConfirmMessages() {
        return confirmMessages;
    }

    public void clearInteractions() {
        shownMessages.clear();
        inputMessages.clear();
        confirmMessages.clear();
        inputResponse = null;
        confirmResponse = true;
    }

    @Override
    public String showInputDialog(String message) {
        inputMessages.add(message);
        return inputResponse;
    }

    @Override
    public void showMessageDialog(String message) {
        shownMessages.add(message);
    }

    @Override
    public boolean showConfirmDialog(String message) {
        confirmMessages.add(message);
        return confirmResponse;
    }
} 