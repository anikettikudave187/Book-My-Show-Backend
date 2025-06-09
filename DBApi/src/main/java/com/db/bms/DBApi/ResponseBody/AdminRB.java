package com.db.bms.DBApi.ResponseBody;

import com.db.bms.DBApi.ModelClasses.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRB {
    List<AppUser> admins;

    public List<AppUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AppUser> admins) {
        this.admins = admins;
    }
}
