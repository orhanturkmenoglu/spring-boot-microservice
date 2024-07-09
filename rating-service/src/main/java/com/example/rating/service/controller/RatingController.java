package com.example.rating.service.controller;

import com.example.rating.service.model.Rating;
import com.example.rating.service.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // create rating

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating saveRating = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveRating);
    }

    // get all
    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getRatings() {
        List<Rating> ratingAll = ratingService.getRatingAll();
        return ResponseEntity.ok(ratingAll);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratingAll = ratingService.getRatingUserById(userId);
        return ResponseEntity.ok(ratingAll);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> ratingAll = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.ok(ratingAll);
    }

}
