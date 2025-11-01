package tools.msm.b_tracking_list.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // http://localhost:8080/
    @GetMapping("/")
    public String getIndex() {
        return "home"; // home.html
    }

    // http://localhost:8080/login
    @GetMapping("/login")
    public String getLogin() {
        return "login"; // login.html 
    }
    
}
