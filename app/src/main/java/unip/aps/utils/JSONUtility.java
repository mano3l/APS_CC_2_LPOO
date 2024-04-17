package unip.aps.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * JSONUtility is a utility class for handling JSON files.
 * It provides methods to parse JSON files into Java objects, append objects to JSON files, and write a list of objects to JSON files.
 */
public class JSONUtility {

    // The path of the JSON file
    private final Path path;

    private final Gson gson;

    /**
     * Constructs a new JSONUtility with the specified path.
     * If the file at the specified path does not exist, it will be created.
     *
     * @param path the path of the JSON file
     * @throws RuntimeException if an I/O error occurs
     */
    public JSONUtility(String path) {
        this.path = Paths.get(path);
        if (Files.notExists(this.path)) {
            try {
                Files.createFile(this.path);
            } catch (IOException e) {
                throw new RuntimeException("Error creating file: " + e);
            }
        }
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Parses the JSON file into a list of objects of the specified type.
     * If the file is empty or the JSON is invalid, an empty list will be returned.
     *
     * @param type the class of the objects
     * @return a list of objects of the specified type
     * @throws RuntimeException if an I/O error occurs
     */
    public <T> List<T> parseJSON(Class<T> type) {
        try (var reader = Files.newBufferedReader(this.path)) {
            List<T> listOfObjects =
                    this.gson.fromJson(reader, TypeToken.getParameterized(List.class, type).getType());

            return listOfObjects == null ? new ArrayList<>() : listOfObjects;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e);
        }
    }

    /**
     * Appends the specified object to the JSON file.
     * The object will be added to the end of the existing list of objects in the file.
     *
     * @param object the object to append
     * @param type   the class of the object
     * @throws RuntimeException if an I/O error occurs
     */
    public <T> void appendToJSON(T object, Class<T> type) {
        List<T> listOfObjects = parseJSON(type);
        listOfObjects.add(object);

        try (var writer = Files.newBufferedWriter(this.path)) {;
            this.gson.toJson(listOfObjects, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }

    /**
     * Writes the specified list of objects to the JSON file.
     * The existing contents of the file will be replaced with the new list.
     *
     * @param jsonArray the list of objects to write
     * @throws RuntimeException if an I/O error occurs
     */
    public <T> void overwriteJSON(List<T> jsonArray) {
        try (var writer = Files.newBufferedWriter(this.path)) {
            this.gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }
}

