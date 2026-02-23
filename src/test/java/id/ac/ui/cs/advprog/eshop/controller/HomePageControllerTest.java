package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageControllerTest {

    @Test
    void homePage_shouldReturnHomePageViewName() {
        // Arrange
        HomePageController controller = new HomePageController();

        // Act
        String viewName = controller.homePage();

        // Assert
        assertEquals("homePage", viewName);
    }
}