package com.jabberpoint.util;

import com.jabberpoint.composite.Presentation;
import com.jabberpoint.composite.Slide;
import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.TextItem;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.composite.items.ShapeItem;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Rectangle;


public class XMLAccessor {
    private static final String PRESENTATION_TAG = "presentation";
    private static final String SHOW_TITLE_TAG = "showtitle";
    private static final String SLIDE_TAG = "slide";
    private static final String SLIDE_TITLE_TAG = "title";
    private static final String ITEM_TAG = "item";
    private static final String ITEM_KIND_ATTRIBUTE = "kind";
    private static final String ITEM_LEVEL_ATTRIBUTE = "level";
    private static final String ITEM_X_ATTRIBUTE = "x";
    private static final String ITEM_Y_ATTRIBUTE = "y";
    private static final String ITEM_WIDTH_ATTRIBUTE = "width";
    private static final String ITEM_HEIGHT_ATTRIBUTE = "height";
    private static final String ITEM_COLOR_ATTRIBUTE = "color";
    private static final String ITEM_SHAPE_TYPE_ATTRIBUTE = "shapeType";
    private static final String ITEM_FONT_SIZE_ATTRIBUTE = "fontSize";

    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            presentation.setTitle(getElementText(doc, SHOW_TITLE_TAG));

            NodeList slides = doc.getElementsByTagName(SLIDE_TAG);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getElementText(xmlSlide, SLIDE_TITLE_TAG));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM_TAG);
                for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Error loading presentation: " + e.getMessage());
        }
    }

    public void saveFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            Element doc = document.createElement(PRESENTATION_TAG);
            document.appendChild(doc);

            Element showTitle = document.createElement(SHOW_TITLE_TAG);
            showTitle.appendChild(document.createTextNode(presentation.getTitle()));
            doc.appendChild(showTitle);

            for (int slideNumber = 0; slideNumber < presentation.getTotalSlides(); slideNumber++) {
                Slide slide = presentation.getCurrentSlide();
                Element xmlSlide = document.createElement(SLIDE_TAG);
                doc.appendChild(xmlSlide);

                Element title = document.createElement(SLIDE_TITLE_TAG);
                title.appendChild(document.createTextNode(slide.getTitle()));
                xmlSlide.appendChild(title);

                for (SlideItem item : slide.getItems()) {
                    Element xmlItem = document.createElement(ITEM_TAG);
                    xmlItem.setAttribute(ITEM_KIND_ATTRIBUTE, getItemType(item));
                    xmlItem.setAttribute(ITEM_LEVEL_ATTRIBUTE, String.valueOf(item.getLevel()));
                    xmlItem.appendChild(document.createTextNode(item.getContent()));

                    // Save position
                    Rectangle bounds = item.getBoundingBox();
                    xmlItem.setAttribute(ITEM_X_ATTRIBUTE, String.valueOf(bounds.x));
                    xmlItem.setAttribute(ITEM_Y_ATTRIBUTE, String.valueOf(bounds.y));
                    xmlItem.setAttribute(ITEM_WIDTH_ATTRIBUTE, String.valueOf(bounds.width));
                    xmlItem.setAttribute(ITEM_HEIGHT_ATTRIBUTE, String.valueOf(bounds.height));

                    // Save additional properties based on item type
                    if (item instanceof TextItem) {
                        xmlItem.setAttribute(ITEM_FONT_SIZE_ATTRIBUTE, String.valueOf(((TextItem) item).getFontSize()));
                    } else if (item instanceof ShapeItem) {
                        xmlItem.setAttribute(ITEM_SHAPE_TYPE_ATTRIBUTE, ((ShapeItem) item).getShapeType());
                        xmlItem.setAttribute(ITEM_COLOR_ATTRIBUTE, String.valueOf(((ShapeItem) item).getColor().getRGB()));
                    }

                    xmlSlide.appendChild(xmlItem);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(filename)));
        } catch (Exception e) {
            throw new IOException("Error saving presentation: " + e.getMessage());
        }
    }

    private void loadSlideItem(Slide slide, Element item) {
        String kind = item.getAttribute(ITEM_KIND_ATTRIBUTE);
        if (kind == null || kind.trim().isEmpty()) {
            throw new IllegalArgumentException("Item 'kind' attribute is missing or empty.");
        }

        int level;
        String levelStr = item.getAttribute(ITEM_LEVEL_ATTRIBUTE);
        if (levelStr == null || levelStr.trim().isEmpty()) {
             throw new IllegalArgumentException("Item 'level' attribute is missing or empty.");
        }
        try {
            level = Integer.parseInt(levelStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value for item 'level' attribute: " + levelStr, e);
        }

        String content = item.getTextContent();
        
        SlideItem slideItem = null;
        
        switch (kind) {
            case "text":
                slideItem = new TextItem(level, content);
                if (item.hasAttribute(ITEM_FONT_SIZE_ATTRIBUTE)) {
                    ((TextItem) slideItem).setFontSize(Integer.parseInt(item.getAttribute(ITEM_FONT_SIZE_ATTRIBUTE)));
                }
                break;
            case "image":
                slideItem = new BitmapItem(level, content);
                break;
            case "shape":
                String shapeType = item.getAttribute(ITEM_SHAPE_TYPE_ATTRIBUTE);
                String colorStr = item.getAttribute(ITEM_COLOR_ATTRIBUTE);
                Color color;
                 try {
                    color = Color.decode(colorStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid value for shape 'color' attribute: " + colorStr, e);
                }

                slideItem = new ShapeItem(level, shapeType, color);
                break;
            default:
                throw new IllegalArgumentException("Unknown item kind: " + kind);
        }

        if (slideItem != null) {
            if (item.hasAttribute(ITEM_X_ATTRIBUTE) && item.hasAttribute(ITEM_Y_ATTRIBUTE)) {
                int x = Integer.parseInt(item.getAttribute(ITEM_X_ATTRIBUTE));
                int y = Integer.parseInt(item.getAttribute(ITEM_Y_ATTRIBUTE));
                slideItem.setPosition(x, y);
            }
            
            if (item.hasAttribute(ITEM_WIDTH_ATTRIBUTE) && item.hasAttribute(ITEM_HEIGHT_ATTRIBUTE)) {
                int width = Integer.parseInt(item.getAttribute(ITEM_WIDTH_ATTRIBUTE));
                int height = Integer.parseInt(item.getAttribute(ITEM_HEIGHT_ATTRIBUTE));
                slideItem.setSize(width, height);
            }
            
            slide.append(slideItem);
        }
    }

    String getItemType(SlideItem item) {
        if (item instanceof TextItem) return "text";
        if (item instanceof BitmapItem) return "image";
        if (item instanceof ShapeItem) return "shape";
        return "unknown";
    }

    private String getElementText(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }
} 