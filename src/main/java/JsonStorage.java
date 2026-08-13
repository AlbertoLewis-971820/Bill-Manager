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

    public void save(Object data, String fileName) throws Exception{
        objectMapper.writerWithDefaultPrettyPrinter()
        .writeValue(new File(fileName), data);
    }

    public void saveBillManager(BillManager billManager) throws Exception{
        save(billManager, "bill-tracker.json");
    }



}
