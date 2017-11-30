package a3.searchengine;

import a3.searchengine.lib.PageBucket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/search")
    public String search() {
        PageBucket pb = new PageBucket();




        return "www.google.com";
    }
}
