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
    public ResponseEntity<?> signup(@RequestBody @Valid @NotNull UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDTO));
        } catch (MessagingException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send verification email");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody @Valid @NotNull UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.login(userDTO));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/home/verifyemail")
    public ResponseEntity<String> verifyEmail(@RequestParam @NotNull String token) {
        return userService.verifyEmail(token);
    }

    @GetMapping("/refresh-token")
    public String postMethodName(@RequestParam @NotNull String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("/star")
    public ResponseEntity<String> addStar(@RequestBody @Valid @NotNull CartAndStarDTO cartAndStarDTO) {
        return userService.addStar(cartAndStarDTO.getUser_id(), cartAndStarDTO.getProduct_id());
    }

    @GetMapping("/star")
    public ResponseEntity<List<Product>> getStar(@RequestParam  @NotNull String user_id) {
        return userService.getStar(user_id);
    }

    @DeleteMapping("/star")
    public ResponseEntity<String> removeStar(@RequestBody @Valid @NotNull CartAndStarDTO cartAndStarDTO) {

        return userService.removeStar(cartAndStarDTO.getUser_id(), cartAndStarDTO.getProduct_id());
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@RequestBody @Valid @NotNull CartAndStarDTO cartAndStarDTO) {
        return userService.addCart(cartAndStarDTO.getUser_id(), cartAndStarDTO.getProduct_id());
    }

    @GetMapping("/cart")
    public ResponseEntity<List<Product>> getCart(@RequestParam @NotNull String user_id) {
        return userService.getCart(user_id);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCart(@RequestBody @Valid @NotNull CartAndStarDTO cartAndStarDTO) {
        return userService.removeCart(cartAndStarDTO.getUser_id(), cartAndStarDTO.getProduct_id());
    }

    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody @Valid @NotNull Order order) {
        return userService.addOrder(order);
    }

    @DeleteMapping("/order")
    public ResponseEntity<String> removeOrder(@RequestBody @Valid @NotNull Order order) {
        return userService.removeOrder(order);

    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestParam  @NotNull String user_id) {
        return userService.getOrders(user_id);
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam @NotNull String product_id) {
        return userService.getProduct(product_id);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody @NotNull UserDTO user, @RequestParam String user_id) {
        return userService.updateProfile(user, user_id);
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody @NotNull PasswordDTO passwordDTO) {

        return userService.updatePassword(passwordDTO.getUserId(), passwordDTO.getOldPassword(),
                passwordDTO.getNewPassword());
    }
}
