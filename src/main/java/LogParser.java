import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogParser {


  public static void main(final String[] args) {
    final List<Map<String, String>> loadedDataSets = new DataReader().readData();

    final List<String> components = new ArrayList<>();
    components.add("TEXT");
    components.add("HIPM");

    final List<Map<String, String>> filteredDataSets = new DataParser("PROD", components)
        .filter(loadedDataSets);

    System.out.println(filteredDataSets);
    for (final String arg : args) {
      System.out.println(arg);
    }
  }

}
