package unip.aps.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Consumer;

/**
 * JSONUtility is a utility class that provides methods for reading and writing JSON data.
 * It uses Google's Gson library to parse and generate JSON.
 *
 * @param <T> the type of objects that this utility will work with
 */
public class JSONUtility<T> {

    // The path of the JSON file
    private final Path path;

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
        this.createFileIfNotExists();
        this.type = type;
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
     *
     * @throws RuntimeException if an I/O error occurs
     */
    private void createFileIfNotExists() {
        if (Files.notExists(this.path)) {
            try {
                Files.createFile(this.path);
            } catch (IOException e) {
                throw new RuntimeException("Error creating file " + this.path.getFileName() + "\n" + e);
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
            String jsonString = reader.lines().collect(Collectors.joining());
            if (jsonString.isEmpty()) {
                return null;
            }
            JSONArray jsonArray = JSON.parseArray(jsonString);
            return jsonArray.toJavaList(this.type);
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

        try (var writer = Files.newOutputStream(this.path)) {
            JSON.writeTo(writer, listOfObjects, JSONWriter.Feature.PrettyFormat);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }


    public void updateJSON(T object, int index) {
        List<T> listOfObjects = parseJSON();
        listOfObjects.set(index,object);

        try (var writer = Files.newOutputStream(this.path)) {
            JSON.writeTo(writer, listOfObjects, JSONWriter.Feature.PrettyFormat);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e);
        }
    }
}
