import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataReader implements iSource {

  String filePath = "C:/Arbeitsaufgabe/Quelldaten.txt";

  public static final String LOG_FIELD_SYSTEM = "SYSTEM";
  public static final String LOG_FIELD_COMPONENT = "COMPONENT";
  public static final String LOG_FIELD_IS_EXPIRED = "IS_EXPIRED";
  public static final String LOG_FIELD_LOGIN_NAME = "LOGIN_NAME";
  public static final String LOG_FIELD_LOGIN_PASSWORD = "LOGIN_PASSWORD";

  public static final List<String> ACCEPTED_LOG_FIELDS = List
      .of(LOG_FIELD_SYSTEM, LOG_FIELD_COMPONENT, LOG_FIELD_IS_EXPIRED, LOG_FIELD_LOGIN_NAME,
          LOG_FIELD_LOGIN_PASSWORD);

  private static final String LOG_SEPERATOR = ";";

  public DataReader()
  {

  }

  @Override
  public List<Map<String, String>> readData() {
      try {
        List<Map<String, String>> validDataSets = new ArrayList<>();

        int countDismissed = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
          skipDescriptionLine(bufferedReader);

          String line;
          while((line = bufferedReader.readLine()) != null) {
            String [] fieldsInDataSet= extractFields(line);
            if(fieldsInDataSet.length == ACCEPTED_LOG_FIELDS.size())
              {
                validDataSets.add(createDataSet(fieldsInDataSet));
              }
            else {
              countDismissed++;
              }
          }
        }

        printDismissedErrorMessage(countDismissed);
        return validDataSets;
      } catch (IOException e) {
        printIoExceptionMessage();
      }
      return null;
  }

  private String[] extractFields(String line) {
    return line.split(LOG_SEPERATOR);
  }

  private void printIoExceptionMessage() {
    System.out.println("Error reading file with filePath: " + filePath );
  }

  private void printDismissedErrorMessage(int countDismissed) {
    System.out.println(  countDismissed + " data entry(s) couldnt be read and were dismissed.");
  }

  private Map<String,String> createDataSet( String [] fieldsInDataSet) {
    Map<String,String> dataSet = new HashMap<>();
    ACCEPTED_LOG_FIELDS.forEach(
        logField-> dataSet.put(logField,fieldsInDataSet[ACCEPTED_LOG_FIELDS.indexOf(logField)]));
    return dataSet;
  }

  private void skipDescriptionLine(BufferedReader br) throws IOException {
    br.readLine();
  }

}

