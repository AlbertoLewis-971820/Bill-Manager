import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static PayCheck payCheck = null;
    static BillManager billManager = new BillManager();
    static JsonStorage jsonStorage = new JsonStorage();
    public static void main(String[] args) {

        loadData();
        Scanner sc = new Scanner(System.in);

        boolean running = true;

            while (running) {


                displayMenu();

                String choice = sc.nextLine();
                //Check if the choice is a valid choice

                if(choice.equals("1")) {

                    try {
                        Bill bill = getBillFromUser(sc);
                        billManager.addBill(bill);
                        saveData();
                        System.out.println("Bill added successfully!");
                    }catch(DateTimeParseException e){
                        System.out.println("Invalid date format please enter as DD/MM/YYYY");
                    }

                } else if (choice.equals("2")) {
                    displayBills();

                }else if(choice.equals("3")) {
                    System.out.println("Enter bill ID you want to update: ");
                    int id = sc.nextInt();

                    for (Bill bill : billManager.getBills()) {

                        if (bill.getId() == id) {

                            BigDecimal remaining = bill.getAmount()
                                    .subtract(bill.getPaidAmount());

                            System.out.println("Remaining balance: $" + remaining);
                            System.out.println("Enter amount to be paid: ");

                            if (sc.hasNextBigDecimal()) {

                                BigDecimal amount = sc.nextBigDecimal();
                                sc.nextLine();

                                if (amount.compareTo(BigDecimal.ZERO) <= 0) {

                                    System.out.println(
                                            "Amount to be paid must be greater than zero!"
                                    );

                                } else if (amount.compareTo(remaining) > 0) {

                                    System.out.println(
                                            "Payment cannot be greater than the remaining balance!"
                                    );

                                } else {

                                    bill.setPaidAmount(
                                            bill.getPaidAmount().add(amount)
                                    );

                                    if (bill.getPaidAmount().compareTo(bill.getAmount()) >= 0) {
                                        bill.setPaid(true);
                                    } else {
                                        bill.setPaid(false);
                                    }

                                    bill.setDate(LocalDate.now());
                                    saveData();

                                    System.out.println("Payment applied successfully!");
                                }

                            } else {

                                System.out.println(
                                        "Invalid input, please enter a valid amount!"
                                );

                                sc.nextLine();
                            }
                        }

                        billManager.updateBill(bill);
                    }


                } else if (choice.equals("4")) {
                    payCheck = addPaycheck(sc);
                    saveData();

                } else if (choice.equals("5")) {
                    displayBills();
                    System.out.println("Enter bill ID to delete:");
                    if(sc.hasNextInt()) {
                        int id = sc.nextInt();
                        sc.nextLine();
                        if(billManager.deleteBill(id)) {
                            saveData();
                            System.out.println("Bill deleted successfully!");
                        }else {
                            System.out.println("Invalid input please enter a valid id!");
                        }
                    }

                }else if (choice.equals("6")) {
                    System.out.println("Goodbye!");
                    running = false;
                }else {
                    System.out.println("Invalid choice!");
                    sc.nextLine();
                }
            }

    }

    private static PayCheck addPaycheck(Scanner sc){

        System.out.println("How much is your paycheck?");
        BigDecimal payCheckAmount = sc.nextBigDecimal();
        if(payCheckAmount.compareTo(BigDecimal.ZERO)<=0){
            System.out.println("Pay check amount can not be negative!");
        }else{
            sc.nextLine();
            System.out.println("Paycheck Amount of: $" + payCheckAmount + " added!");
        }
        return new PayCheck(payCheckAmount);

    }



    private static Bill getBillFromUser(Scanner sc) {


        System.out.println("Please enter bill name");
        String name = sc.nextLine();
        System.out.println("Please enter bill amount");
        BigDecimal amount = sc.nextBigDecimal();
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            System.out.println("Bill amount cannot be negative!");
        }
        sc.nextLine();

        System.out.println("Please enter bill date: MM/DD/YYYY");
        LocalDate date = LocalDate.parse(sc.nextLine(), formatter);

        return billManager.createBill(name, amount, date);
    }

    public static void displayMenu(){
        System.out.println();
        System.out.println("==================================================");
        System.out.println("                  BILL TRACKER");
        System.out.println("==================================================");

        System.out.println("1. Add Bill");
        System.out.println("2. View Bills");
        System.out.println("3. Update Bill");
        System.out.println("4. Add / Update Paycheck");
        System.out.println("5. Delete Bill");
        System.out.println("6. Exit");

        System.out.println("--------------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private static void loadData() {
        try {
            AppData appData = jsonStorage.loadAppData();
            billManager = appData.getBillManager();
            payCheck = appData.getPayCheck();

            System.out.println("Bills loaded successfully!");
        } catch (Exception e) {
            System.out.println("No previous bills found, starting fresh.");
        }
    }

    private static void saveData() {
        try {
            AppData appData = new AppData();
            appData.setBillManager(billManager);
            appData.setPayCheck(payCheck);
            jsonStorage.saveAppData(appData);

            System.out.println("Bills saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving bills: " + e.getMessage());
        }
    }


    public static void displayBills(){

            System.out.println();
            System.out.println("==================================================");
            System.out.println("                    BILL SUMMARY");
            System.out.println("==================================================");

            if (payCheck != null) {

                System.out.printf("Paycheck:                 $%.2f%n",
                        payCheck.getPayCheckAmount());

                System.out.printf("Total Bills:              $%.2f%n",
                        billManager.calculateTotalBills());

                System.out.printf("Total Paid:               $%.2f%n",
                        billManager.calculateTotalPaid());

                BigDecimal moneyRemaining =
                        payCheck.getPayCheckAmount()
                                .subtract(billManager.calculateTotalPaid());

                System.out.printf("Money Remaining:           $%.2f%n",
                        moneyRemaining);

            } else {
                System.out.println("Paycheck:                 Not Set");
            }

            System.out.println();
            System.out.println("==================================================");
            System.out.println("                    MY BILLS");
            System.out.println("==================================================");

            billManager.getBills().forEach(bill -> {

                System.out.println("Bill ID: " + bill.getId());
                System.out.println("Bill Name: " + bill.getName());
                System.out.println("Bill Amount: $" + bill.getAmount());
                System.out.println("Amount Paid: $" + bill.getPaidAmount());
                System.out.println("Bill Due Date: " +
                        bill.getDate().format(formatter));
                System.out.println("Bill Paid: " + bill.isPaid());

                System.out.println("--------------------------------------------------");
            });
        }


}

