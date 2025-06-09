package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "shows")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne
    Movies movies;

    @ManyToOne
    Hall hall;

    Long startTime;
    Long endTime;
}
