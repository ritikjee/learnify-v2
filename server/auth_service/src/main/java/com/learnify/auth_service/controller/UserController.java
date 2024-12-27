package com.learnify.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnify.auth_service.dto.ErrorResponseDTO;
import com.learnify.auth_service.dto.ResponseDTO;
import com.learnify.auth_service.dto.UserDTO;
import com.learnify.auth_service.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        try {
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), "User not found"));
            }

            UserDTO userDTO = new UserDTO();

            userDTO.setUsername(userDetails.getEmail());
            userDTO.setFirstname(userDetails.getFirstname());
            userDTO.setLastname(userDetails.getLastname());
            userDTO.setId(userDetails.getId());
            userDTO.setImage(userDetails.getImage());

            return ResponseEntity.ok(new ResponseDTO<UserDTO>(HttpStatus.OK, userDTO));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}