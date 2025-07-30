package org.junle.web.controller.system;

import java.util.List;
import java.util.Set;

import org.junle.common.constant.Constants;
import org.junle.common.core.domain.AjaxResult;
import org.junle.common.core.domain.entity.SysMenu;
import org.junle.common.core.domain.entity.SysUser;
import org.junle.common.core.domain.model.LoginBody;
import org.junle.common.core.domain.model.LoginUser;
import org.junle.common.utils.SecurityUtils;
import org.junle.common.utils.sign.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.junle.framework.web.service.SysLoginService;
import org.junle.framework.web.service.SysPermissionService;
import org.junle.framework.web.service.TokenService;
import org.junle.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author elnujuw
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        String password = null;
        try {
            password = RsaUtils.decryptByPrivateKey(loginBody.getPassword());
        } catch (Exception e) {
            return AjaxResult.error("密码解码失败");
        }
        // 生成令牌
        String token = loginService.login(
                loginBody.getUsername(),
                password,
                loginBody.getCode(),
                loginBody.getUuid()
        );
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
