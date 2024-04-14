package com.web.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.ecommerce.dto.JwtResponse;
import com.web.ecommerce.dto.UserDTO;
import com.web.ecommerce.model.Order;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid @NotNull UserDTO userDTO)
            throws MessagingException, InterruptedException {
        String user = userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Todo : Invalid endpoint, authenticated endpoints shouldn't be redirected to
    // gh login page
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@RequestBody @NotNull UserDTO userDTO) {
        JwtResponse jwtResponse = userService.login(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/me/profile-picture")
    public ResponseEntity<String> saveCurrentUserProfilePicture(@RequestBody @NotNull JsonNode user) {
        String email = user.get("email").asText();
        String image = user.get("image").asText();

        userService.saveProfileImage(email, image);
        return ResponseEntity.ok("image added");

    }

    @GetMapping("/me/profile-picture")
    public ResponseEntity<String> getCurrentUserProfilePicture() {
        String profilePicture = userService.getCurrentUserProfilePicture();
        return ResponseEntity.ok(profilePicture);
    }

    @GetMapping("/me/name")
    public ResponseEntity<String> getCurrentUserName() {
        String userName = userService.getCurrentUserName();
        return ResponseEntity.ok(userName);
    }

    @GetMapping("/me/email")
    public ResponseEntity<String> getCurrentUserEmail() {
        String email = userService.getCurrentUserEmail();
        return ResponseEntity.ok(email);
    }

    @PostMapping("/refresh-token")
    public String postMethodName(@RequestBody JsonNode token) {
        String refreshToken = token.get("refreshToken").asText();
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("/star")
    public ResponseEntity<String> addStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String  product_id = jsonNode.get(" product_id").asText();

        return userService.addStar(user_id,  product_id);
    }

    @DeleteMapping("/star")
    public ResponseEntity<String> removeStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String  product_id = jsonNode.get(" product_id").asText();

        return userService.removeStar(user_id,  product_id);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String  product_id = jsonNode.get(" product_id").asText();

        return userService.addCart(user_id,  product_id);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String  product_id = jsonNode.get(" product_id").asText();

        return userService.removeCart(user_id,  product_id);
    }

    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        return userService.addOrder(order);
    }

    @DeleteMapping("/order")
    public ResponseEntity<String> removeOrder(@RequestBody Order order) {
        return userService.removeOrder(order);

    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestParam String user_id) {
        return userService.getOrders(user_id);
    }

}
