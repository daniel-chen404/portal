package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.model.dto.ArticleLiteDto;
import com.dj.service.ArticleService;
import com.dj.service.WebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by SuperS on 16/2/28.
 * 访客 归档页面
 */
@Controller
@RequestMapping("archive")
public class ArchiveController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private WebAppService webAppService;
     //显示归档页面
    @RequestMapping(method = RequestMethod.GET)
    public String archive(ModelMap model){
        List<ArticleLiteDto> articleLiteDtos = articleService.getArchive();
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        model.addAttribute(AttributeConstant.ARTICLES, articleLiteDtos);
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/archive/detail.vm");
        return "index";
    }
}
