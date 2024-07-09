package com.example.rating.service.service;

import com.example.rating.service.model.Rating;
import com.example.rating.service.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;

    private final RestTemplate restTemplate;

    // create

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // get all rating

    public List<Rating> getRatingAll() {
        return ratingRepository.findAll();
    }

    // get all by userıd

    public List<Rating> getRatingUserById(String userId) {
        return ratingRepository.findByUserId(userId);
    }


    // get all by hotel
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

}
