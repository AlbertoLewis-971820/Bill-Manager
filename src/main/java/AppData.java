public class AppData{
    //This class is used to store the BillManager and PayCheck objects for the application state
    private BillManager billManager;
    private PayCheck payCheck;

    public BillManager getBillManager() {
        return billManager;
    }

    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    public PayCheck getPayCheck() {
        return payCheck;
    }

    public void setPayCheck(PayCheck payCheck) {
        this.payCheck = payCheck;
    }
}