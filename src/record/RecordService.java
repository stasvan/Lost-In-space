package record;

import IO.FileSystemService;
import utils.ResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordService {


    private FileSystemService fileSystemService;

    public RecordService() {
        this.fileSystemService = new FileSystemService();
    }

    public ArrayList<Record> findAllRecords() {
        ArrayList<Record> records = new ArrayList<>();

        String RECORDS_FILENAME = "scores.txt";
        List<String> strings = fileSystemService.readLines(ResourceLoader.resolve(RECORDS_FILENAME));
        for (String s : strings) {
            Record record = new Record(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
            records.add(record);
        }


        System.out.println(records);
        return records;

    }
}
