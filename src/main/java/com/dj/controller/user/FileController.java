package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.model.TextFile;
import com.dj.model.dto.TextFileDto;
import com.dj.model.dto.UserDto;
import com.dj.service.FileService;
import com.dj.service.WebAppService;
import com.dj.util.Pager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author d.c
 * @since 2017/4/23
 */
@Controller
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private WebAppService webAppService;

    @Value("#{configProperties['file.path']}")
    private String filePath;


    @RequestMapping("file")
    public String showFile(String content, ModelMap model, @RequestParam(defaultValue = "1") Integer currentPage, HttpSession session){


        TextFile file = new TextFile();

        Pager pager = new Pager(currentPage, webAppService.getWebAppDtos().get(0).getAdminPageArticleSize(), fileService.countPage(file));

        List<TextFileDto> list = fileService.pagerAtion(pager);
        model.addAttribute("fileList", list);
        model.addAttribute(AttributeConstant.PAGER, pager);
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/file/fileList.vm");
        return "index";

    }
    /**
     * 文件下载
     * @Description:
     * @param id
     * @param response
     * @return
     */
    @RequestMapping("download")
    public String downloadFile(@RequestParam("id") String id, HttpServletResponse response) {

        TextFile textFile = fileService.getTextFile(Integer.valueOf(id));
        System.out.println(filePath);
        System.out.println(textFile.getPath());
        if (textFile.getName() != null) {
            String realPath = filePath+textFile.getPath()+"/";

            File file = new File(realPath, textFile.getName());
            String fileName=textFile.getName().substring(15,textFile.getName().length());
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                try {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                }catch (UnsupportedEncodingException e1){
                    e1.printStackTrace();
                }

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 文件下载
     * @Description:
     * @param type
     * @param response
     * @return
     */
    @RequestMapping("downloadType")
    public String downloadTypeFile(@RequestParam("type") String type, HttpServletResponse response) {

        TextFile textFile = fileService.getOneFileByType(type);
        System.out.println(filePath);
        System.out.println(textFile.getPath());
        if (textFile.getName() != null) {
            String realPath = filePath+textFile.getPath()+"/";

            File file = new File(realPath, textFile.getName());
            String fileName=textFile.getName().substring(15,textFile.getName().length());
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                try {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                }catch (UnsupportedEncodingException e1){
                    e1.printStackTrace();
                }

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
