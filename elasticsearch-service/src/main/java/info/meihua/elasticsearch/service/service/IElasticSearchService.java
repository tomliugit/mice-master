package info.meihua.elasticsearch.service.service;

import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import org.elasticsearch.common.settings.Settings;

import java.util.List;
import java.util.Map;

/**
 * @author sunwell
 */
public interface IElasticSearchService {

    /**
     * 判断索引是否存在
     *
     * @return
     */
    JestResult indexExists(String index);

    /**
     * 创建索引
     *
     * @param indexName
     * @param setting
     * @param analysis
     * @return
     */
    Boolean createIndex(String indexName, Settings.Builder setting, String analysis);

    /**
     * 创建索引
     *
     * @param o     ：返回对象
     * @param index ：文档在哪存放
     * @param type  ： 文档表示的对象类别
     * @return
     */
    <T> JestResult createIndex(T o, String index, String type);

    /**
     * bulkIndex
     *
     * @param index
     * @param type
     * @param o
     * @param <T>
     */
    <T> void bulkIndex(String index, String type, T o);

    /**
     * 删除索引
     *
     * @param index ：当前删除索引名称
     * @return
     */
    JestResult deleteIndex(String index);

    /**
     * 关闭索引
     *
     * @param index ：索引名称
     * @return
     */
    JestResult closeIndex(String index);

    /**
     * 刷新索引
     *
     * @return
     */
    JestResult flushIndex();


    /**
     * 优化索引
     *
     * @return
     */
    JestResult optimizeIndex();

    /**
     * 获取mapping
     *
     * @param indexName
     * @param typeName
     * @return
     * @throws Exception
     */
    String getMapping(String indexName, String typeName) throws Exception;

    /**
     * 设置mapping
     *
     * @param indexName
     * @param typeName
     * @param source
     * @return
     * @throws Exception
     */
    boolean putMapping(String indexName, String typeName, Object source) throws Exception;

    /**
     * 清除缓存
     *
     * @return
     */
    JestResult clearCache();

    /**
     * 查看节点信息
     *
     * @return
     */
    JestResult nodesInfo();

    /**
     * 节点状态
     *
     * @return
     */
    JestResult nodesStats();

    /**
     * 查看集群健康信息
     *
     * @return
     */
    JestResult health();

    /**
     * 新建文档
     *
     * @param indexName
     * @param typeName
     * @param object
     * @param params
     * @return
     * @throws Exception
     */
    boolean insertDocument(String indexName, String typeName, Object object, Map<String, Object> params) throws Exception;

    /**
     * 批量新建文档
     *
     * @param indexName
     * @param typeName
     * @param objects
     * @return
     * @throws Exception
     */
    boolean insertDocumentBatch(String indexName, String typeName, List<Object> objects) throws Exception;

    /**
     * 更新Document
     *
     * @param index ：文档在哪存放
     * @param type  ： 文档表示的对象类别
     * @param id    ：文档唯一标识
     */
    JestResult updateDocument(String script, String index, String type, String id);

    /**
     * 删除Document
     *
     * @param index ：文档在哪存放
     * @param type  ： 文档表示的对象类别
     * @param id    ：文档唯一标识
     * @return
     */
    JestResult deleteDocument(String index, String type, String id);

    /**
     * 根据条件删除
     *
     * @param index
     * @param type
     * @param params
     */
    JestResult deleteDocumentByQuery(String index, String type, String params);


    /**
     * 获取Document
     *
     * @param o     ：返回对象
     * @param index ：文档在哪存放
     * @param type  ： 文档表示的对象类别
     * @param id    ：文档唯一标识
     * @return
     */
    <T> JestResult getDocument(T o, String index, String type, String id);

    /**
     * 查询
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    SearchResult search(String indexName, String typeName, String query) throws Exception;

    /**
     * 查询
     *
     * @param indexName
     * @param typeName
     * @param query
     * @return
     * @throws Exception
     */
    String searchAsString(String indexName, String typeName, String query) throws Exception;


    /**
     * 查询全部
     *
     * @param index ：文档在哪存放
     * @return
     */
    <T> List<SearchResult.Hit<T, Void>> searchAll(String index, T o);

    /**
     * 搜索
     *
     * @param keyWord ：搜索关键字
     * @return
     */
    <T> List<SearchResult.Hit<T, Void>> createSearch(String keyWord, String type, T o, String... fields);


}