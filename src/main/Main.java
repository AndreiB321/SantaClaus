package main;

import checker.Checker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.Constants;
import dataset.Database;
import input.InputLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) {
        Database database = Database.getDatabase();

        try {
            File testDirectory = new File(Constants.INPUT_DIR);
            Path outputDirectory = Paths.get(Constants.OUTPUT_DIR);

            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }

            for (File file : Objects.requireNonNull(testDirectory.listFiles())) {
                // Exctract digits
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(file.getName());
                String testFile = Constants.OUTPUT_PATH;
                while (m.find()) {
                    testFile +=  m.group();
                    testFile +=  Constants.FILE_EXTENSION;
                }
                // create output file
                FileWriter outputFile = new FileWriter(testFile);
                InputLoader inputLoader = new InputLoader(Constants.INPUT_DIR + file.getName());
                ObjectMapper objectMapper = new ObjectMapper();
                // read and process data
                inputLoader.readData();
                JSONArray arrayResult = database.solution();
                JSONObject jsonObject = new JSONObject();
                // add to final array
                jsonObject.put(Constants.ANNUAL_CHILDREN, arrayResult);
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                outputFile.write(objectMapper.writeValueAsString(jsonObject));
                // close file
                outputFile.flush();
                outputFile.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Checker.calculateScore();
        }
    }
}
