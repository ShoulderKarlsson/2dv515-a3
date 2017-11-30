package a3.searchengine;

import a3.searchengine.lib.PageBucket;
import a3.searchengine.lib.PageBucketSearch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/search")
    public String search() {
//        PageBucket pb = new PageBucket();

        PageBucketSearch pbs = new PageBucketSearch(new PageBucket());
        pbs.search("code syntax");


        return "www.google.com";
    }
}
