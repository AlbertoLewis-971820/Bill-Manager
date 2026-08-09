import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillManager {


    private List<Bill> bills = new ArrayList<>();
    private int nextId = 1;

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getBills() {
        return bills;
    }

    public BigDecimal calculateAllBills() {
        BigDecimal total = BigDecimal.ZERO;
        for (Bill bill : bills) {
            total = total.add(bill.getAmount());
        }
        return total;
    }

    public void updateBill(Bill bill) {
        for (Bill b : bills) {
            if (Objects.equals(b.getName(), bill.getName())) {
                b.setAmount(bill.getAmount());
                b.setDate(bill.getDate());
                b.setPaid(bill.isPaid());
            }
        }

    }

    public Bill createBill(String name, BigDecimal amount, LocalDate date) {

        return new Bill(
                name,
                nextId++,
                amount,
                date,
                false
        );
    }
}