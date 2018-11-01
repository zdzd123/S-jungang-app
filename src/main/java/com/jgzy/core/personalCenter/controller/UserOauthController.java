package com.jgzy.core.personalCenter.controller;


import com.jgzy.core.personalCenter.service.IUserOauthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zou
 * @since 2018-10-24
 */
@RefreshScope
@RestController
@RequestMapping("/api/userOauth")
@Api(value = "用户微信认证信息", description = "用户微信认证信息")
public class UserOauthController {
}

