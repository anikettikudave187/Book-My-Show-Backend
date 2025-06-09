package com.bms.central_api_v1.responseBody;

import com.bms.central_api_v1.models.Show;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowByHallResponseBody {
    List<Show> shows;

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}

