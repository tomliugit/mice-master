package info.meihua.elasticsearch.service.controller;

import info.meihua.elasticsearch.service.service.IElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunwell
 */
@RestController
public class ExampleController {

    @Autowired
    private IElasticSearchService elasticSearchService;

    @RequestMapping(value = "/es/nodes")
    public String esNodeInfo() {
        return elasticSearchService.nodesInfo().getJsonString();
    }
}
