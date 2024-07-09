package com.example.user.service.external;

import com.example.user.service.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @FeignClient(name = "RATING-SERVICE") : ARAYACAĞIMIZ MİKRO SERVİS HIZMETİNİN ADINI VERİYORUZ.
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    // GET
    @GetMapping("/ratings/all")
    List<Rating> getRatingAll();

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingByUserId(@PathVariable String userId);

    // POST
    @PostMapping("/ratings")
    Rating createRating(@RequestBody Rating rating);

    // PUT
    @PutMapping("/ratings/{ratingId}")
    Rating updateRating(@PathVariable String ratingId,Rating rating);

    // DELETE

    @DeleteMapping("/ratings/{ratingId}")
    void deleteRating(@PathVariable String ratingId);
}
