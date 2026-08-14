

import java.math.BigDecimal;

public class PayCheck {

    //Default constructor for Jackson
    public PayCheck() {

    }

    private BigDecimal payCheckAmount;
    public PayCheck(BigDecimal payCheckAmount) {
        this.payCheckAmount = payCheckAmount;
    }

    public BigDecimal getPayCheckAmount() {
        return payCheckAmount;
    }
    public void setPayCheckAmount(BigDecimal payCheckAmount) {
        this.payCheckAmount = payCheckAmount;
    }

}
