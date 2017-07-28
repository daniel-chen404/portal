package com.dj.controller.admin;

import com.dj.constant.AttributeConstant;
import com.dj.model.WebApp;
import com.dj.model.dto.WebAppDto;
import com.dj.service.WebAppService;
import com.dj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by SuperS on 16/3/16.
 */
@Controller
@RequestMapping("/manage/web")
public class ManageWebAppController {
    @Autowired
    private WebAppService webAppService;

    @RequestMapping(method = RequestMethod.GET)
    public String showWebAppPage(ModelMap model, HttpSession session) {
        model.addAttribute(AttributeConstant.USER, session.getAttribute(AttributeConstant.CURRENT_USER));
        model.addAttribute(AttributeConstant.MAIN_PAGE, "admin/home/home.vm");
        WebAppDto webAppDto = webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId());
        webAppDto.setArticleViews(webAppService.getArticlesView());
        model.addAttribute(AttributeConstant.WEB_APP_DTO, webAppDto);
        return "admin/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String editWebApp(WebApp webApp, ModelMap model, HttpSession session) {
        webApp.setId(webAppService.getWebAppDtos().get(0).getId());
        model.addAttribute(AttributeConstant.USER, session.getAttribute(AttributeConstant.CURRENT_USER));
        if (webAppService.webAppNotNull()) {
            //表中只有1个webApp配置
            if(StringUtil.isEmpty(webApp.getWebName())){
                webApp.setWebName(webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()).getWebName());
            }

            if(StringUtil.isEmpty(webApp.getWebTitle())){
                webApp.setWebTitle(webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()).getWebTitle());
            }
            webAppService.updateWebApp(webApp);
        } else {
            webAppService.saveWebApp(webApp);
        }
        return "redirect:/manage/web";
    }
}
