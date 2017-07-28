package com.dj.service.impl;

import com.dj.dao.FileDao;
import com.dj.model.TextFile;
import com.dj.model.dto.TextFileDto;
import com.dj.service.FileService;
import com.dj.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author d.c
 * @since 2017/4/18
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    public int countPage(TextFile file){

        int count = 0;
        try {
            count = fileDao.countPage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<TextFileDto> pagerAtion(Pager pager) {
        List<TextFileDto> list = null;
        try {
            list = fileDao.pagerAtion(pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void saveFile(TextFile file) {
        try {
            fileDao.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public TextFile getTextFile(Integer id){
        TextFile textFile =null;
        try {
            textFile = fileDao.getTextFile(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textFile;
    }

    public TextFile getOneFileByType(String type){
        TextFile textFile =null;
        TextFile txFile = new TextFile();
        txFile.setType(type);
        try {
            textFile = fileDao.getOneFileByType(txFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textFile;
    }
}
