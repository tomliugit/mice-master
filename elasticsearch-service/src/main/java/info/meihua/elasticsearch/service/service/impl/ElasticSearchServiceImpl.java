package info.meihua.elasticsearch.service.service.impl;

import info.meihua.elasticsearch.service.config.ElasticSearchConfig;
import info.meihua.elasticsearch.service.service.IElasticSearchService;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.*;
import io.searchbox.indices.*;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sunwell
 */
@Service
public class ElasticSearchServiceImpl implements IElasticSearchService {

    private Logger log = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Autowired
    private ElasticSearchConfig esConfig;


    @Override
    public JestResult indexExists(String index) {
        IndicesExists indicesExists = new IndicesExists.Builder(index).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(indicesExists);
            log.info("indexExists == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean createIndex(String indexName, Settings.Builder setting, String analysis) {
        JestResult jr;
        try {
            CreateIndex.Builder builder = new CreateIndex.Builder(indexName);
            if (setting != null) {
                builder.settings(setting.build().getAsMap());
            }
            if (analysis != null) {
                builder.settings(analysis);
            }
            jr = esConfig.getClient().execute(builder.build());
            return jr.isSucceeded();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public <T> JestResult createIndex(T o, String index, String type) {
        JestResult jestResult = null;
        try {
            jestResult = esConfig.getClient().execute(new Index.Builder(o).index(index).type(type).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jestResult;
    }

    @Override
    public <T> void bulkIndex(String index, String type, T o) {
        Bulk bulk = new Bulk.Builder()
                .defaultIndex(index)
                .defaultType(type)
                .addAction(Arrays.asList(
                        new Index.Builder(o).build()
                )).build();
        try {
            esConfig.getClient().execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JestResult deleteIndex(String index) {
        DeleteIndex deleteIndex = new DeleteIndex.Builder(index).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(deleteIndex);
            log.info("deleteIndex == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult closeIndex(String index) {
        CloseIndex closeIndex = new CloseIndex.Builder(index).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(closeIndex);
            log.info("closeIndex == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult flushIndex() {
        Flush flush = new Flush.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(flush);
            log.info("flushIndex == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult optimizeIndex() {
        Optimize optimize = new Optimize.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(optimize);
            log.info("optimizeIndex == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取映射
     *
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    @Override
    public String getMapping(String indexName, String typeName) throws Exception {
        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr = esConfig.getClient().execute(getMapping);
        return jr.getJsonString();
    }

    /**
     * 新增映射
     *
     * @param indexName
     * @param typeName
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public boolean putMapping(String indexName, String typeName, Object source) throws Exception {
        PutMapping putMapping = new PutMapping.Builder(indexName, typeName, source).build();
        JestResult jr = esConfig.getClient().execute(putMapping);
        return jr.isSucceeded();
    }


    @Override
    public JestResult clearCache() {
        ClearCache closeIndex = new ClearCache.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(closeIndex);
            log.info("clearCache == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public JestResult nodesInfo() {
        NodesInfo nodesInfo = new NodesInfo.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(nodesInfo);
            log.info("nodesInfo == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult nodesStats() {
        NodesStats nodesStats = new NodesStats.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(nodesStats);
            log.info("nodesStats == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult health() {
        Health health = new Health.Builder().build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(health);
            log.info("health == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean insertDocument(String indexName, String typeName, Object object, Map<String, Object> params) throws Exception {
        Index.Builder builder = new Index.Builder(object).index(indexName).type(typeName);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue());
        }
        JestResult br = esConfig.getClient().execute(builder.build());
        return br.isSucceeded();
    }

    @Override
    public boolean insertDocumentBatch(String indexName, String typeName, List<Object> objects) throws Exception {
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        for (Object obj : objects) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = esConfig.getClient().execute(bulk.build());
        return br.isSucceeded();
    }


    @Override
    public JestResult updateDocument(String script, String index, String type, String id) {
        Update update = new Update.Builder(script).index(index).type(type).id(id).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(update);
            log.info("updateDocument == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult deleteDocument(String index, String type, String id) {
        Delete delete = new Delete.Builder(id).index(index).type(type).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(delete);
            log.info("deleteDocument == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public JestResult deleteDocumentByQuery(String index, String type, String params) {
        DeleteByQuery db = new DeleteByQuery.Builder(params)
                .addIndex(index)
                .addType(type)
                .build();

        JestResult result = null;
        try {
            result = esConfig.getClient().execute(db);
            log.info("deleteDocument == " + result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public <T> JestResult getDocument(T object, String index, String type, String id) {
        Get get = new Get.Builder(index, id).type(type).build();
        JestResult result = null;
        try {
            result = esConfig.getClient().execute(get);
            T o = (T) result.getSourceAsObject(object.getClass());
            for (Method method : o.getClass().getMethods()) {
                log.info("getDocument == " + method.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public <T> List<SearchResult.Hit<T, Void>> searchAll(String index, T o) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(index)
                .build();
        SearchResult result;
        List<?> hits = null;
        try {
            result = esConfig.getClient().execute(search);
            System.out.println("本次查询共查到：" + result.getTotal() + "个关键字！");
            log.info("本次查询共查到：" + result.getTotal() + "个关键字！");
            hits = result.getHits(o.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (List<SearchResult.Hit<T, Void>>) hits;
    }

    @Override
    public <T> List<SearchResult.Hit<T, Void>> createSearch(String keyWord, String type, T o, String... fields) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(keyWord));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String field : fields) {
            highlightBuilder.field(field);//高亮field
        }
        highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
        highlightBuilder.fragmentSize(200);//高亮内容长度
        searchSourceBuilder.highlight(highlightBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(type).build();
        SearchResult result;
        List<?> hits = null;
        try {
            result = esConfig.getClient().execute(search);
            System.out.println("本次查询共查到：" + result.getTotal() + "个结果！");

            hits = result.getHits(o.getClass());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (List<SearchResult.Hit<T, Void>>) hits;
    }

    @Override
    public SearchResult search(String indexName, String typeName, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();

        return esConfig.getClient().execute(search);
    }

    @Override
    public String searchAsString(String indexName, String typeName, String query) throws Exception {
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .addType(typeName)
                .build();
        JestResult jr = esConfig.getClient().execute(search);
        return jr.getSourceAsString();
    }
}