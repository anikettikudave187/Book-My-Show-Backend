package com.bms.central_api_v1.RequestBody;

import com.bms.central_api_v1.models.AppUser;
import com.bms.central_api_v1.models.Theater;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AcceptTheaterRB {
    Theater theater;
    AppUser admin;

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public AppUser getAdmin() {
        return admin;
    }

    public void setAdmin(AppUser admin) {
        this.admin = admin;
    }
}
