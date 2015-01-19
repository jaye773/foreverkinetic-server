import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Library {

    static ArrayList<Exercise> exercises = new ArrayList<>();

    public static void getExercise(Element ar) throws IOException {
        Element el = ar.select("h3 a").first();
        System.out.println(el.text());
        System.out.println(el.attr("href"));
        Document doc = Jsoup
                .connect(el.attr("href"))
                .userAgent("Mozilla")
                .get();
        Exercise exercise = createExercise(doc);
        exercise.print();
        //exercises.add(exercise);
    }

    public static Exercise createExercise(Document doc) {
        Exercise exercise = new Exercise();
        exercise.setTitle(doc.select(".entry-title a").first().text());
        exercise.setDescription(doc.select(".exercise-entry-content p").first().text());
        Elements stepElements = doc.select(".exercise-entry-content ol li");
        for (Element stepElement : stepElements) {
            exercise.addStep(stepElement.text());
        }
        Elements taxonomies = doc.select("ul.exercise-taxonomies li");
        for (int i = 0; i<taxonomies.size();i++) {
            Element element = taxonomies.get(i).select(" strong ").first();
            String type = element.text().toLowerCase();
            if (type.contains("type")) {
                Elements typeElements = taxonomies.get(i).select(" a ");
                for (Element typeElement : typeElements) {
                    exercise.addType(typeElement.text());
                }
            } else if (type.contains("primary")) {
                Elements primaryElements = taxonomies.get(i).select(" a ");
                for (Element primaryElement : primaryElements) {
                    exercise.addPrimaryMuscleGroup(primaryElement.text());
                }
            } else if (type.contains("secondary")) {
                Elements secondaryElements = taxonomies.get(i).select(" a ");
                for (Element secondayElement : secondaryElements) {
                    exercise.addSecondaryMuscleGroup(secondayElement.text());
                }
            } else if (type.contains("equipment")) {
                Elements equipmentElements = taxonomies.get(i).select(" a ");
                for (Element equipmentElement : equipmentElements) {
                    exercise.addEquipment(equipmentElement.text());
                }
            }
        }
        return exercise;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting parsing.");
        for (int i=1;i<=25;i++) {
            Document doc = Jsoup
                    .connect("http://db.everkinetic.com/page/"+i)
                    .userAgent("Mozilla")
                    .get();
            Elements articles = doc.select("article");
            for (Element ar : articles) {
                getExercise(ar);
            }
        }
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("exercises.json"), exercises);
    }

}
