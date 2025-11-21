package com.slcm.iam.web;

import com.slcm.iam.dto.CreateRoleRequest;
import com.slcm.iam.dto.RoleDto;
import com.slcm.iam.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleRequest req) {
        return ResponseEntity.ok(roleService.createRole(req));
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> listRoles() {
        return ResponseEntity.ok(roleService.listRoles());
    }

    @PostMapping("/{roleCode}/permissions/{permissionCode}")
    public ResponseEntity<Void> addPermissionToRole(@PathVariable("roleCode") String roleCode,
                                                    @PathVariable String permissionCode) {
        roleService.addPermissionToRole(roleCode, permissionCode);
        return ResponseEntity.noContent().build();
    }
}
