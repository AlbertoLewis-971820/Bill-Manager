import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillManager {

    private List<Bill> bills = new ArrayList<>();
    private int nextId = 1;

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getBills() {
        return bills;
    }

    public Bill createBill(String name, BigDecimal amount, LocalDate date) {

        Bill bill = new Bill(
                name,
                nextId++,
                amount,
                date,
                false
        );

        return bill;
    }
}