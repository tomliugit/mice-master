package info.meihua.news.service.service.impl;

import entity.demo.Demo;
import info.meihua.news.service.dao.DemoMapper;
import info.meihua.news.service.service.IDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author sunwell
 */
@Service
@SuppressWarnings({"SpringJavaAutowiringInspection", "unchecked"})
public class DemoServiceImpl implements IDemoService {

    private Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private final DemoMapper demoDao;

    @Autowired
    public DemoServiceImpl(DemoMapper demoDao) {
        this.demoDao = demoDao;
    }

    @Override
    public Demo get()
            throws Exception {
        return demoDao.get(1L);

    }

}
