package com.example.user.service.service;

import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.model.Hotel;
import com.example.user.service.model.Rating;
import com.example.user.service.model.User;
import com.example.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    // user operations

    private final UserRepository userRepository;

    //Bu kütüphane, mikro hizmetlerin birbirleriyle iletişim kurmasını sağlar ve JSON, XML veya diğer medya tipleri üzerinde işlem yapabilir.
    private final RestTemplate restTemplate;

    //create

    public User saveUser(User user) {
        // generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    // get all user

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    // get single user of given userId

    public User getUserById(String userId) {
        // get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id si not found on server !! :" + userId));

        /*
         fetch rating of the above user from rating service
         rating micro hizmet sistemi ile iletişim kurmam gerekiyor.
         rating service içerisinde arayacağı url  : http://localhost:8082/ratings/users/{userId}
         */
        // rating servisi ile iletişime geçme
        String url = "http://localhost:8082/ratings/users/" + user.getUserId();
        /*
        ResponseEntity ve exchange() Metodu Kullanma:
        JSON dönüşümü için exchange() metodunu kullanarak ResponseEntity'yi doğrudan işleyebilirsiniz. Örneğin:
         */
        ResponseEntity<List<Rating>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                });

        log.info("{}", response);

        List<Rating> ratingsOfUser = response.getBody();

        // gelen modeli gez ve hotel bilgilerni hotel serviceden  al.
        ratingsOfUser.stream().map(rating -> {

            // TEK BİR OBJEYE İHTİYACIM OLDUĞU İÇİN ENTİTY METHODUNU KULLANIYORUM
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8081/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();// hotel bilgilerni dönder.
            log.info("response status code : {}", forEntity.getStatusCode());

            rating.setHotel(hotel);

            return rating;
        }).collect(Collectors.toList());


        // rating serviceden gelen değeri user servisine aktardık.
        user.setRatings(ratingsOfUser);

        // rating içerisinden hotel bilgilerni de alacağız.

        return user;

    }


}
