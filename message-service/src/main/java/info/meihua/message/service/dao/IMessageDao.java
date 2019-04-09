package info.meihua.message.service.dao;

import entity.message.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunwell
 */
public interface IMessageDao {

    /**
     * 新增消息
     *
     * @param message
     * @throws Exception
     */
    void add(Message message) throws Exception;

    /**
     * 获取列表
     *
     * @param appCode
     * @param userId
     * @param scope
     * @param type
     * @param status
     * @param start
     * @param limit
     * @return
     * @throws Exception
     */
    List<Message> list(@Param("status") Integer status,
                       @Param("type") Integer type,
                       @Param("scope") Integer scope,
                       @Param("userId") Long userId,
                       @Param("appCode") String appCode,
                       @Param("start") Integer start,
                       @Param("limit") Integer limit) throws Exception;

    /**
     * 消息数量
     *
     * @param userId
     * @param appCode
     * @param scope
     * @param type
     * @param status
     * @return
     * @throws Exception
     */
    Integer count(@Param("status") Integer status,
                  @Param("type") Integer type,
                  @Param("scope") Integer scope,
                  @Param("userId") Long userId,
                  @Param("appCode") String appCode) throws Exception;


    /**
     * 修改状态
     *
     * @param id
     * @param userId
     * @param status
     * @param appCode
     * @param gmtModified
     * @throws Exception
     */
    void updateStatusById(@Param("status") int status,
                          @Param("id") Long id,
                          @Param("userId") Long userId,
                          @Param("appCode") String appCode,
                          @Param("gmtModified") Long gmtModified) throws Exception;

    /**
     * 修改状态
     *
     * @param type
     * @param userId
     * @param status
     * @param appCode
     * @param gmtModified
     * @throws Exception
     */
    void updateStatusByType(@Param("status") int status,
                            @Param("type") Integer type,
                            @Param("userId") Long userId,
                            @Param("appCode") String appCode,
                            @Param("gmtModified") Long gmtModified) throws Exception;

    /**
     * 修改状态
     *
     * @param scope
     * @param userId
     * @param status
     * @param gmtModified
     * @throws Exception
     */
    void updateStatusByScope(@Param("status") int status,
                             @Param("scope") Integer scope,
                             @Param("userId") Long userId,
                             @Param("appCode") String appCode,
                             @Param("gmtModified") Long gmtModified) throws Exception;

    /**
     * 修改状态
     *
     * @param scope
     * @param type
     * @param appCode
     * @param userId
     * @param status
     * @param gmtModified
     * @throws Exception
     */
    void updateStatusByTypeScope(@Param("status") int status,
                                 @Param("type") Integer type,
                                 @Param("scope") Integer scope,
                                 @Param("userId") Long userId,
                                 @Param("appCode") String appCode,
                                 @Param("gmtModified") Long gmtModified) throws Exception;

}
