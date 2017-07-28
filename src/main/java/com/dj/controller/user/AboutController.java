package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.service.AboutService;
import com.dj.service.WebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SuperS on 16/2/28.
 * 访客About页面
 */
@Controller
public class AboutController {
    @Autowired
    private AboutService aboutService;
    @Autowired
    private WebAppService webAppService;

    //显示about页面
    @RequestMapping("/about")
    public String aboutPage(ModelMap model){
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        model.addAttribute(AttributeConstant.MAIN_PAGE,"user/about/about.vm");
        if(aboutService.list().size() > 0) {
            model.addAttribute(AttributeConstant.ABOUT, aboutService.getAbout(aboutService.list().get(0).getId()));
        }
        return "index";
    }
}
