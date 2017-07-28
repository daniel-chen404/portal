package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.model.Article;
import com.dj.model.dto.ArticleDto;
import com.dj.model.dto.UserDto;
import com.dj.service.ArticleService;
import com.dj.service.WebAppService;
import com.dj.util.Pager;
import com.dj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by SuperS on 16/2/26.
 * 访客 文章页面
 */
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WebAppService webAppService;
    //显示 详细文章
    @RequestMapping("article/{articleId:[0-9]+}")
    public String showArticle(@PathVariable("articleId")Integer articleId,ModelMap model){
        ArticleDto articleDto = articleService.getArticle(articleId);
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/article/detail.vm");
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        if(StringUtil.isNotEmpty(articleDto.getTitle())) {
            //点击量+1
            articleService.updateClicks(articleDto.getClicks() + 1, articleDto.getId());
            //更新一下用于显示
            articleDto.setClicks(articleDto.getClicks() + 1);
        /*    //获取上一篇文章
            ArticleLiteDto preArticle = articleService.getPreArticle(articleDto.getId());
            //获取下一篇文章
            ArticleLiteDto nextArticle = articleService.getNextArticle(articleDto.getId());*/
            model.addAttribute(AttributeConstant.ARTICLE, articleDto);
           /* model.addAttribute("preArticle", preArticle);
            model.addAttribute("nextArticle", nextArticle);*/
        }else{
            model.addAttribute(AttributeConstant.ERROR,"没有此文章");
        }
        return "index";
    }
    //搜索操作
    @RequestMapping(value = "search",method = RequestMethod.POST)
    public String search(String content,@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue ="0" ) Integer categoryId, ModelMap model, HttpSession session) {
       return searchInfo(content,currentPage,categoryId,model,session);
    }


    //显示文章列表
    @RequestMapping(value = "search",method = RequestMethod.GET)
    public String showListArticle(ModelMap model, @RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue ="0" ) Integer categoryId,String content, HttpSession session) {

        return searchInfo(content,currentPage,categoryId,model,session);
    }

    protected  String searchInfo(String content,@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue ="0" ) Integer categoryId, ModelMap model, HttpSession session){

        Article article = new Article();
        article.setTitle(content);
        article.setCategoryId(categoryId);
        Pager pager = new Pager(currentPage, webAppService.getWebAppDtos().get(0).getAdminPageArticleSize(), articleService.countPage(article));
        pager.setCategoryId(categoryId);
        pager.setTitle(content);

        List<ArticleDto> articles = articleService.getPageArticles(pager);
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        model.addAttribute(AttributeConstant.ARTICLES, articles);
        model.addAttribute(AttributeConstant.PAGER, pager);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/article/search.vm");
        return "index";
    }

}
