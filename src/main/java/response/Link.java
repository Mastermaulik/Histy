package response;

import lombok.*;

/**
 * Created by maulik on 01/12/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Link {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String link;
}
