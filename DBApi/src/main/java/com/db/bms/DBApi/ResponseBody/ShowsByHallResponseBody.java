package com.db.bms.DBApi.ResponseBody;

import com.db.bms.DBApi.ModelClasses.Shows;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowsByHallResponseBody {
    List<Shows> shows;

    public List<Shows> getShows() {
        return shows;
    }

    public void setShows(List<Shows> shows) {
        this.shows = shows;
    }
}
