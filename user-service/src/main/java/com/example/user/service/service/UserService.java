package com.example.user.service.service;

import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.external.HotelService;
import com.example.user.service.external.RatingService;
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

    private final HotelService hotelService;

    private final RatingService ratingService;


    //create

    public User saveUser(User user) {
        // generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    // get all user

    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
       /* String url = "http://RATING-SERVICE/ratings/all";
        ResponseEntity<List<Rating>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                }
        );


        List<Rating> ratingList = response.getBody();*/

        // feing client kullanarak rating servise istekte bulunma.

        List<Rating> ratingList = ratingService.getRatingAll();

        userList.forEach(user -> {
            List<Rating> userRatings = findRatingsForUser(ratingList, user.getUserId());
            user.setRatings(userRatings);
        });

        log.info("ratingList : {}", ratingList.toString());


        return userList;
    }

    // Kullanıcıya ait değerlendirmeleri bulma
    private List<Rating> findRatingsForUser(List<Rating> ratingList, String userId) {
        return ratingList.stream()
                .filter(rating -> rating.getUserId().equals(userId))
                .toList();
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

        List<Rating> ratingsOfUser = getRatings(user);

        // rating serviceden gelen değeri user servisine aktardık.
        user.setRatings(ratingsOfUser);

        // rating içerisinden hotel bilgilerni de alacağız.

        return user;

    }

    private List<Rating> getRatings(User user) {
    /*
    ResponseEntity ve exchange() Metodu Kullanma:
    JSON dönüşümü için exchange() metodunu kullanarak ResponseEntity'yi doğrudan işleyebilirsiniz. Örneğin:
     */
        // String ratingsUsersIdUrl = "http://localhost:8082/ratings/users/" + user.getUserId();

        // SERVİCE REGİSTRY İLE SERVİS KAYDINI YAPMIŞTIK ŞİMDİ BU
        // URL İLERLEYEN ZAMANLARDA DEĞİŞME İHTİMALİ YÜKSEK BUNU DİNAMİK HALE GETİRELİM
        // BUNUDA ZATEN HİZMET KAYDI İLE SERVİSLERİ İSİMLERİNE GÖRE TUTMUŞTUK.
        // BUNU İSİMLERİNE GÖRE KULLANABİLMEK İÇİN @LOADBLANCER AKTİF EDİYORUZ YÜKLERİ DAHA DİNAMİK HALE GETİR.
        /*String ratingsUsersIdUrl = "http://RATING-SERVICE/ratings/users/" + user.getUserId();
        ResponseEntity<List<Rating>> response = restTemplate.exchange(
                ratingsUsersIdUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                });


        List<Rating> ratingsOfUser = response.getBody();

        log.info("{}", ratingsOfUser);*/

        // feing client ile rating service çağrısında bulunuyoruz.

        List<Rating> ratingsOfUser = ratingService.getRatingByUserId(user.getUserId());

        // gelen modeli gez ve hotel bilgilerni hotel serviceden  al.
        ratingsOfUser.stream().map(rating -> {

            // TEK BİR OBJEYE İHTİYACIM OLDUĞU İÇİN ENTİTY METHODUNU kullanadabilirim
          /*  String ratingsHotelIdUrl = "http://HOTEL-SERVICE/hotels/" + rating.getHotelId();
            ResponseEntity<Hotel> forEntity = restTemplate.exchange(
                    ratingsHotelIdUrl,
                    HttpMethod.GET,
                    null,
                    Hotel.class
            );
            Hotel hotel = forEntity.getBody();// hotel bilgilerni dönder.*/

            Hotel hotel = hotelService.getHotel(rating.getHotelId());// hotel bilgilerni dönder.
            // log.info("response status code : {}", forEntity.getStatusCode());

            rating.setHotel(hotel);

            return rating;
        }).collect(Collectors.toList());
        return ratingsOfUser;
    }


}
