import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataParser {

  String filterForSystem;
  Set<String> filterForComponents;

  public DataParser(final String filterForSystem, final Set<String> filterForComponents) {
    this.filterForSystem = filterForSystem;
    this.filterForComponents = filterForComponents;
  }

  public List<Map<String, String>> filter(final List<Map<String, String>> dataSets) {
    return dataSets.stream().filter(this::filterCondition).collect(Collectors.toList());
  }

  private boolean filterCondition(final Map<String, String> dataSet) {
    if (Boolean.parseBoolean(dataSet.get(DataReader.LOG_FIELD_IS_EXPIRED))) {
      return false;
    }
    if (!dataSet.get(DataReader.LOG_FIELD_SYSTEM).equals(filterForSystem)) {
      return false;
    }
    return filterForComponents.contains(dataSet.get(DataReader.LOG_FIELD_COMPONENT));
  }
}
