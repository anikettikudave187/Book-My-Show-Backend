package com.db.bms.DBApi.Repository;

import com.db.bms.DBApi.ModelClasses.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepo extends JpaRepository<Shows, UUID> {
    @Query(value = "select * from shows where hall_id=:hallId",nativeQuery = true)
    public List<Shows> getShowsByHallId(UUID hallId);
}
