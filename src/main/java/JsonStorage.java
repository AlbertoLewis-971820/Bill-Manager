import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

public class JsonStorage {

    private final ObjectMapper objectMapper;

    public JsonStorage(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    //Saves the AppData object to a JSON file named "bill-tracker.json"
    public void saveAppData(AppData appData) throws Exception{
        save(appData, "bill-tracker.json");
    }

    //Saves the data to a JSON file with the given file name
    public void save(Object data, String fileName) throws Exception{
        objectMapper.writerWithDefaultPrettyPrinter()
        .writeValue(new File(fileName), data);
    }
    //Saves the BillManager data to a JSON file named "bill-tracker.json"
    public void saveBillManager(BillManager billManager) throws Exception{
        save(billManager, "bill-tracker.json");
    }
    //Loads the data from a JSON file with the given file name and returns it as an object of the specified type
    public <T> T load(String fileName, Class<T> valueType) throws Exception{
        return objectMapper.readValue(new File(fileName), valueType);
    }

    public AppData loadAppData() throws Exception{
        return load("bill-tracker.json", AppData.class);
    }



}
