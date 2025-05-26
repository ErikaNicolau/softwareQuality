package com.jabberpoint;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.view.SlideViewerComponent;
import com.jabberpoint.command.*;
import com.jabberpoint.util.XMLAccessor;
import com.jabberpoint.util.DemoPresentation;
import com.jabberpoint.util.Constants;
import com.jabberpoint.util.Position;
import com.jabberpoint.service.DialogService;
import com.jabberpoint.service.FileService;
import com.jabberpoint.service.PositionDialog;
import com.jabberpoint.service.swing.SwingDialogService;
import com.jabberpoint.service.swing.SwingFileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;

public class JabberPoint {
    private static final String WINDOW_TITLE = "JabberPoint";
    private static SlideViewerComponent slideViewer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Could not set system look and feel: " + e.getMessage());
            }
            Presentation presentation = DemoPresentation.createDemoPresentation();
            JFrame frame = createFrame(presentation);
            DialogService dialogService = new SwingDialogService(frame);
            FileService fileService = new SwingFileService(frame);
            createMenuBar(frame, presentation, dialogService, fileService);
            setupKeyBindings(frame, presentation, dialogService, fileService);
            presentation.goToSlide(0);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JFrame createFrame(Presentation presentation) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);
        frame.setMinimumSize(new Dimension(800, 600));
        slideViewer = new SlideViewerComponent(presentation, frame);
        slideViewer.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(slideViewer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        frame.add(scrollPane);
        return frame;
    }

    private static void createMenuBar(JFrame frame, Presentation presentation, DialogService dialogService, FileService fileService) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New", new NewCommand(presentation, dialogService)));
        fileMenu.add(createMenuItem("Open", new OpenCommand(presentation, fileService, dialogService)));
        fileMenu.add(createMenuItem("Save", new SaveCommand(presentation, fileService, dialogService)));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", new ExitCommand(frame, presentation, fileService, dialogService)));
        
        JMenu slideMenu = new JMenu("Slide");
        slideMenu.add(createMenuItem("New Slide", new AddSlideCommand(presentation, dialogService)));
        slideMenu.addSeparator();
        slideMenu.add(createMenuItem("Next", new NextSlideCommand(presentation)));
        slideMenu.add(createMenuItem("Previous", new PrevSlideCommand(presentation)));
        
        JMenuItem goToMenuItem = new JMenuItem("Go to...");
        goToMenuItem.addActionListener(e -> {
            String slideNumberStr = dialogService.showInputDialog("Go to slide number:");
            if (slideNumberStr != null && !slideNumberStr.trim().isEmpty()) {
                try {
                    int slideNumber = Integer.parseInt(slideNumberStr) - 1;
                    new GotoCommand(presentation, slideNumber).execute();
                } catch (NumberFormatException ex) {
                    dialogService.showMessageDialog("Invalid slide number.");
                }
            }
        });
        slideMenu.add(goToMenuItem);
        
        JMenu editMenu = new JMenu("Edit");
        JMenuItem addTextMenuItem = new JMenuItem("Add Text");
        addTextMenuItem.addActionListener(e -> {
            String text = dialogService.showInputDialog("Enter text content:");
            if (text != null && !text.trim().isEmpty()) {
                PositionDialog positionDialog = new PositionDialog(frame);
                Position position = positionDialog.showDialog();
                if (position != null) {
                    TextItem textItem = new TextItem(Constants.DEFAULT_LEVEL, text.trim());
                    textItem.setPosition(position.getX(), position.getY());
                    textItem.setFontSize(Constants.DEFAULT_FONT_SIZE);
                    presentation.getCurrentSlide().append(textItem);
                }
            }
        });
        editMenu.add(addTextMenuItem);
        
        JMenuItem addImageMenuItem = new JMenuItem("Add Image");
        addImageMenuItem.addActionListener(e -> {
            String imagePath = fileService.getFilePathToOpen();
            if (imagePath != null) {
                try {
                    PositionDialog positionDialog = new PositionDialog(frame);
                    Position position = positionDialog.showDialog();
                    if (position != null) {
                        BitmapItem bitmapItem = new BitmapItem(Constants.DEFAULT_LEVEL, imagePath);
                        bitmapItem.setPosition(position.getX(), position.getY());
                        presentation.getCurrentSlide().append(bitmapItem);
                    }
                } catch (Exception ex) {
                    dialogService.showMessageDialog("Error loading image: " + ex.getMessage());
                }
            }
        });
        editMenu.add(addImageMenuItem);
        
        JMenuItem addShapeMenuItem = new JMenuItem("Add Shape");
        addShapeMenuItem.addActionListener(e -> {
            String shapeType = dialogService.showInputDialog("Enter shape type (Rectangle, Oval, Line):");
            if (shapeType != null && !shapeType.trim().isEmpty()) {
                try {
                    PositionDialog positionDialog = new PositionDialog(frame);
                    Position position = positionDialog.showDialog();
                    if (position != null) {
                        ShapeItem shapeItem = new ShapeItem(Constants.DEFAULT_LEVEL, shapeType.trim(), Color.BLACK);
                        shapeItem.setPosition(position.getX(), position.getY());
                        presentation.getCurrentSlide().append(shapeItem);
                    }
                } catch (IllegalArgumentException ex) {
                    dialogService.showMessageDialog("Error creating shape: " + ex.getMessage());
                }
            }
        });
        editMenu.add(addShapeMenuItem);
        
        menuBar.add(fileMenu);
        menuBar.add(slideMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);
    }

    private static JMenuItem createMenuItem(String label, Command command) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.addActionListener(e -> command.execute());
        return menuItem;
    }

    private static void setupKeyBindings(JFrame frame, Presentation presentation, DialogService dialogService, FileService fileService) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_PLUS ->
                        new NextSlideCommand(presentation).execute();
                    case KeyEvent.VK_PAGE_UP, KeyEvent.VK_LEFT, KeyEvent.VK_MINUS ->
                        new PrevSlideCommand(presentation).execute();
                    case KeyEvent.VK_ESCAPE ->
                        new ExitCommand(frame, presentation, fileService, dialogService).execute();
                }
            }
        });
        frame.setFocusable(true);
        frame.requestFocus();
    }
} 