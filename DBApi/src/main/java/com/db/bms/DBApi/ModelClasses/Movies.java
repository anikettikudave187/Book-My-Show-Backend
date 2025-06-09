package com.db.bms.DBApi.ModelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;
    double duration;
    int reviews;
    int totalReviewsVotes;
    String language;
    @ManyToOne
    AppUser movieOwner;
    boolean isReleased;
}
