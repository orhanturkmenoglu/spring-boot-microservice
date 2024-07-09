package com.example.rating.service.service;

import com.example.rating.service.model.Rating;
import com.example.rating.service.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

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
