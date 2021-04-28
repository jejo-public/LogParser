import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataFilter {

  String filterForSystem;
  Set<String> filterForComponents;

  public DataFilter(final String filterForSystem, final Set<String> filterForComponents) {
    this.filterForSystem = filterForSystem;
    this.filterForComponents = filterForComponents;
  }

  public List<Map<String, String>> filter(final List<Map<String, String>> dataSets) {
    return dataSets.stream().filter(
        dataSet -> filterConditionIsNotExpired(dataSet) && filterConditionMatchMinOneParameter(
            dataSet))
        .collect(Collectors.toList());
  }

  private boolean filterConditionMatchAllParameter(final Map<String, String> dataSet) {
    if (!dataSet.get(DataReader.LOG_FIELD_SYSTEM).equals(filterForSystem)) {
      return false;
    }
    return filterForComponents.contains(dataSet.get(DataReader.LOG_FIELD_COMPONENT));
  }

  private boolean filterConditionMatchMinOneParameter(final Map<String, String> dataSet) {
    if (dataSet.get(DataReader.LOG_FIELD_SYSTEM).equals(filterForSystem)) {
      return true;
    }
    return filterForComponents.contains(dataSet.get(DataReader.LOG_FIELD_COMPONENT));
  }

  private boolean filterConditionIsNotExpired(final Map<String, String> dataSet) {
    return !Boolean.parseBoolean(dataSet.get(DataReader.LOG_FIELD_IS_EXPIRED));
  }
}
