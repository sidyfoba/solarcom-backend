package com.slcm.iam.web;

import com.slcm.iam.dto.PermissionDto;
import com.slcm.iam.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto dto) {
        return ResponseEntity.ok(permissionService.createPermission(dto));
    }

    @GetMapping
    public ResponseEntity<List<PermissionDto>> listPermissions() {
        return ResponseEntity.ok(permissionService.listPermissions());
    }
}
