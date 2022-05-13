package com.gameshub.controller;

import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping(value = "users")
    public ResponseEntity<List<UserOpenDto>> users() {
        return ResponseEntity.ok(userMapper.mapToUserOpenDtoList(userService.getAllUsers()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "admin/users")
    public ResponseEntity<List<UserCloseDto>> usersForAdmin() {
        return ResponseEntity.ok(userMapper.mapToUserCloseDtoList(userService.getAllUsers()));
    }
}
