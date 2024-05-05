package com.web.ecommerce.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.ecommerce.jwt.JwtResponse;
import com.web.ecommerce.order.Order;
import com.web.ecommerce.product.Product;

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
    public ResponseEntity<?> signup(@RequestBody @Valid UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDTO));
        } catch (MessagingException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send verification email");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.login(userDTO));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @GetMapping("/home/verifyemail")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return userService.verifyEmail(token);
    }


    @PostMapping("/me/profile-picture")
    public ResponseEntity<String> saveCurrentUserProfilePicture(@RequestBody @NotNull JsonNode user) {
        String email = user.get("email").asText();
        String image = user.get("image").asText();

        userService.saveProfileImage(email, image);
        return ResponseEntity.ok("image added");

    }

    @PostMapping("/refresh-token")
    public String postMethodName(@RequestBody JsonNode token) {
        String refreshToken = token.get("refreshToken").asText();
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("/star")
    public ResponseEntity<String> addStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String product_id = jsonNode.get(" product_id").asText();

        return userService.addStar(user_id, product_id);
    }

    @DeleteMapping("/star")
    public ResponseEntity<String> removeStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String product_id = jsonNode.get(" product_id").asText();

        return userService.removeStar(user_id, product_id);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String product_id = jsonNode.get(" product_id").asText();

        return userService.addCart(user_id, product_id);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String product_id = jsonNode.get(" product_id").asText();

        return userService.removeCart(user_id, product_id);
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

    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam String product_id) {
        return userService.getProduct(product_id);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody UserDTO user,@RequestParam String user_id) {
        return userService.updateProfile(user, user_id);
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String oldPassword = jsonNode.get("old_password").asText();
        String newPassword = jsonNode.get("new_password").asText();

        return userService.updatePassword(user_id, oldPassword, newPassword);
    }
}
