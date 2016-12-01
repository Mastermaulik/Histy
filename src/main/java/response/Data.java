package response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by maulik on 01/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    @Getter
    @Setter
    private String year;

    @Getter
    @Setter
    private String html;

    @Getter
    @Setter
    private String text;
}
