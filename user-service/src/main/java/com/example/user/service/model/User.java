package com.example.user.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "micro_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String about;

    // other user properties that you want

    @Transient // veritabanı tablomuzda olmadığı için görzmeden gel dedik.
            /*
            Özelliğin veya alanın kalıcı olmadığını belirtir. Bir varlık sınıfının,
             eşlenen üst sınıfın veya gömülebilir sınıfın bir özelliğine veya alanına açıklama eklemek için kullanılır.
             */
    List<Rating> ratings = new ArrayList<>();// derecelendirme

}
