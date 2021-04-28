import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataReader implements iSource {

  public static final String FILE_PATH = "C:/Arbeitsaufgabe/";
  public static final String LOG_SEPARATOR = ";";

  public static final String LOG_FIELD_SYSTEM = "SYSTEM";
  public static final String LOG_FIELD_COMPONENT = "COMPONENT";
  public static final String LOG_FIELD_IS_EXPIRED = "IS_EXPIRED";
  public static final String LOG_FIELD_LOGIN_NAME = "LOGIN_NAME";
  public static final String LOG_FIELD_LOGIN_PASSWORD = "LOGIN_PASSWORD";

  public static final List<String> ACCEPTED_LOG_FIELDS = List
      .of(LOG_FIELD_SYSTEM, LOG_FIELD_COMPONENT, LOG_FIELD_IS_EXPIRED, LOG_FIELD_LOGIN_NAME,
          LOG_FIELD_LOGIN_PASSWORD);
  public String fileName = "Quelldaten.txt";

  public DataReader() {

  }

  @Override
  public List<Map<String, String>> readData() {
    try {
      final List<Map<String, String>> validDataSets = new ArrayList<>();

      int countDismissed = 0;
      try (final BufferedReader bufferedReader = new BufferedReader(
          new FileReader(FILE_PATH + fileName))) {
        skipDescriptionLine(bufferedReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
          final String[] fieldsInDataSet = extractFields(line);
          if (fieldsInDataSet.length == ACCEPTED_LOG_FIELDS.size()) {
            validDataSets.add(createDataSet(fieldsInDataSet));
          } else {
            countDismissed++;
          }
        }
      }

      printDismissedErrorMessage(countDismissed);
      return validDataSets;
    } catch (final IOException e) {
      printIoExceptionMessage();
    }
    return null;
  }

  private String[] extractFields(final String line) {
    return line.split(LOG_SEPARATOR);
  }

  private void printIoExceptionMessage() {
    System.out.println("Error reading file with filePath: " + FILE_PATH);
  }

  private void printDismissedErrorMessage(final int countDismissed) {
    System.out.println(countDismissed + " data entry(s) couldnt be read and were dismissed.");
  }

  private Map<String, String> createDataSet(final String[] fieldsInDataSet) {
    final Map<String, String> dataSet = new HashMap<>();
    ACCEPTED_LOG_FIELDS.forEach(
        logField -> dataSet.put(logField, fieldsInDataSet[ACCEPTED_LOG_FIELDS.indexOf(logField)]));
    return dataSet;
  }

  private void skipDescriptionLine(final BufferedReader br) throws IOException {
    br.readLine();
  }
}

