package com.example.hotel.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping
    public ResponseEntity<List<String>>  getStaffs(){
        List<String> result = Arrays.asList("RAM", "SHYAM", "SITA", "KRISHNA");

        return ResponseEntity.ok(result);
    }
}
