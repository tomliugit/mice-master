package info.meihua.logs.service.dao;

import entity.demo.Demo;

/**
 * @author sunwell
 */
public interface IDemoDao {

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws Exception
     */
    Demo get(Long id) throws Exception;
}
