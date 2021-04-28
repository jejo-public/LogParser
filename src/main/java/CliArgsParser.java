import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CliArgsParser {

  public static final String CLI_ARGS_SEPARATOR = "=";
  String[] cliArgs;

  public CliArgsParser(final String[] args) {
    this.cliArgs = args;
  }

  public String getFilterSystem() {
    for (final String cliArgument : cliArgs) {
      final String searchString = DataReader.LOG_FIELD_SYSTEM + CLI_ARGS_SEPARATOR;
      final Pattern searchPattern = Pattern.compile("^" + searchString);
      final Matcher matcher = searchPattern.matcher(cliArgument);
      if (matcher.find()) {
        return cliArgument.substring(searchString.length());
      }
    }
    return null;
  }

  public Set<String> getFilterComponents() {
    final Set<String> components = new HashSet<>();
    for (final String cliArgument : cliArgs) {
      final String searchString = DataReader.LOG_FIELD_COMPONENT + CLI_ARGS_SEPARATOR;
      final Pattern searchPattern = Pattern.compile("^" + searchString);
      final Matcher matcher = searchPattern.matcher(cliArgument);
      if (matcher.find()) {
        components.add(cliArgument.substring(searchString.length()));
      }
    }
    return components;
  }
}
