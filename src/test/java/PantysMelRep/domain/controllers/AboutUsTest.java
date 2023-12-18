package PantysMelRep.domain.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AboutUsControllerTest {

    private AboutUsController aboutUsController;

    @BeforeEach
    void setUp() {
        aboutUsController = new AboutUsController();
    }

    @Test
    void aboutUs_ReturnsCorrectView() {
        String view = aboutUsController.aboutUs();
        assertEquals("AboutUS", view);
    }

    @Test
    void contactUs_ReturnsCorrectView() {
        String view = aboutUsController.contactUs();
        assertEquals("ContactUS", view);
    }

    @Test
    void ourTeam_ReturnsCorrectView() {
        String view = aboutUsController.ourTeam();
        assertEquals("OurTeam", view);
    }
}
