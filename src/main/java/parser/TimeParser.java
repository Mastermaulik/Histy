package parser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.time.SUTime;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class TimeParser {

    private static AnnotationPipeline pipeline;

    private static String rulesFiles = "src/main/resources";

    public static void setup() {
        try {
            // import the rule files for Holidays and details about parsing time
            String defs_sutime = rulesFiles + "/defs.sutime.txt";
            String holiday_sutime = rulesFiles + "/english.holidays.sutime.txt";
            String _sutime = rulesFiles + "/english.sutime.txt";


            pipeline = new AnnotationPipeline();
            Properties props = new Properties();


            String sutimeRules = defs_sutime + "," + holiday_sutime
                    + "," + _sutime;

            /*
            Set properties for Properties
            Example: http://nlp.stanford.edu/software/sutime.html
             */
            props.setProperty("sutime.rules", sutimeRules);
            props.setProperty("sutime.binders", "0");
            props.setProperty("sutime.markTimeRanges", "true");
            props.setProperty("sutime.includeRange", "true");


            pipeline.addAnnotator(new TokenizerAnnotator(false));
            pipeline.addAnnotator(new TimeAnnotator("sutime", props));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SUTime.Temporal> annotateText(String text, String referenceDate) {
        List<SUTime.Temporal> times = new ArrayList<SUTime.Temporal>();
        try {
            if (pipeline != null) {

                Annotation annotation = new Annotation(text);
                annotation.set(CoreAnnotations.DocDateAnnotation.class, referenceDate);
                pipeline.annotate(annotation);

                List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);

                for (CoreMap cm : timexAnnsAll) {
                    List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
                    System.out.println(cm + " [from char offset " +
                            tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
                            " to " + tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) + ']' +
                            " --> " + cm.get(TimeExpression.Annotation.class).getTemporal());

                    times.add(cm.get(TimeExpression.Annotation.class).getTemporal());
                }

            } else {
                System.out.println("Annotation Pipeline object is NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
}