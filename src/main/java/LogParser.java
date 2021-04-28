import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogParser {


  public static void main(final String[] args) {
    for (final String arg : args) {
      System.out.println(arg);
    }

    final List<Map<String, String>> loadedDataSets = new DataReader().readData();

    final String system = "PROD";
    final List<String> components = new ArrayList<>();
    components.add("TEXT");
    components.add("HIPM");

    final List<Map<String, String>> filteredDataSets = new DataParser(system, components)
        .filter(loadedDataSets);

    final DataWriter dataWriter = new DataWriter(system, components);
    dataWriter.writeData(filteredDataSets);

    System.out.println(filteredDataSets);
  }
}
