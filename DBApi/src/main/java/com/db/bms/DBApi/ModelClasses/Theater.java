package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="theater")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String address;
    int pincode;
    String state;
    @ManyToOne
    AppUser owner;
    String status;
}
