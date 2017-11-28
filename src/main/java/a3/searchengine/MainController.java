package a3.searchengine;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/search")
    public String search() {




        return "www.google.com";
    }
}
