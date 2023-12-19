package PantysMelRep.domain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Definición del controlador AboutUsController
@Controller
public class AboutUsController
{
    //Mapeo de /AboutUS
    @GetMapping("/AboutUS")
    public String aboutUs()
    {
        return "AboutUS";
    }

    //Mapeo de /ContactUS
    @GetMapping("/ContactUS")
    public String contactUs()
    {
        return "ContactUS";
    }
    //Mapeo de /OurTeam
    @GetMapping("/OurTeam")
    public String ourTeam()
    {
        return "OurTeam";
    }
}
