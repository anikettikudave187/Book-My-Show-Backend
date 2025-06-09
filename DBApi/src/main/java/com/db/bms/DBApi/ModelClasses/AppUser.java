package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID userId;
    String name;
    @Column(unique = true)
    String Email;
    String password;
    @Column(unique = true)
    Long phoneNo;
    int pincode;
    String address;
    String state;
    String userType;
}
