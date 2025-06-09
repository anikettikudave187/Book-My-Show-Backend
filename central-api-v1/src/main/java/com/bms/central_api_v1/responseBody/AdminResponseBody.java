package com.bms.central_api_v1.responseBody;

import com.bms.central_api_v1.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseBody {
    List<AppUser> admins;

    public List<AppUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AppUser> admins) {
        this.admins = admins;
    }
}
