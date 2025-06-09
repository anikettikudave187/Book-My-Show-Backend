package com.db.bms.DBApi.Repository;

import com.db.bms.DBApi.ModelClasses.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TheaterRepo extends JpaRepository<Theater, UUID> {

}
