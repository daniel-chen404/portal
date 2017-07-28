package com.dj.controller.admin;

import com.dj.constant.AttributeConstant;
import com.dj.model.Message;
import com.dj.model.dto.MessageDto;
import com.dj.model.dto.UserDto;
import com.dj.service.MessageService;
import com.dj.service.WebAppService;
import com.dj.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author d.c
 * @since 2017/4/18
 */
@Controller
@RequestMapping("/manage/message")
public class ManageMessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private WebAppService webAppService;

    //显示用户列表
    @RequestMapping(method = RequestMethod.GET)
    public String showFile(String content, ModelMap model, @RequestParam(defaultValue = "1") Integer currentPage, HttpSession session){
        Message msg = new Message();

        Pager pager = new Pager(currentPage, webAppService.getWebAppDtos().get(0).getAdminPageArticleSize(), messageService.countPage(msg));

        List<MessageDto> list = messageService.pagerAtion(pager);
        model.addAttribute("msgList", list);
        model.addAttribute(AttributeConstant.PAGER, pager);
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        model.addAttribute(AttributeConstant.MAIN_PAGE, "admin/message/listMessage.vm");
        return "admin/index";

    }

    //显示创建页面
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreatePage(ModelMap model, HttpSession session) {
        model.addAttribute(AttributeConstant.MAIN_PAGE, "admin/message/editor.vm");
        model.addAttribute(AttributeConstant.USER, (UserDto) session.getAttribute(AttributeConstant.CURRENT_USER));
        return "admin/index";
    }
}
