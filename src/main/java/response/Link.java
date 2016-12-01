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
public class Link {
    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String link;
}
