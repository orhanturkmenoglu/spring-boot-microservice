package com.example.hotel.service.service;

import com.example.hotel.service.exception.ResourceNotFoundException;
import com.example.hotel.service.model.Hotel;
import com.example.hotel.service.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public Hotel createHotel(Hotel hotel) {
        String randomHotelId = UUID.randomUUID().toString();
        hotel.setId(randomHotelId);
        return hotelRepository.save(hotel);
    }

    // get all
    public List<Hotel> getHotelAll() {
        return hotelRepository.findAll();
    }


    // get single hotel

    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() ->
                new ResourceNotFoundException("hotel with given id not found :" + hotelId));
    }


}
