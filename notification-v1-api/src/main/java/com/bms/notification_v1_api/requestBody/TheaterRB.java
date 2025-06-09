package com.bms.notification_v1_api.requestBody;

import com.bms.notification_v1_api.models.AppUser;
import com.bms.notification_v1_api.models.Theater;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TheaterRB {
    AppUser admin;
    Theater theater;
    String token;

    public AppUser getAdmin() {
        return admin;
    }

    public void setAdmin(AppUser admin) {
        this.admin = admin;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
