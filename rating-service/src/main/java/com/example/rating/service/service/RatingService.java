package com.example.rating.service.service;

import com.example.rating.service.model.Rating;
import com.example.rating.service.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    // update
    public Rating updateRating(String ratingId, Rating rating) {
        Optional<Rating> findRatingById = ratingRepository.findById(ratingId);

        if (findRatingById.isEmpty()) {
            throw new NullPointerException("rating is null");
        }

        return ratingRepository.save(rating);
    }

    // delete

    public void deleteRatingById(String ratingId) {
        Optional<Rating> findRatingById = ratingRepository.findById(ratingId);
        findRatingById.ifPresent(rating -> ratingRepository.deleteById(rating.getRatingId()));
    }

}
