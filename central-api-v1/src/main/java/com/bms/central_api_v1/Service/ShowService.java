package com.bms.central_api_v1.Service;

import com.bms.central_api_v1.RequestBody.CreateShowRB;
import com.bms.central_api_v1.RequestBody.PurchaseTicketRB;
import com.bms.central_api_v1.exceptions.AlreadyBookedSeatException;
import com.bms.central_api_v1.exceptions.InvalidShowException;
import com.bms.central_api_v1.exceptions.UnAuthorizedException;
import com.bms.central_api_v1.integrations.DBApi;
import com.bms.central_api_v1.models.*;
import com.bms.central_api_v1.responseBody.BillResponseBody;
import com.bms.central_api_v1.responseBody.ShowByHallResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    @Autowired
    HallService hallService;

    @Autowired
    UserService userService;

    @Autowired
    MovieService movieService;

    @Autowired
    DBApi dbApi;

    public Show findShowById(UUID showId){
        return dbApi.callFindShowByIdEndpoint(showId);
    }

    public List<Show> getAllShowsByHallId(UUID hallId){
        ShowByHallResponseBody showByHallResponseBody= dbApi.getShowsByHallId(hallId);
        return showByHallResponseBody.getShows();
    }

    public LocalDateTime getTimeInLocalDateAndTime(Long timeInMillis){
        LocalDateTime referenceTime=LocalDateTime.of(2014,1,1,00,00);
        Duration duration=Duration.ofMillis(timeInMillis);
        referenceTime.plus(duration);
        return referenceTime;
    }

    public boolean isOverLapping(List<Show> shows,Show show2){
        for(int i=0;i<shows.size();i++){
            Show show1=shows.get(i);
            if(show1.getEndTime()>=show2.getStartTime() && show1.getStartTime()<=show2.getEndTime()){
                return true;
            }
        }
        return false;
    }

    public Show createShow(CreateShowRB showRB, UUID ownerId){
        Hall hall=hallService.findHallById(showRB.getHallId());
        Movie movie=movieService.findMovieById(showRB.getMovieId());

        if(!hall.getTheater().getOwner().getUserId().equals(ownerId)){
            throw new UnAuthorizedException(String.format("user doesn't own hall"));
        }

        LocalDateTime startTime=showRB.getStartTime();
        LocalDateTime endTime=showRB.getEndTime();
        LocalDateTime referenceTime=LocalDateTime.of(2014,1,1,00,00);

        Long startTimeInMillis= Duration.between(referenceTime,startTime).toMillis();
        Long endTimeInMillis=Duration.between(referenceTime,endTime).toMillis();

        Show show=new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setEndTime(endTimeInMillis);
        show.setStartTime(startTimeInMillis);
        show.setPrice(showRB.getPrice());

        List<Show> shows=this.getAllShowsByHallId(hall.getId());
        boolean isShowOverLapping=isOverLapping(shows,show);
        if(isShowOverLapping){
            throw new InvalidShowException("show is overlapping");
        }
        return dbApi.callCreateShowEndpoint(show);
    }

    public List<Integer> getAvailableSeatsInShow(UUID showId){
        Show show=this.findShowById(showId);
        int totalSeats=show.getHall().getCapacity();
        List<Integer> notBookedSeat=new ArrayList<>();
        for(int seat=1;seat<=totalSeats;seat++){
            BookedSeats bookedSeats=dbApi.callGetBookedSeatsEndpoint(showId,seat);
            if(bookedSeats==null){
                notBookedSeat.add(seat);
            }
        }
        return notBookedSeat;
    }

    public BillResponseBody purchaseTicketeForShow(UUID userId, UUID showId, PurchaseTicketRB ticketRB){
        AppUser user=userService.getUserById(userId);
        Show show=this.findShowById(showId);
        List<Integer> seats=ticketRB.getSeats();
        Double totalPrice=0.0;
        for(int seat:seats){
            BookedSeats bookedSeats=dbApi.callGetBookedSeatsEndpoint(showId,seat);
            if(bookedSeats==null){
                BookedSeats newBookedSeat=new BookedSeats();
                newBookedSeat.setShowId(showId);
                newBookedSeat.setSeatNo(seat);

                dbApi.callCreateBookedSeatEndpoint(newBookedSeat);
                totalPrice+=show.getPrice();
            }else{
                throw new AlreadyBookedSeatException("seat is already booked");
            }
        }

        BillResponseBody billResponseBody=new BillResponseBody();
        LocalDateTime startTime=this.getTimeInLocalDateAndTime(show.getStartTime());
        LocalDateTime endTime=this.getTimeInLocalDateAndTime(show.getEndTime());

        billResponseBody.setEndTime(endTime);
        billResponseBody.setStartTime(startTime);
        billResponseBody.setSeats(ticketRB.getSeats());
        billResponseBody.setMovieName(show.getMovie().getName());
        billResponseBody.setUserName(user.getName());
        billResponseBody.setTotalAmount(totalPrice);

        return billResponseBody;
    }


}
