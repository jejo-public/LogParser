import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataWriter implements iDataWriter {

  public static final String FILE_PATH_PARTS_SEPARATOR = "-";
  public static final String FILE_PATH_COMPONENT_SEPARATOR = "_";
  public static final String FILE_PATH_FILETYPE = ".txt";
  public static final String FILE_PATH_TIMESTAMP_FORMAT = "HH-mm-SS";
  Set<String> components;
  String system;

  public DataWriter(final String system, final Set<String> components) {
    this.system = system;
    this.components = components;
  }

  @Override
  public void writeData(final List<Map<String, String>> dataToWrite) {
    final List<String> dataForFile = transformDataForFile(dataToWrite);
    writeDataToFile(dataForFile);
  }

  private void writeDataToFile(final List<String> dataForFile) {
    final Path path = Paths.get(generateFilePath());
    try {
      Files.write(Paths.get(generateFilePath()), dataForFile, StandardCharsets.UTF_8);
    } catch (final IOException e) {
      System.out.println("Error writing file with filePath: " + path);
    }
  }

  private List<String> transformDataForFile(final List<Map<String, String>> dataToWrite) {
    return dataToWrite.stream().map(entry -> String.join(DataReader.LOG_SEPARATOR, entry.values()))
        .collect(Collectors.toList());
  }

  private String generateFilePath() {
    return DataReader.FILE_PATH
        + system
        + FILE_PATH_PARTS_SEPARATOR
        + String.join(FILE_PATH_COMPONENT_SEPARATOR, components)
        + FILE_PATH_PARTS_SEPARATOR
        + getTimeStamp()
        + FILE_PATH_FILETYPE;
  }

  public String getTimeStamp() {
    return ZonedDateTime
        .now(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(FILE_PATH_TIMESTAMP_FORMAT));
  }
}
