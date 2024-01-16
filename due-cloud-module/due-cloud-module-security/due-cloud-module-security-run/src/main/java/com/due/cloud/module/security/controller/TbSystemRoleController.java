package com.due.cloud.module.security.controller;


import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.module.security.domain.response.SystemRole;
import com.due.cloud.module.security.service.ITbSystemRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author HanWenGang
 * @since 2023-12-29
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class TbSystemRoleController extends BasicController {

    private ITbSystemRoleService systemRoleService;

    @GetMapping("/path")
    public Result<List<SystemRole>> listByPath(@RequestParam("pathUrl") String pathUrl) {
        return Result.exec(() -> this.copyList(this.systemRoleService.selectDataListByPath(pathUrl), SystemRole.class));
    }

    @GetMapping("/member-id")
    public Result<List<SystemRole>> listByMemberId(@RequestParam("memberId") Long memberId) {
        return Result.exec(() -> this.copyList(this.systemRoleService.selectDataListByMemberId(memberId), SystemRole.class));
    }
}

