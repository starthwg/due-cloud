package com.due.cloud.service.back.controller.sercurity;

import com.due.basic.tookit.doamin.Result;
import com.due.bridge.tomcat.support.BasicController;
import com.due.cloud.service.back.domian.response.SystemRoleVo;
import com.due.cloud.service.back.domian.reuqest.SystemRoleQuery;
import com.due.cloud.service.back.service.security.ISystemRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@AllArgsConstructor
public class SysRoleController extends BasicController {


    private final ISystemRoleService systemRoleService;


    @GetMapping("/list")
    public Result<List<SystemRoleVo>> selectListRole(SystemRoleQuery query) {
        return Result.exec(() -> this.copyList(this.systemRoleService.selectSystemRoleListCondition(query), SystemRoleVo.class));
    }
}
