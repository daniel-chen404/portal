package com.dj.service;

import com.dj.model.Message;
import com.dj.model.dto.MessageDto;
import com.dj.util.Pager;

import java.util.List;

/**
 * @author d.c
 * @since 2017/4/19
 */
public interface MessageService {

    public int countPage(Message msg);

    public List<MessageDto> pagerAtion(Pager pager);

    public void save(Message msg);
}
