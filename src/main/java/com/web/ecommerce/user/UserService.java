package com.web.ecommerce.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.ecommerce.email.EmailService;
import com.web.ecommerce.jwt.JwtResponse;
import com.web.ecommerce.jwt.JwtService;
import com.web.ecommerce.order.Order;
import com.web.ecommerce.order.OrderRepository;
import com.web.ecommerce.product.ProductRepository;
import com.web.ecommerce.product.Product;
import com.web.ecommerce.review.Review;
import com.web.ecommerce.review.ReviewRepository;

import jakarta.mail.MessagingException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public UserService(UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            JwtService jwtService, UserDetailsServiceImpl userDetailsService, OrderRepository orderRepository,
            EmailService emailService, ProductRepository productRepository,
            ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public String register(@NonNull UserDTO userDTO) throws MessagingException, InterruptedException {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        User user = new User(userDTO);
        String verificationToken = jwtService.generateToken(user);

        user.setEmailVerified(false);
        user.setVerificationToken(verificationToken);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        String subject = "Verify Your Email";

        // if we use render site then use this
        String body = "Click the link to verify your email:https://ecommerce-j0hf.onrender.com/home/verifyemail?token="
                + verificationToken;

        // if we use localhost then use this
        // String body = "Click the link to verify your
        // email:http://localhost:8080/home/verifyemail?token="
        // + verificationToken;
        emailService.sendEmail(savedUser.getEmail(), subject, body);

        return "the user added successfully go to your email to verify your email";
    }

    public JwtResponse login(@NonNull UserDTO userDTO) {
        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null && bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user), user);
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    public User findUserByEmail(String email) {
        if (userRepository.findByEmail(email) == null) {
            throw new IllegalArgumentException("User not found");
        }
        return userRepository.findByEmail(email);
    }

    public void saveProfileImage(String email, String image) {
        User user = findUserByEmail(email);
        user.setImage(image);
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername());
    }

    public String getCurrentUserProfilePicture() {
        User currentUser = getCurrentUser();
        return currentUser.getImage();
    }

    public String getCurrentUserName() {
        User currentUser = getCurrentUser();
        return currentUser.getName();
    }

    public String getCurrentUserEmail() {
        User currentUser = getCurrentUser();
        return currentUser.getEmail();
    }

    public String refreshToken(String refreshToken) {
        String email = jwtService.extractUserName(refreshToken);
        if (email == null) {
            throw new RuntimeException("Invalid Token");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (!jwtService.validateToken(refreshToken, userDetails)) {
            throw new RuntimeException("expired Token or Invalid");
        }

        return jwtService.generateToken(userDetails);
    }

    public ResponseEntity<String> addStar(String userId, String productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getStar() == null) {
                user.setStar(new ArrayList<>());
            }

            List<String> stars = new ArrayList<>(user.getStar());

            if (stars.contains(productId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product already in stars");
            }

            stars.add(productId);
            user.setStar(stars);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("product added to stars successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error addingproduct to stars");
        }
    }

    public ResponseEntity<String> removeStar(String userId, String productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getStar() == null || user.getStar().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Noproducts in stars to remove");
            }

            List<String> stars = new ArrayList<>(user.getStar());

            if (!stars.contains(productId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product not found in stars");
            }

            stars.remove(productId);
            user.setStar(stars);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("product removed from stars successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removingproduct from stars");
        }
    }

    public ResponseEntity<String> addCart(String userId, String productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getCart() == null) {
                user.setCart(new ArrayList<>());
            }

            List<String> carts = new ArrayList<>(user.getCart());

            if (carts.contains(productId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product already in the cart");
            }

            carts.add(productId);
            user.setCart(carts);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("product added to the cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error addingproduct to the cart");
        }
    }

    public ResponseEntity<String> removeCart(String userId, String productId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getCart() == null || user.getCart().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Noproducts in the cart to remove");
            }

            List<String> carts = new ArrayList<>(user.getCart());

            if (!carts.contains(productId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("product not found in the cart");
            }

            carts.remove(productId);
            user.setCart(carts);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("product removed from the cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removingproduct from the cart");
        }
    }

    public ResponseEntity<String> addOrder(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);

            User user = userRepository.findById(order.getUser_id()).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getOrder_id() == null) {
                user.setOrder_id(null);
            }

            List<String> orders = new ArrayList<>(user.getOrder_id());

            if (orders.contains(savedOrder.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order already associated with the user");
            }

            orders.add(savedOrder.getId());
            user.setOrder_id(orders);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Order added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding order");
        }
    }

    public ResponseEntity<String> removeOrder(Order order) {
        try {
            orderRepository.deleteById(order.getId());

            User user = userRepository.findById(order.getUser_id()).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getOrder_id() == null || user.getOrder_id().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No orders to remove");
            }

            List<String> orders = new ArrayList<>(user.getOrder_id());

            if (!orders.contains(order.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not found for the user");
            }

            orders.remove(order.getId());
            user.setOrder_id(orders);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Order removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing order");
        }
    }

    public ResponseEntity<List<Order>> getOrders(String userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            List<String> orderIds = user.getOrder_id();

            if (orderIds == null || orderIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }

            List<Order> orders = new ArrayList<>();

            for (String id : orderIds) {
                orderRepository.findById(id).ifPresent(orders::add);
            }

            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public User get_user(String t, String email, String pass) {
        if (userRepository.findByEmail(email) == null) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = userRepository.findByEmail(email);
        user.setToken(t);

        return user;
    }

    public ResponseEntity<Product> getProduct(String product_id) {
        if (product_id == null) {
            throw new IllegalArgumentException("Product id cannot be empty");
        }

        Optional<Product> product = productRepository.findById(product_id);

        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Product p = product.get();
        Double averageRating = calculateAverageRating(p.getId());

        p.setRating(averageRating);

        List<Review> reviews = reviewRepository.findByProductId(product_id);
        // add reviews to product
        ArrayList<Review> reviewss = new ArrayList<Review>();
        for (Review review : reviews) {
            reviewss.add(review);
        }
        product.get().setReviews(reviewss);

        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    public double calculateAverageRating(String productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public ResponseEntity<String> verifyEmail(String token) {
        String email = jwtService.extractUserName(token);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user1 = user;
        if (user1.getVerificationToken().equals(token)) {
            user1.setEmailVerified(true);
            user1.setVerificationToken(null);
            userRepository.save(user1);

            return ResponseEntity.ok("Email verified successfully");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
        }
    }

    public ResponseEntity<String> updateProfile(UserDTO user, String user_id) {

        User user1 = userRepository.findById(user_id).orElse(null);

        if (user1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user1.setName(user.getName());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        userRepository.save(user1);

        return ResponseEntity.ok("Profile updated successfully");
    }

    public ResponseEntity<String> updatePassword(String user_id, String oldPassword, String newPassword) {
        User user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password updated successfully");
    }

    public ResponseEntity<Object> getCart(String user_id) {
        User user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> productIds = user.getCart();

        if (productIds == null || productIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
        }

        List<Product> products = new ArrayList<>();

        for (String id : productIds) {
            productRepository.findById(id).ifPresent(products::add);
        }

        int subTotal = 0;
        int shipping = 0;
        for (Product product : products) {
            subTotal += product.getPrice();
            shipping = Math.max(shipping, product.getDelivery().get(0).getPrice());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CartResponse(products, subTotal, shipping));
    }

    public ResponseEntity<List<Product>> getStar(String user_id) {
        User user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<String> productIds = user.getStar();

        if (productIds == null || productIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
        }

        List<Product> products = new ArrayList<>();

        for (String id : productIds) {
            productRepository.findById(id).ifPresent(products::add);
        }

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    public ResponseEntity<String> addAddress(String NewAddress, String user_id) {
        User user = userRepository.findById(user_id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (user.getAddress() == null) {
            user.setAddress(new ArrayList<>());
        }

        user.getAddress().add(NewAddress);

        userRepository.save(user);

        return ResponseEntity.ok("Address added successfully");

    }
}
