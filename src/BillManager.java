import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillManager {


    private List<Bill> bills = new ArrayList<>();
    private Integer nextId = 1;

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getBills() {
        return bills;
    }

    public BigDecimal calculateTotalBills() {
        BigDecimal total = BigDecimal.ZERO;
        for (Bill bill : bills) {
            total = total.add(bill.getAmount());
        }
        return total;
    }

    public BigDecimal calculateTotalPaid(){
        BigDecimal total = BigDecimal.ZERO;
        for (Bill bill : bills) {
            total = total.add(bill.getPaidAmount());

        }
        return total;
    }

    public Bill createBill(String name, BigDecimal amount, LocalDate date) {
        return new Bill(
                name,
                nextId++,
                amount,
                BigDecimal.ZERO,
                date,
                false
        );
    }


    public void updateBill(Bill bill) {
        for (Bill b : bills) {
            if (Objects.equals(b.getId(), bill.getId())) {
                b.setId(bill.getId());
                b.setAmount(bill.getAmount());
                b.setPaidAmount(bill.getPaidAmount());
                b.setDate(bill.getDate());
                b.setPaid(bill.isPaid());
            }
        }

    }

    public boolean deleteBill(int id){
        return bills.removeIf(bill -> bill.getId() == id);
    }


}