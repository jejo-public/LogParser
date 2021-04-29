import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataReader implements iSource {

  public static final String FILE_PATH = "C:/Arbeitsaufgabe/";
  public static final String FILENAME = "Quelldaten.txt";

  public static final String LOG_SEPARATOR = ";";
  public static final String LOG_BOOLEAN_STRING_REPRESENTATION_TRUE = "TRUE";
  public static final String LOG_BOOLEAN_STRING_REPRESENTATION_FALSE = "FALSE";

  public static final String LOG_FIELD_SYSTEM = "SYSTEM";
  public static final String LOG_FIELD_COMPONENT = "COMPONENT";
  public static final String LOG_FIELD_IS_EXPIRED = "IS_EXPIRED";
  public static final String LOG_FIELD_LOGIN_NAME = "LOGIN_NAME";
  public static final String LOG_FIELD_LOGIN_PASSWORD = "LOGIN_PASSWORD";

  public static final List<String> ACCEPTED_LOG_FIELDS = List
      .of(LOG_FIELD_SYSTEM, LOG_FIELD_COMPONENT, LOG_FIELD_IS_EXPIRED, LOG_FIELD_LOGIN_NAME,
          LOG_FIELD_LOGIN_PASSWORD);

  public DataReader() {

  }

  @Override
  public List<Map<String, String>> readData() {
    try {
      final List<Map<String, String>> validDataSets = new ArrayList<>();
      final List<String> inValidDataSets = new ArrayList<>();

      final int countDismissed = 0;
      try (final BufferedReader inputFromFile = new BufferedReader(
          new FileReader(FILE_PATH + FILENAME))) {
        skipDescriptionLine(inputFromFile);

        String line;
        while ((line = inputFromFile.readLine()) != null) {
          final String[] fieldsInDataSet = extractFields(line);
          if (fieldsInDataSet.length == ACCEPTED_LOG_FIELDS.size()) {
            final Map<String, String> dataSet = createDataSet(fieldsInDataSet);
            if (isValidDataSet(dataSet)) {
              validDataSets.add(createDataSet(fieldsInDataSet));
            }
          } else {
            inValidDataSets.add(line);
          }
        }
      }

      printInfoMessages(validDataSets, inValidDataSets);
      return validDataSets;
    } catch (final IOException e) {
      printIoExceptionMessage(e.getMessage());
    }
    return null;
  }

  private boolean isValidDataSet(final Map<String, String> dataSet) {
    return dataSet.get(LOG_FIELD_IS_EXPIRED).equals(LOG_BOOLEAN_STRING_REPRESENTATION_FALSE)
        || dataSet.get(LOG_FIELD_IS_EXPIRED).equals(LOG_BOOLEAN_STRING_REPRESENTATION_TRUE);
  }

  private void printInfoMessages(final List<Map<String, String>> validDataSets,
      final List<String> inValidDataSets) {
    System.out.println();
    printValidCountMessage(validDataSets.size());
    printInvalidCountErrorMessage(inValidDataSets.size());
    System.out.println();
    System.out.println("dismissed datasets: ");
    inValidDataSets.forEach(System.out::println);
  }

  private String[] extractFields(final String line) {
    return line.split(LOG_SEPARATOR);
  }

  private void printIoExceptionMessage(final String exceptionMessage) {
    System.out.println("Error reading file with filePath: " + FILE_PATH);
    System.out.println("Error message : " + exceptionMessage);
  }

  private void printValidCountMessage(final int countDismissed) {
    System.out.println(countDismissed + " dataset(s) could be read and are further processed.");
  }

  private void printInvalidCountErrorMessage(final int countDismissed) {
    System.out.println(countDismissed + " dataset(s) couldnt be read and were dismissed.");
  }

  private Map<String, String> createDataSet(final String[] fieldsInDataSet) {
    final Map<String, String> dataSet = new LinkedHashMap<>();
    ACCEPTED_LOG_FIELDS.forEach(
        logField -> dataSet.put(logField, fieldsInDataSet[ACCEPTED_LOG_FIELDS.indexOf(logField)]));
    return dataSet;
  }

  private void skipDescriptionLine(final BufferedReader br) throws IOException {
    br.readLine();
  }
}

