package a3.searchengine;

import a3.searchengine.lib.PageBucket;
import a3.searchengine.lib.PageBucketSearch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/search/{query}")
    public String search(@PathVariable String query) {
//        PageBucket pb = new PageBucket();

        System.out.println(query);
        PageBucketSearch pbs = new PageBucketSearch(new PageBucket());
        pbs.search(query);


        return "www.google.com";
    }
}
