package com.projects.modular.healthPlatform.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projects.core.beetl.LayuiPageFactory;
import com.projects.core.beetl.LayuiPageInfo;
import com.projects.modular.healthPlatform.entity.News;
import com.projects.modular.healthPlatform.mapper.NewsMapper;
import com.projects.modular.healthPlatform.model.params.NewsParam;
import com.projects.modular.healthPlatform.model.result.NewsResult;
import com.projects.modular.healthPlatform.service.NewsService;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author demo
 * @since 2025-01-04
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public void add(NewsParam param){
        News entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(NewsParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(NewsParam param){
        News oldEntity = getOldEntity(param);
        News newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public NewsResult findBySpec(NewsParam param){
        return null;
    }

    @Override
    public List<News> findListBySpec(NewsParam param){
    	   QueryWrapper<News> objectQueryWrapper = new QueryWrapper<>();
    	   if(ToolUtil.isNotEmpty(param.getTitle())) {
     		  objectQueryWrapper.like("title", param.getTitle());
     	  }
        return baseMapper.selectList(objectQueryWrapper);
    }

    @Override
    public LayuiPageInfo findPageBySpec(NewsParam param){
        Page pageContext = getPageContext();
        QueryWrapper<News> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(NewsParam param){
        return param.getNewsId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private News getOldEntity(NewsParam param) {
        return this.getById(getKey(param));
    }

    private News getEntity(NewsParam param) {
        News entity = new News();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
