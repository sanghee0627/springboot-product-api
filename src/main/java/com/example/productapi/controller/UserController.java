package com.example.productapi.controller;

import com.example.productapi.dto.UserDto;
import com.example.productapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * 사용자(User) 관련 API를 처리하는 컨트롤러
 * <p>
 * 이 컨트롤러는 사용자의 생성, 조회, 수정, 삭제 등 사용자와 관련된 모든 작업을 담당합니다.
 * 사용자 목록 조회 및 이메일 중복 확인 기능을 제공합니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * 모든 사용자 목록 조회
     */
    @Operation(summary = "모든 사용자 조회", description = "DB에 저장된 전체 사용자 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 새로운 사용자 생성
     */
    @Operation(summary = "사용자 생성", description = "새로운 사용자를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "사용자 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "생성할 사용자 정보", required = true)
            @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * ID로 사용자 조회
     */
    @Operation(summary = "사용자 조회 (ID)", description = "ID를 기준으로 특정 사용자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "조회할 사용자 ID", example = "1")
            @PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 이메일로 사용자 조회
     */
    @Operation(summary = "사용자 조회 (이메일)", description = "이메일을 기준으로 특정 사용자를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @Parameter(description = "조회할 사용자 이메일", example = "user@example.com")
            @PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * 이메일 중복 확인
     */
    @Operation(summary = "이메일 중복 확인", description = "해당 이메일이 이미 등록되어 있는지 확인합니다.")
    @ApiResponse(responseCode = "200", description = "중복 여부 반환")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "중복 확인할 이메일", example = "user@example.com")
            @RequestParam String email) {
        boolean exists = userService.isEmailExists(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * 사용자 정보 수정
     */
    @Operation(summary = "사용자 수정", description = "ID를 기준으로 사용자의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "수정할 사용자 ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "수정할 사용자 정보", required = true)
            @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * 사용자 삭제
     */
    @Operation(summary = "사용자 삭제", description = "ID를 기준으로 특정 사용자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "삭제할 사용자 ID", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
