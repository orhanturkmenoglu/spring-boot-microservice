package com.example.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private String ratingId;

    private String userId;

    private String hotelId;

    private int rating;

    private String feedback;

    // rating derecelendirme sisteminden hotel bilgilerni de alıyor olacağız.
    private Hotel hotel;
}