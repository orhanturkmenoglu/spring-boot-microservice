package com.example.user.service.controller;

import com.example.user.service.model.Rating;
import com.example.user.service.model.User;
import com.example.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    // create

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    // single user get
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUserAll() {
        List<User> getUserAll = userService.getAllUser();
        return ResponseEntity.ok(getUserAll);
    }

    int count = 1;
    // all user get
    @GetMapping("/{userId}")
    // BURADA KULLANICI DERECELENDİRME SİSTEMİNİ ARAYARAK KULLANICI DERELENDİRME BİLGİLERİNİ GETİRECEĞİZ.
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")  // devre kesici uygulamasını çalıştırıyoruz istediğiniz isimi verebilirsiniz.
    //@Retry(name = "ratingHotelService",fallbackMethod ="ratingHotelFallback")  // tekrar deneme mekanizması.
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback") // rate limiter (oran sınırlayıcı) kullanarak belirli bir süre içinde belirli sayıda istek kabul edebilirsiniz
    public ResponseEntity<User> getUserById(@PathVariable String userId) {

        log.info("Retry Count : {}",count);
        count++;
        User getUserById = userService.getUserById(userId);
        return ResponseEntity.ok(getUserById);
    }

    // creating fall back method for circuit breaker :Geri dönüş methodu.
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception exception){
        log.info("fallback is executed because servise is down :"+exception.getMessage());
        User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This is user is created dummy because some service is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    // create ratings
    @PostMapping("/ratings")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating saveRating = userService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveRating);
    }

    // delete rating
    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> deleteRating(@PathVariable String ratingId) {
        userService.deleteRating(ratingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update rating
    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratingId, @RequestBody Rating rating) {
        Rating updatedRating = userService.updateRating(ratingId, rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedRating);
    }


}
