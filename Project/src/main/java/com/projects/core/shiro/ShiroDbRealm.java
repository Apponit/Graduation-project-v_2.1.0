/**
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.projects.core.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.projects.core.shiro.service.UserAuthService;
import com.projects.core.shiro.service.UserAuthServiceServiceImpl;
import com.projects.modular.system.entity.User;

import cn.stylefeng.roses.core.util.ToolUtil;

public class ShiroDbRealm extends AuthorizingRealm {
	@Autowired
	private SessionDAO sessionDAO;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UserAuthService shiroFactory = UserAuthServiceServiceImpl.me();
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = shiroFactory.user(token.getUsername());
		ShiroUser shiroUser = shiroFactory.shiroUser(user);
		// 执行shiro登录操作
		String username = (String) token.getPrincipal();

		Session session = ShiroKit.getSubject().getSession();
		session.setAttribute("loginedUser", username);
		return shiroFactory.info(shiroUser, user, super.getName());
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserAuthService shiroFactory = UserAuthServiceServiceImpl.me();
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		List<Long> roleList = shiroUser.getRoleList();

		Set<String> permissionSet = new HashSet<>();
		Set<String> roleNameSet = new HashSet<>();

		for (Long roleId : roleList) {
			List<String> permissions = shiroFactory.findPermissionsByRoleId(roleId);
			if (permissions != null) {
				for (String permission : permissions) {
					if (ToolUtil.isNotEmpty(permission)) {
						permissionSet.add(permission);
					}
				}
			}
			String roleName = shiroFactory.findRoleNameByRoleId(roleId);
			roleNameSet.add(roleName);
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissionSet);
		info.addRoles(roleNameSet);
		return info;
	}

	/**
	 * 设置认证加密方式
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
		md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
		md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
		super.setCredentialsMatcher(md5CredentialsMatcher);
	}
}
