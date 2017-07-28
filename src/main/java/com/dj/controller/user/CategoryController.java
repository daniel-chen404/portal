package com.dj.controller.user;

import com.dj.constant.AttributeConstant;
import com.dj.model.Category;
import com.dj.model.dto.ArticleLiteDto;
import com.dj.model.dto.CategoryDto;
import com.dj.service.ArticleService;
import com.dj.service.CategoryService;
import com.dj.service.WebAppService;
import com.dj.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by SuperS on 16/2/28.
 * 访客 分类页面
 */
@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WebAppService webAppService;
//    //分页list 需要自己 写分页 的前端页面.
//    @RequestMapping("list/{pageIndex:[0-9]+}")
//    public String listPage(ModelMap model,@PathVariable("pageIndex")Integer pageIndex){
//        Pager pager = new Pager(pageIndex,10,categoryService.getCount());
//        List<CategoryDto> categories = categoryService.getPageCategories(pager);
//        model.addAttribute(Constant.MAIN_PAGE,"category/categoryList.vm");
//        model.addAttribute("pager",pager);
//        model.addAttribute(Constant.CATEGORIES,categories);
//        return "index";
//    }

    //全部list
    @RequestMapping("list")
    public String list(ModelMap model) {
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/category/categoryList.vm");
        model.addAttribute(AttributeConstant.CATEGORIES, categories);
        return "index";
    }

    //详情
    @RequestMapping("{categoryId:[0-9]+}")
    public String detail(@PathVariable("categoryId") Integer categoryId, ModelMap model) {
        model.addAttribute(AttributeConstant.WEB_APP_DTO,webAppService.getWebDtoWebApp(webAppService.getWebAppDtos().get(0).getId()));

        Category category = categoryService.getCategory(categoryId);
        model.addAttribute(AttributeConstant.MAIN_PAGE, "user/category/detail.vm");
        if (StringUtil.isNotEmpty(category.getName())) {
            List<ArticleLiteDto> articles = articleService.getArticlesByCategory(categoryId);
            if (articles.size() == 0) {
                articles = null;
            }
            model.addAttribute(AttributeConstant.CATEGORY, category);
            model.addAttribute(AttributeConstant.ARTICLES, articles);
        } else {
            model.addAttribute(AttributeConstant.ERROR, "找不到该分类");
        }
        return "index";
    }


}
