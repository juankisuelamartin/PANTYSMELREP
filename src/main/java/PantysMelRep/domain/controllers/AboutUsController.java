package PantysMelRep.domain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController
{
    @GetMapping("/AboutUS")
    public String aboutUs()
    {
        return "AboutUS";
    }

    @GetMapping("/ContactUS")
    public String contactUs()
    {
        return "ContactUS";
    }
    @GetMapping("/OurTeam")
    public String ourTeam()
    {
        return "OurTeam";
    }
}
