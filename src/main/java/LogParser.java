import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogParser {


  public static void main(final String[] args) {
    final CliArgsParser cliArgsParser = new CliArgsParser(args);

    final String system = cliArgsParser.getFilterSystem();
    final Set<String> components = cliArgsParser.getFilterComponents();

    final List<Map<String, String>> loadedDataSets = new DataReader().readData();
    final List<Map<String, String>> filteredDataSets = new DataParser(system, components)
        .filter(loadedDataSets);
    final DataWriter dataWriter = new DataWriter(system, components);
    dataWriter.writeData(filteredDataSets);

    System.out.println(filteredDataSets);
  }
}
