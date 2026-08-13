
import java.math.BigDecimal;
import java.time.LocalDate;

public class Bill {

    private String name;
    private int id = 1;
    private BigDecimal amount;



    private BigDecimal paidAmount;
    private LocalDate date;
    private boolean paid;

    public Bill(String name, int id, BigDecimal amount, BigDecimal paidAmount, LocalDate date, boolean paid) {
        this.name = name;
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.paid = paid;
        this.paidAmount = paidAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public boolean isPaid() {

        return paid;
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
