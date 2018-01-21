package com.dj.dao;

import com.dj.model.TextFile;
import com.dj.model.dto.TextFileDto;
import com.dj.util.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author d.c
 * @since 2017/4/18
 */
@Repository
public interface FileDao {

    public int countPage(TextFile file) throws Exception;

    public List<TextFileDto> pagerAtion(Pager pager) throws Exception;

    public void save(TextFile file) throws Exception;

    public TextFile getTextFile(Integer id) throws  Exception;

    public TextFile getOneFileByType(TextFile file) throws Exception;


    public Integer delete(Integer id) throws  Exception;

}
