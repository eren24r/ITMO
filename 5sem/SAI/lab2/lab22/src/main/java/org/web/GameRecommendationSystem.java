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

        System.out.println("Platform: ");
        String platform = scanner.nextLine();

        System.out.println("Character: ");
        String character = scanner.nextLine();

        System.out.println("Введите ваши любимые жанры (например, RPG, Indie, Shooter): ");
        String genres = scanner.nextLine();
        String[] genders = genres.split(", ");
        for (String gender : genders) {
            String sparqlQuery = buildSparqlQuery(age, gender, platform, character);

            executeSparqlQuery(model, sparqlQuery);
        }
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

    public static String buildSparqlQuery(Integer age, String genre, String platform, String character) {

        String prefixes =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "PREFIX ex: <http://example.com/game#>\n" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";

        StringBuilder query = new StringBuilder();
        query.append(prefixes);
        query.append("SELECT ?game ?label WHERE {\n");
        query.append("  ?game rdf:type ex:Game .\n");

        if (genre != null && !genre.isEmpty()) {
            query.append("  ?game ex:hasGenre ex:").append(genre).append(" .\n");
        }

        if (platform != null && !platform.isEmpty()) {
            query.append("  ?game ex:hasPlatform ex:").append(platform).append(" .\n");
        }

        if (character != null && !character.isEmpty()) {
            query.append("  ?game ex:hasCharacter ex:").append(character).append(" .\n");
        }

        if (age != null) {
            query.append("  ?game ex:hasAgeRestriction ?age .\n");
            query.append("  FILTER (?age <= ").append(age).append(") .\n");
        }

        query.append("  ?game rdfs:label ?label .\n");
        query.append("}");

        return query.toString();
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
                String gameName = soln.getLiteral("label").getString();
                System.out.println("- " + gameName);
            }
        }
    }
}

