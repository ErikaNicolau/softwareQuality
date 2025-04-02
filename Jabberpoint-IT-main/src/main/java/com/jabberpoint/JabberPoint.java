package com.jabberpoint;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.view.SlideViewerComponent;
import com.jabberpoint.command.*;
import com.jabberpoint.util.XMLAccessor;
import com.jabberpoint.util.DemoPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;

/**
 * JabberPoint Main Program
 * <p>This program is distributed under the terms of the accompanying
 * README file.</p>
 * <p>This program is copyrighted by its author.</p>
 * <p>This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.</p>
 * <p>This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.</p>
 * <p>You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA.</p>
 */
public class JabberPoint {
    private static final String WINDOW_TITLE = "JabberPoint";
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;

    /**
     * The main program creates a Presentation and starts the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better UI
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Could not set system look and feel: " + e.getMessage());
            }
            
            Presentation presentation = DemoPresentation.createDemoPresentation();
            JFrame frame = createFrame(presentation);
            setupKeyBindings(frame, presentation);
            
            // Ensure we start at the first slide
            presentation.goToSlide(0);
            
            // Center the frame and show it
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JFrame createFrame(Presentation presentation) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setMinimumSize(new Dimension(800, 600));
        
        // Create the slide viewer with a white background
        SlideViewerComponent slideViewer = new SlideViewerComponent(presentation, frame);
        slideViewer.setBackground(Color.WHITE);
        
        // Add the slide viewer to a scroll pane
        JScrollPane scrollPane = new JScrollPane(slideViewer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border for cleaner look
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        frame.add(scrollPane);
        createMenuBar(frame, presentation);

        return frame;
    }

    private static void createMenuBar(JFrame frame, Presentation presentation) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Add some padding

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New", new NewCommand(presentation, frame)));
        fileMenu.add(createMenuItem("Open", new OpenCommand(presentation, frame)));
        fileMenu.add(createMenuItem("Save", new SaveCommand(presentation, frame)));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", new ExitCommand(frame, presentation)));

        JMenu slideMenu = new JMenu("Slide");
        slideMenu.add(createMenuItem("New Slide", new AddSlideCommand(presentation, frame)));
        slideMenu.addSeparator();
        slideMenu.add(createMenuItem("Next", new NextSlideCommand(presentation)));
        slideMenu.add(createMenuItem("Previous", new PrevSlideCommand(presentation)));
        slideMenu.add(createMenuItem("Go to...", new GotoCommand(presentation, frame)));

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(createMenuItem("Add Text", new AddTextCommand(presentation, frame)));
        editMenu.add(createMenuItem("Add Image", new AddImageCommand(presentation, frame)));
        editMenu.add(createMenuItem("Add Shape", new AddShapeCommand(presentation, frame)));

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

    private static void setupKeyBindings(JFrame frame, Presentation presentation) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_PLUS ->
                        new NextSlideCommand(presentation).execute();
                    case KeyEvent.VK_PAGE_UP, KeyEvent.VK_LEFT, KeyEvent.VK_MINUS ->
                        new PrevSlideCommand(presentation).execute();
                    case KeyEvent.VK_ESCAPE ->
                        new ExitCommand(frame, presentation).execute();
                }
            }
        });
        frame.setFocusable(true);
        frame.requestFocus();
    }
} 