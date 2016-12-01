package response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by maulik on 01/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponse {
    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private String url;

    @Setter
    @Getter
    private Map<String,List<Data>> data;
}


