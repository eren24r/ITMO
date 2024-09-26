package org.web;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import java.io.InputStream;
import java.util.Scanner;

public class GameRecommendationSystem {

    private static final String ONTOLOGY_PATH = "game.owl";  // Путь к вашей онтологии

    public static void main(String[] args) {
        // Загрузка онтологии
        Model model = loadOntology(ONTOLOGY_PATH);

        // Ввод данных от пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваш возраст: ");
        Integer age = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        System.out.println("Введите ваши любимые жанры (например, RPG, Indie, Shooter): ");
        String genres = scanner.nextLine();

        // Построение SPARQL-запроса
        String sparqlQuery = buildSparqlQuery(age, genres);

        // Выполнение запроса
        executeSparqlQuery(model, sparqlQuery);
    }

    // Загрузка онтологии
    private static Model loadOntology(String filePath) {
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filePath);
        if (in == null) {
            throw new IllegalArgumentException("Файл не найден: " + filePath);
        }
        model.read(in, null);
        return model;
    }

    // Построение SPARQL-запроса на основе данных пользователя
    private static String buildSparqlQuery(int age, String genres) {
        String[] genreArray = genres.split(",\\s*");
        StringBuilder genreFilter = new StringBuilder();
        for (String genre : genreArray) {
            genreFilter.append("?game ex:hasGenre ex:").append(genre).append(" .\n");
        }

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ex: <http://example.com/game#>\n" +
                "SELECT ?game ?gameName\n" +
                "WHERE {\n" +
                "  ?game rdf:type ex:Game .\n" +
                "  ?game ex:hasAgeRestriction ?ageRestriction .\n" +
                genreFilter.toString() +
                "  ?game rdfs:label ?gameName .\n" +
                "  FILTER (?ageRestriction <= " + age + ")\n" +
                "}";
    }


    // Выполнение SPARQL-запроса
    private static void executeSparqlQuery(Model model, String sparqlQuery) {
        Query query = QueryFactory.create(sparqlQuery);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            if (!results.hasNext()) {
                System.out.println("Рекомендаций по вашему запросу не найдено.");
                return;
            }
            System.out.println("Рекомендованные игры:");
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String gameName = soln.getLiteral("gameName").getString();
                System.out.println("- " + gameName);
            }
        }
    }
}

