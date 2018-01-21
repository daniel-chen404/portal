package com.dj.controller.admin;

import com.dj.constant.AttributeConstant;
import com.dj.model.TextFile;
import com.dj.model.dto.TextFileDto;
import com.dj.model.dto.UserDto;
import com.dj.service.FileService;
import com.dj.service.WebAppService;
import com.dj.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author d.c
 * @since 2017/4/18
 */
@Controller
@RequestMapping("/manage/file")
public class ManageFileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private WebAppService webAppService;

    @Value("#{configProperties['file.path']}")
    private String filePath;

    //显示用户列表
    @RequestMapping(method = RequestMethod.GET)
    public String showFile(String content, ModelMap model, @RequestParam(defaultValue = "1") Integer currentPage, HttpSession session){
        TextFile file = new TextFile();

        Pager pager = new Pager(currentPage, webAppService.getWebAppDtos().get(0).getAdminPageArticleSize(), fileService.countPage(file));

        List<TextFileDto> list = fileService.pagerAtion(pager);
        model.addAttribute("fileList", list);
        model.addAttribute(AttributeConstant.PAGER, pager);
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        model.addAttribute(AttributeConstant.MAIN_PAGE, "admin/file/listFile.vm");
        return "admin/index";

    }

    //显示创建页面
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreatePage(ModelMap model, HttpSession session) {
        model.addAttribute(AttributeConstant.MAIN_PAGE, "admin/file/editor.vm");
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        return "admin/index";
    }

    //显示创建页面
    @RequestMapping(value = "delete/{id:[0-9]+}")
    public String delete(@PathVariable("id") Integer id, HttpSession session) {
        TextFile textFile = fileService.getTextFile(id);
        String realPath = filePath+textFile.getPath()+"/";

        File file = new File(realPath, textFile.getName());
        file.delete();
        fileService.delete(id);
        return "redirect:/manage/file";
    }

   @RequestMapping(value ="uploadFile",method = RequestMethod.POST)
   public String upload( ModelMap model,@RequestParam(value = "file", required = false) MultipartFile file, TextFile textFile,HttpServletRequest request) throws IOException {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
       SimpleDateFormat sd = new SimpleDateFormat("yyMMddHHmmssSSS");
       String date=formatter.format(new Date());
       String path =filePath+date+"/";
       String fileName = sd.format(new Date()).concat(file.getOriginalFilename());
       File tfile = new File(path, fileName);
       if(!tfile.exists()){
           tfile.mkdirs();
       }

       //保存
       try {
           file.transferTo(tfile);
           textFile.setPath(date);
           SimpleDateFormat createsd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           textFile.setCreateDate(createsd.format(new Date()));
           textFile.setName(fileName);
           textFile.setRemark(file.getOriginalFilename());
           fileService.saveFile(textFile);
           return "redirect:/manage/file";
       } catch (Exception e) {
           e.printStackTrace();
       }

       return "admin/index";
   }
}
