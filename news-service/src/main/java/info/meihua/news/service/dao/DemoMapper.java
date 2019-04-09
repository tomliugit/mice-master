package info.meihua.news.service.dao;

import entity.demo.Demo;
import org.apache.ibatis.annotations.*;

/**
 * @author sunwell
 */
@Mapper
public interface DemoMapper {

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    Demo get(@Param("id") Long id) throws Exception;
}
