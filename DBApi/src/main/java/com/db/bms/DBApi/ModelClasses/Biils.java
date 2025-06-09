package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bills")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Biils {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Shows show;
    @ManyToOne
    AppUser user;
    String seats;
    int totalTickets;
    int totalAmount;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
