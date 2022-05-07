package com.gameshub.controller;

import com.gameshub.domain.user.UserCloseDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("users")
    public ResponseEntity<List<UserOpenDto>> users() {
        return ResponseEntity.ok(userMapper.mapToUserOpenDtoList(userService.getAllUsers()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin/users")
    public ResponseEntity<List<UserCloseDto>> usersForAdmin() {
        return ResponseEntity.ok(userMapper.mapToUserCloseDtoList(userService.getAllUsers()));
    }
}
