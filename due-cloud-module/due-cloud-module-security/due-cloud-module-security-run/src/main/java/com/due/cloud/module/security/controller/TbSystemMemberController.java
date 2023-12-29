package com.due.cloud.module.security.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.module.security.domain.response.SystemMember;
import com.due.cloud.module.security.service.ITbSystemMemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class TbSystemMemberController extends BasicController {


    private final ITbSystemMemberService systemMemberService;


    @GetMapping("/username")
    public Result<SystemMember> username(@RequestParam("username") String userName) {
        return Result.exec(() -> this.copy(this.systemMemberService.selectSystemMemberByUsername(userName), SystemMember.class));
    }

}

