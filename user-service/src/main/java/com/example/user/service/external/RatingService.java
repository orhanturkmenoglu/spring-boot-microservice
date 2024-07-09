package com.example.user.service.external;

import com.example.user.service.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// @FeignClient(name = "RATING-SERVICE") : ARAYACAĞIMIZ MİKRO SERVİS HIZMETİNİN ADINI VERİYORUZ.
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/ratings/all")
    List<Rating> getRatingAll();

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingByUserId(@PathVariable String userId);

}
