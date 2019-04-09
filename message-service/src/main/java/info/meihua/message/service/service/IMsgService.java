package info.meihua.message.service.service;

import java.util.List;
import java.util.Map;

/**
 * @author sunwell
 */
public interface IMsgService {


    /**
     * 新增消息
     *
     * @param appCode
     * @param msg
     * @param type
     * @param scope
     * @param userId
     * @return
     * @throws Exception
     */
    Long add(String msg, Integer type, Integer scope, Long userId, String appCode) throws Exception;

    /**
     * 获取消息列表
     *
     * @param userId
     * @param scope
     * @param type
     * @param status
     * @param start
     * @param limit
     * @return
     * @throws Exception
     */
    Map<String, Object> list(Integer status, Integer type, Integer scope, Long userId, String appCode, Integer start, Integer limit) throws Exception;

    /**
     * 读消息
     *
     * @param appCode
     * @param id
     * @param userId
     * @throws Exception
     */
    void doRead(Long id, Long userId, String appCode) throws Exception;

    /**
     * 读消息
     *
     * @param appCode
     * @param ids
     * @param userId
     * @throws Exception
     */
    void doReadBatch(List<Long> ids, Long userId, String appCode) throws Exception;

    /**
     * 读消息
     *
     * @param type
     * @param scope
     * @param userId
     * @param appCode
     * @throws Exception
     */
    void doReadBatchByTypeScope(Integer type, Integer scope, Long userId, String appCode) throws Exception;

}
