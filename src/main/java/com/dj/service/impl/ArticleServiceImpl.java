package com.dj.service.impl;

import com.dj.dao.ArticleDao;
import com.dj.model.Article;
import com.dj.model.dto.ArticleDto;
import com.dj.model.dto.ArticleLiteDto;
import com.dj.service.ArticleService;
import com.dj.util.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by SuperS on 15/12/13.
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Override
    public List<ArticleDto> searchArticles(Article article) {
        List<ArticleDto> articleDtoList = null;
        try {
            articleDtoList = articleDao.search(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getPageArticles(Pager pager) {
        List<ArticleDto> articleDtoList = null;
        try {
            articleDtoList = articleDao.pagerAction(pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleDtoList;
    }


    @Override
    public ArticleDto getArticle(Integer id) {
        ArticleDto articleDto = null;
        try {
            articleDto = articleDao.getArticleDto(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleDto;
    }

    @Override
    public ArticleLiteDto getPreArticle(Integer id) {
        ArticleLiteDto articleLiteDto = null;
        try {
            articleLiteDto = articleDao.getPreArticleDto(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleLiteDto;
    }

    @Override
    public ArticleLiteDto getNextArticle(Integer id) {
        ArticleLiteDto articleLiteDto = null;
        try {
            articleLiteDto = articleDao.getNextArticleDto(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articleLiteDto;
    }

    @Override
    public List<ArticleLiteDto> getArticlesByCategory(int categoryId) {
        List<ArticleLiteDto> articleLiteDtos = null;
        try {
            articleLiteDtos = articleDao.getByCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleLiteDtos;
    }

    @Override
    public List<ArticleLiteDto> getArchive() {
        List<ArticleLiteDto> articleLiteDtos = null;
        try {
            articleLiteDtos = articleDao.archive();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleLiteDtos;
    }


    @Override
    public void updateArticle(Article article) {
        try {
            articleDao.update(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveArticle(Article article) {
        try {
            articleDao.save(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteArticle(Integer id) {
        try {
            articleDao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() {
        int count = 0;
        try {
            count = articleDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void updateClicks(Integer clicks, Integer id) {
        Article article = new Article();
        article.setClicks(clicks);
        article.setId(id);
        try {
            articleDao.updateArticleClicks(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<ArticleDto> queryLawRule(Pager pager) {
        List<ArticleDto> list = null;
        try {
            list =articleDao.queryLawRule(pager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countPage(Article article){

        int count = 0;
        try {
            count = articleDao.countPage(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
