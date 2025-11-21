package com.slcm.iam.web;

import com.slcm.iam.dto.AssignUserRoleRequest;

import com.slcm.iam.dto.CreateUserRequest;
import com.slcm.iam.dto.UserAccountDto;
import com.slcm.iam.dto.UserRoleDto;
import com.slcm.iam.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.slcm.iam.dto.UpdateUserRequest;
import com.slcm.iam.dto.ResetPasswordRequest;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {

	private final UserAccountService userAccountService;

	public UserAccountController(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	@PostMapping
	public ResponseEntity<UserAccountDto> createUser(@RequestBody CreateUserRequest req) {
		return ResponseEntity.ok(userAccountService.createUser(req));
	}

	@GetMapping
	public ResponseEntity<List<UserAccountDto>> listUsers() {
		return ResponseEntity.ok(userAccountService.listUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserAccountDto> getUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userAccountService.getUser(id));
	}

	@PatchMapping("/{id}/enabled")
	public ResponseEntity<Void> setUserEnabled(@PathVariable("id") Long id, @RequestParam boolean enabled) {
		userAccountService.enableUser(id, enabled);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/assign-role")
	public ResponseEntity<UserRoleDto> assignRole(@RequestBody AssignUserRoleRequest req) {
		return ResponseEntity.ok(userAccountService.assignUserRole(req));
	}

	@GetMapping("/{userId}/roles")
	public ResponseEntity<List<UserRoleDto>> listUserRoles(@PathVariable("userId") Long userId) {
		return ResponseEntity.ok(userAccountService.listRolesForUser(userId));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserAccountDto> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserRequest req) {
		return ResponseEntity.ok(userAccountService.updateUser(id, req));
	}

	@PostMapping("/{id}/reset-password")
	public ResponseEntity<Void> resetPassword(@PathVariable("id") Long id, @RequestBody ResetPasswordRequest req) {
		userAccountService.resetPassword(id, req);
		return ResponseEntity.noContent().build();
	}

}
