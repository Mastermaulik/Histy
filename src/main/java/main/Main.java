package main;

import edu.stanford.nlp.time.SUTime;
import parser.TimeParser;
import response.Data;
import response.EventsResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by maulik on 01/12/16.
 */
public class Main {

    static List<String> birthSynonyms = Arrays.asList("born","birth","brought to birth");

    static List<String> deathSynonyms = Arrays.asList("die","death","dead","died");

    private static String queryString = "Who died yesterday";

    public static void main (String [] args) {

        // setup the time parser
        TimeParser time = new TimeParser();
        TimeParser.setup();

        // get today's date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        List<SUTime.Temporal> temporals = time.annotateText(queryString, dateFormat.format(today));

        List<String> times = new ArrayList<String>();


        for(int temporalIter=0;temporalIter<temporals.size();temporalIter++) {
            String temporalTime = temporals.get(temporalIter).toString();

            String [] tokens = temporalTime.split("-");

            // parse the current string and get the day and month from it
            int month = Integer.parseInt(tokens[1]);
            int date = Integer.parseInt(tokens[2]);

            Client client = ClientBuilder.newClient();

            // Query from muffin labs link
            WebTarget target = client.target("http://history.muffinlabs.com/date/"+month+"/"+date);

            // get the response and store it in an object
            EventsResponse rs = target.request(MediaType.APPLICATION_JSON).get(EventsResponse.class);

            String mood = getMood(queryString);

            List<Data> latestResults = getLatestFiveEvents(rs,mood);

            System.out.println("Here are the top results for the query that you entered for Date: "+temporalTime);
            for(int resultsIter=0;resultsIter<latestResults.size();resultsIter++) {
                Data d = latestResults.get(resultsIter);
                System.out.print("In Year: "+d.getYear()+", "+d.getText());

                if(mood.equals("Births")) {
                    System.out.print(" was born.\n");
                }
                else if(mood.equals("Deaths")) {
                    System.out.print(" died\n");
                }
                else {
                    System.out.print("\n");
                }
            }
        }
    }

    /*
    Get Mood of the query string based on whether it matches birth or death keywords
     */
    public static String getMood(String queryString) {
        // Parse the query string and store the keywords
        List<String> inputTokens = Arrays.asList(queryString.split(" "));

        if(!Collections.disjoint(inputTokens,birthSynonyms)) {
            return "Births";
        }
        else if(!Collections.disjoint(inputTokens,deathSynonyms)) {
            return "Deaths";
        }
        else  {
            return "Events";
        }
    }

    /*
    get the five latest results from data
     */
    public static List<Data> getLatestFiveEvents(EventsResponse rs,String mood) {
        List<Data> userMoodData = rs.getData().get(mood);

        List<Data> result = new ArrayList<Data>();

        int size = userMoodData.size();
        for(int i=size-1;i>=size-6;i--) {
            result.add(userMoodData.get(i));
        }
        return result;
    }
}
