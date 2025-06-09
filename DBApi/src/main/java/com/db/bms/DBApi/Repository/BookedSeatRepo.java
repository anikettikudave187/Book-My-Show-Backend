package com.db.bms.DBApi.Repository;

import com.db.bms.DBApi.ModelClasses.BookedSeats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookedSeatRepo extends JpaRepository<BookedSeats, UUID> {
    @Query(value = "select * from bookedseats where seat_number=:seat and show_id=:showId", nativeQuery = true)
    public BookedSeats getBookedSeat(UUID showId,int seat);
}
