import java.math.BigDecimal;
import java.time.LocalDate;

public class JsonStorageTest {

    public static void main(String[] args) {

        JsonStorage storage = new JsonStorage();

        try {
            AppData appData = storage.loadAppData();

            System.out.println("Data loaded successfully!");

            System.out.println(
                    "Bills: " + appData.getBillManager().getBills().size()
            );

            System.out.println(
                    "Next ID: " + appData.getBillManager().getNextId()
            );

            System.out.println(
                    "Paycheck: $" +
                            appData.getPayCheck().getPayCheckAmount()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}