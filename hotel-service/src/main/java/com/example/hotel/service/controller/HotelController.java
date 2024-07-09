package com.example.hotel.service.controller;

import com.example.hotel.service.model.Hotel;
import com.example.hotel.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // create hotel
    @PostMapping
    public ResponseEntity<Hotel> createUser(@RequestBody Hotel hotel) {
        Hotel saveHotel = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveHotel);
    }

    // getall
    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getHotelAll() {
        List<Hotel> getHotelAll = hotelService.getHotelAll();
        return ResponseEntity.ok(getHotelAll);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        Hotel hotelById = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelById);
    }
}
