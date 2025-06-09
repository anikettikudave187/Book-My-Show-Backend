package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "halls")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    int capacity;
    @ManyToOne
    Theater theater;
}
