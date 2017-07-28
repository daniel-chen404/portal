package com.dj.dao;

import com.dj.model.Message;
import com.dj.model.dto.MessageDto;
import com.dj.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author d.c
 * @since 2017/4/19
 */
@Repository
public interface MessageDao {
    public int countPage(Message msg) throws Exception;

    public List<MessageDto> pagerAtion(Pager pager) throws Exception;

    public void save(Message msg) throws Exception;
}
