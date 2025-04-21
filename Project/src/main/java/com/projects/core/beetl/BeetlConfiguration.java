
package com.projects.core.beetl;

import static com.projects.core.common.constant.factory.Const.DEFAULT_SYSTEM_NAME;
import static com.projects.core.common.constant.factory.Const.DEFAULT_WELCOME_TIP;

import java.util.HashMap;
import java.util.Map;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * 

 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    public void initOther() {

        //全局共享变量
        Map<String, Object> shared = new HashMap<>();
        shared.put("systemName", DEFAULT_SYSTEM_NAME);
        shared.put("welcomeTip", DEFAULT_WELCOME_TIP);
        groupTemplate.setSharedVars(shared);

        //全局共享方法
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
       
    }
}
