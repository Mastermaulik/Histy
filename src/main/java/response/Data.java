package response;

import lombok.*;

import java.util.List;

/**
 * Created by maulik on 01/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Data {
    @Getter
    @Setter
    private String year;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private String html;

    @Getter
    @Setter
    private List<Link> links;

}
