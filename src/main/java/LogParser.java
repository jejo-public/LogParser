import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LogParser {


  public static void main(String[] args) {
    List<Map<String, String>>  loadedDataSets =  new DataReader().readData();

    System.out.println(loadedDataSets);
    for(int i = 0; i < args.length; i++) {
      System.out.println(args[i]);
    }
  }

}
