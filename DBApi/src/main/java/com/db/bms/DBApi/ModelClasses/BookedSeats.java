package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookedSeats")
@ToString
@Getter
@Setter
public class BookedSeats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    UUID showId;
    int seatNo;
}
