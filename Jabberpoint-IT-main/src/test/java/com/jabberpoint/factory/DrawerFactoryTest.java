package com.jabberpoint.factory;

import com.jabberpoint.composite.items.BitmapItem;
import com.jabberpoint.composite.items.ShapeItem;
import com.jabberpoint.composite.items.SlideItem;
import com.jabberpoint.composite.items.TextItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrawerFactoryTest {

    @Test
    void createDrawer_shouldReturnBitmapDrawerForBitmapItem() {
        BitmapItem mockBitmapItem = mock(BitmapItem.class);

        ItemDrawer drawer = DrawerFactory.createDrawer(mockBitmapItem);

        assertNotNull(drawer);
        assertTrue(drawer instanceof BitmapDrawer);
    }

    @Test
    void createDrawer_shouldReturnTextDrawerForTextItem() {
        TextItem mockTextItem = mock(TextItem.class);

        ItemDrawer drawer = DrawerFactory.createDrawer(mockTextItem);

        assertNotNull(drawer);
        assertTrue(drawer instanceof TextDrawer);
    }

    @Test
    void createDrawer_shouldReturnShapeDrawerForShapeItem() {
        ShapeItem mockShapeItem = mock(ShapeItem.class);

        ItemDrawer drawer = DrawerFactory.createDrawer(mockShapeItem);

        assertNotNull(drawer);
        assertTrue(drawer instanceof ShapeDrawer);
    }

    @Test
    void createDrawer_shouldThrowExceptionForUnknownItemType() {
        SlideItem unknownItem = mock(SlideItem.class);

        assertThrows(IllegalArgumentException.class, () -> {
            DrawerFactory.createDrawer(unknownItem);
        });
    }
} 