package a3.searchengine;

import a3.searchengine.lib.PageBucket;
import a3.searchengine.lib.PageBucketSearch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class MainController {

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET, produces = "application/json")
    public ArrayList<PageBucketSearch.SearchResult> search(@PathVariable String query) {
        System.out.println(query);
        PageBucketSearch pbs = new PageBucketSearch(new PageBucket());
        return pbs.search(query);
    }
}
