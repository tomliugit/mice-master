package info.meihua.message.service.service.impl;

import entity.message.Message;
import info.meihua.message.service.dao.IMessageDao;
import info.meihua.message.service.service.IMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunwell
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
public class MsgServiceImpl implements IMsgService {

    private Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);

    @Autowired
    private IMessageDao userMessageDao = null;

    @Override
    public Long add(String msg, Integer type, Integer scope, Long userId, String appCode)
            throws Exception {
        if (type == Message.TYPE_ONLINE) {
//            在线消息类型
            userMessageDao.updateStatusByType(Message.STATUS_READ, type, userId, appCode, System.currentTimeMillis());
        }
        Message m = new Message();
        m.setScope(scope);
        m.setAppCode(appCode);
        m.setType(type);
        m.setContent(msg);
        m.setStatus(Message.STATUS_UNREAD);
        m.setUserId(userId);
        m.setGmtCreate(System.currentTimeMillis());
        m.setGmtModified(System.currentTimeMillis());
        userMessageDao.add(m);
        return m.getId();
    }


    @Override
    public Map<String, Object> list(Integer status, Integer type, Integer scope, Long userId, String appCode, Integer start, Integer limit)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Message> list = userMessageDao.list(status, type, scope, userId, appCode, start, limit);
        long count = userMessageDao.count(status, type, scope, userId, appCode);

        result.put("list", list);
        result.put("total", count);
        return result;
    }

    @Override
    public void doRead(Long id, Long userId, String appCode)
            throws Exception {
        userMessageDao.updateStatusById(Message.STATUS_READ, id, userId, appCode, System.currentTimeMillis());
    }

    @Override
    public void doReadBatch(List<Long> ids, Long userId, String appCode)
            throws Exception {
        for (Long id : ids) {
            userMessageDao.updateStatusById(Message.STATUS_READ, id, userId, appCode, System.currentTimeMillis());
        }
    }

    @Override
    public void doReadBatchByTypeScope(Integer type, Integer scope, Long userId, String appCode) throws Exception {
        userMessageDao.updateStatusByTypeScope(Message.STATUS_READ, type, scope, userId, appCode, System.currentTimeMillis());
    }


}
