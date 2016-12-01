package main;

import edu.stanford.nlp.time.SUTime;
import parser.TimeParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maulik on 01/12/16.
 */
public class Main {

    private static String queryString = "Three interesting dates are 18 Feb 1997, the 20th of july and 4 days from today";

    public static void main (String [] args) {

        // setup the time parser
        TimeParser time = new TimeParser();
        TimeParser.setup();

        // get today's date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        List<SUTime.Temporal> temporals = time.annotateText(queryString, dateFormat.format(today));

        List<String> times = new ArrayList<String>();

        for(int i=0;i<temporals.size();i++) {
            String temporalTime = temporals.get(i).toString();

            String [] tokens = temporalTime.split("-");

            // parse the current string and get the day and month from it
            int month = Integer.parseInt(tokens[1]);
            int date = Integer.parseInt(tokens[2]);

            Client client = ClientBuilder.newClient();

            // Query from muffin labs link
            WebTarget target = client.target("http://history.muffinlabs.com/date/"+month+"/"+date);
            System.out.println("http://history.muffinlabs.com/date/"+month+"/"+date);

//          If I use this target, I get the response, while, if i try above link, the response is not returned
//            target = client.target(
//                    "http://api.openweathermap.org/data/2.5/forecast/daily").queryParam("cnt", "10")
//                    .queryParam("mode", "json")
//                    .queryParam("units", "metric")
//                    .queryParam("appid", "GET-YOUR-API-KEY-FROM:http://openweathermap.org/api")
//            ;

            Response r = target.request(MediaType.APPLICATION_JSON).get();

            System.out.println(r.getLength());
        }
    }
}
