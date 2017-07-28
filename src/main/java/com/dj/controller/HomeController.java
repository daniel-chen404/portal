package com.dj.controller;

import com.dj.constant.AttributeConstant;
import com.dj.model.WebApp;
import com.dj.model.dto.ArticleDto;
import com.dj.service.ArticleService;
import com.dj.service.WebAppService;
import com.dj.util.Pager;
import com.dj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by SuperS on 16/2/26.
 * 网站首页 负责显示文章列表 分页数为4
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WebAppService webAppService;

    //显示首页 分页文章列表
    @RequestMapping(method = RequestMethod.GET)
    public String home(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex, ModelMap model, HttpServletRequest request) {
        String link = "index";
        if (webAppService.getWebAppDtos().size() == 0) {
            link = "redirect:/init";
        } else {
            model.addAttribute(AttributeConstant.MAIN_PAGE, "user/article/articlelist.vm");
            model.addAttribute(AttributeConstant.WEB_APP_DTO, webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));

            Pager pager1 = new Pager(1,9,9);
            Pager pager2 = new Pager(1,8,8);

            pager1.setCategoryId(1);
            List<ArticleDto> lowRuleList =articleService.queryLawRule(pager1);
            pager2.setCategoryId(2);
            List<ArticleDto> tenderList =articleService.queryLawRule(pager2);
            pager2.setCategoryId(3);
            List<ArticleDto> clarifyList =articleService.queryLawRule(pager2);
            pager2.setCategoryId(4);
            List<ArticleDto> winBidList =articleService.queryLawRule(pager2);
            model.addAttribute(AttributeConstant.LAW_RULES, lowRuleList);
            model.addAttribute(AttributeConstant.TENDER, tenderList);
            model.addAttribute(AttributeConstant.CLARIFY, clarifyList);
            model.addAttribute(AttributeConstant.WIN_BID, winBidList);

            String contextPath = request.getContextPath() + "/";
            String imgPath = contextPath + "img";
            request.getSession().setAttribute("imgPath",imgPath);
        }
        return link;
    }

    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String init(ModelMap model) {
        return "admin/init/init";
    }

    @RequestMapping(value = "init", method = RequestMethod.POST)
    public String initAction(ModelMap model, WebApp webApp) {
        String link = "redirect:/init";
        if (StringUtil.isNotEmpty(webApp.getWebName()) && StringUtil.isNotEmpty(webApp.getWebTitle())) {
            if (StringUtil.isNotEmpty(webApp.getAdminPageArticleSize().toString()) && StringUtil.isNotEmpty(webApp.getUserPageArticleSize().toString())) {
                webAppService.saveWebApp(webApp);
                link = "redirect:/";
            }
        }
        return link;
    }
}