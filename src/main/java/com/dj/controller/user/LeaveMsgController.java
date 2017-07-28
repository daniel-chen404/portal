package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.service.WebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by SuperS on 16/2/28.
 * 访客留言页面 调用多说的API接口
 */
@Controller
@RequestMapping("/leave")
public class LeaveMsgController {
    @Autowired
    private WebAppService  webAppService;
    //显示留言页面
    @RequestMapping(method = RequestMethod.GET)
    public String leavePage(ModelMap model){
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));

        model.addAttribute(AttributeConstant.MAIN_PAGE,"user/leave/detail.vm");
        return "index";
    }
}
