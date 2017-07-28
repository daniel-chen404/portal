package com.dj.service;

import com.dj.model.WebApp;
import com.dj.model.dto.WebAppDto;

import java.util.List;

/**
 * Created by SuperS on 16/3/16.
 */
public interface WebAppService {
    public void saveWebApp(WebApp webApp);
    public void updateWebApp(WebApp webApp);
    public WebAppDto getWebDtoWebApp(Integer id);
    public Integer getArticlesView();
    public boolean webAppNotNull();
    public List<WebApp> getWebAppDtos();
}
