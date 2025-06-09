package com.db.bms.DBApi.Repository;

import com.db.bms.DBApi.ModelClasses.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, UUID> {
    @Query(value = "select * from user where userType='SYSTEM_ADMIN' ",nativeQuery = true)
    public List<AppUser> getAllAdmins();
}
