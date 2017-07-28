package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.model.Message;
import com.dj.model.dto.UserDto;
import com.dj.service.MessageService;
import com.dj.service.WebAppService;
import com.dj.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author d.c
 * @since 2017/4/23
 */
@Controller
public class MessageController {

    @Resource
    private MessageService messageService;
    @Resource
    private WebAppService webAppService;


    @RequestMapping("message")
    public String showMessage(HttpServletRequest request, ModelMap model) {

        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/message/message.vm");
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        return "index";

    }

    @RequestMapping(value = "message/create", method = RequestMethod.POST)
    public String createMessage(Message message, HttpSession session, ModelMap model) {

        String path;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setCreateDate(formatter.format(new Date()));
        if (StringUtil.isEmpty(message.getTitle())){
            path=errorMsg(model,"标题不能为空",session);
        }else  if (StringUtil.isEmpty(message.getContent())){
            path=errorMsg(model,"内容不能为空",session);
        }else if (StringUtil.isEmpty(message.getCompany())){
            path=errorMsg(model,"单位名称不能为空",session);
        }else{
            messageService.save(message);
            model.addAttribute(AttributeConstant.ERROR, "留言发布成功！");
            model.addAttribute(AttributeConstant.MAIN_PAGE, "/user/message/message.vm");
            path="index";
        }
        return path;
    }

    protected String errorMsg(ModelMap model,String errorMsg,HttpSession session){
        model.addAttribute(AttributeConstant.ERROR, errorMsg);
        model.addAttribute(AttributeConstant.MAIN_PAGE, "/user/message/error.vm");
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        return "index";
    }
}
