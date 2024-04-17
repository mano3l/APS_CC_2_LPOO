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
 * JSONUtility is a utility class that provides methods for reading and writing JSON data.
 * It uses Google's Gson library to parse and generate JSON.
 *
 * @param <T> the type of objects that this utility will work with
 */
public class JSONUtility<T> {

    // The path of the JSON file
    private final Path path;

    // Gson instance for JSON operations
    private final Gson gson;

    // The class of the objects that this utility will work with
    private final Class<T> type;

    /**
     * Constructs a new JSONUtility instance.
     * If the file at the specified path does not exist, it will be created.
     *
     * @param path the path of the JSON file
     * @param type the class of the objects that this utility will work with
     */
    public JSONUtility(Path path, Class<T> type) {
        this.path = path;
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        createFileIfNotExists();
    }

    /**
     * Constructs a new JSONUtility instance.
     * If the file at the specified path does not exist, it will be created.
     *
     * @param path the path of the JSON file as a string
     * @param type the class of the objects that this utility will work with
     */
    public JSONUtility(String path, Class<T> type) {
        this(Paths.get(path), type);
    }

    /**
     * Creates the JSON file if it does not exist.
     * @throws RuntimeException if an I/O error occurs
     */
    private void createFileIfNotExists() {
        if (Files.notExists(this.path)) {
            try {
                Files.createFile(this.path);
            } catch (IOException e) {
                throw new RuntimeException("Error creating file: " + e);
            }
        }
    }

    /**
     * Parses the JSON file into a list of objects of the specified type.
     * If the file is empty or the JSON is invalid, an empty list will be returned.
     *
     * @return a list of objects of type T
     * @throws RuntimeException if an I/O error occurs
     */
    public List<T> parseJSON() {
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
     *
     * @param object the object to append
     * @throws RuntimeException if an I/O error occurs
     */
    public void appendToJSON(T object) {
        List<T> listOfObjects = parseJSON();
        listOfObjects.add(object);

        try (var writer = Files.newBufferedWriter(this.path)) {
            this.gson.toJson(listOfObjects, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }

    /**
     * Overwrites the JSON file with a specified list of objects.
     *
     * @param jsonArray the new array of objects
     * @throws RuntimeException if an I/O error occurs
     */
    public void overwriteJSON(List<T> jsonArray) {
        try (var writer = Files.newBufferedWriter(this.path)) {
            this.gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }
}
