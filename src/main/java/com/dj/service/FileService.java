package com.dj.service;

import com.dj.model.TextFile;
import com.dj.model.dto.TextFileDto;
import com.dj.util.Pager;

import java.util.List;

/**
 * @author d.c
 * @since 2017/4/18
 */
public interface FileService {


    public int countPage(TextFile file);

    public List<TextFileDto> pagerAtion(Pager pager);

    public void saveFile(TextFile file);

    public TextFile getTextFile(Integer id);

    public TextFile getOneFileByType(String type);


}
