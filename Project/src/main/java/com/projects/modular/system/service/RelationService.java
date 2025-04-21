package com.projects.modular.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.projects.modular.system.entity.Relation;
import com.projects.modular.system.mapper.RelationMapper;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>

 */
@Service
public class RelationService extends ServiceImpl<RelationMapper, Relation> {

}
