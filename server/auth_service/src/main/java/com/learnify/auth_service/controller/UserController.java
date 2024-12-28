package com.learnify.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnify.auth_service.dto.ErrorResponseDTO;
import com.learnify.auth_service.dto.ResponseDTO;
import com.learnify.auth_service.dto.UserDTO;
import com.learnify.auth_service.entity.User;
import com.learnify.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthService userService;

    public UserController(AuthService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        System.out.println("====================================================================");
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), "User not found"));
            }

            System.out.println("UserDetails: " + userDetails);

            User user = userService.getUserByEmail(userDetails.getUsername());

            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstname(user.getFirstname());
            userDTO.setLastname(user.getLastname());
            userDTO.setImage(user.getImage());

            System.out.println("============================2================================");

            return ResponseEntity.ok(new ResponseDTO<UserDTO>(HttpStatus.OK, userDTO));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}