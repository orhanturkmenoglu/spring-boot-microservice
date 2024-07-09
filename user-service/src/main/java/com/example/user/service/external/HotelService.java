package com.example.user.service.external;

import com.example.user.service.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "HOTEL-SERVICE") : ARAYACAĞIMIZ MİKRO SERVİS HIZMETİNİN ADINI VERİYORUZ.
@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable String hotelId);
}
