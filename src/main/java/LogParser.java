import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogParser {

  public static void main(final String[] args) {
    final CliArgsParser cliArgsParser = new CliArgsParser(args);

    final String systemFilter = cliArgsParser.getSystemFilter();
    final Set<String> componentsFilters = cliArgsParser.geComponentFilters();

    final List<Map<String, String>> loadedDataSets = new DataReader().readData();
    final List<Map<String, String>> filteredDataSets = new DataFilter(systemFilter,
        componentsFilters)
        .filter(loadedDataSets);
    new DataWriter(systemFilter, componentsFilters).writeData(filteredDataSets);
  }
}
