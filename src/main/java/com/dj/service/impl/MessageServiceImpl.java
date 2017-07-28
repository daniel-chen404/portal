package com.dj.service.impl;

import com.dj.dao.MessageDao;
import com.dj.model.Message;
import com.dj.model.dto.MessageDto;
import com.dj.service.MessageService;
import com.dj.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author d.c
 * @since 2017/4/19
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    @Override
    public int countPage(Message msg) {
        int count = 0;
        try {
            count = messageDao.countPage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<MessageDto> pagerAtion(Pager pager) {
        List<MessageDto> list = null;
        try {
            list = messageDao.pagerAtion(pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Message msg) {
        try {
            messageDao.save(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
