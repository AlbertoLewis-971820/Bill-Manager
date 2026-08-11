import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BillManager billManager = new BillManager();

        PayCheck payCheck = null;

        boolean running = true;




        while(running) {

            while (running) {

                System.out.println();
                System.out.println("==================================================");
                System.out.println("                  BILL TRACKER");
                System.out.println("==================================================");

                // You can add your paycheck summary here later
                if (payCheck != null) {
                    System.out.printf("Paycheck:                 $%.2f%n",
                            payCheck.getPayCheckAmount());

                    System.out.printf("Total Bills:              $%.2f%n",
                            billManager.calculateTotalBills());

                    System.out.printf("Total Paid:              $%.2f%n",
                            billManager.calculateTotalPaid());

                    System.out.printf("Money Remaining:          $%.2f%n",
                            payCheck.getPayCheckAmount()
                                    .subtract(billManager.calculateTotalPaid()));
                } else {
                    System.out.println("Paycheck:                 Not Set");
                }

                System.out.println();
                System.out.println("==================================================");
                System.out.println("                    MAIN MENU");
                System.out.println("==================================================");

                System.out.println("1. Add Bill");
                System.out.println("2. View Bills");
                System.out.println("3. Update Bill");
                System.out.println("4. Add / Update Paycheck");
                System.out.println("5. Exit");

                System.out.println("--------------------------------------------------");
                System.out.print("Enter your choice: ");

                String choice = sc.nextLine();


                if(choice.equals("1")) {

                    try {
                        Bill bill = createBill(sc);
                        billManager.addBill(bill);
                        System.out.println("Bill added successfully!");
                    }catch(DateTimeParseException e){
                        System.out.println("Invalid date format please enter as DD/MM/YYYY");
                    }

                } else if (choice.equals("2")) {
                    PayCheck finalPayCheck = payCheck;
                    PayCheck remainingPayCheck = payCheck;
                    billManager.getBills().forEach(bill -> {
                        BigDecimal total = billManager.calculateTotalBills();
                        BigDecimal moneyRemaining = remainingPayCheck.getPayCheckAmount().subtract(billManager.calculateTotalPaid());
                        System.out.println("====================================================");
                        System.out.println("====================================================");
                        System.out.println("Bills Total: $" + total);
                        if(remainingPayCheck != null) {
                            System.out.println("Bills Remaining Amount: $" + moneyRemaining);
                        }else{
                            System.out.println("Bills Remaining: Not Set");
                        }

                        if(finalPayCheck != null){
                            System.out.println("Paycheck Amount: $" + finalPayCheck.getPayCheckAmount());
                        }else{
                            System.out.println("Please enter a paycheck first.");
                        }

                        System.out.println("====================================================");
                        System.out.println("                  My Bills                          ");
                        System.out.println("Bill: " + bill.getName());
                        System.out.println("Bill Amount: $" + bill.getAmount());
                        System.out.println("Bill Due Date: " + bill.getDate().format(formatter));
                        System.out.println("Bill paid: " + bill.isPaid());
                        System.out.println("====================================================");
                    });

                }else if(choice.equals("3")) {
                    System.out.println("Enter bill name: ");
                    String name = sc.nextLine();
                    for (Bill bill : billManager.getBills()) {
                        if (bill.getName().equals(name)) {
                            IO.println("Enter amount to be paid: ");
                            BigDecimal amount = sc.nextBigDecimal();
                            if (bill.getPaidAmount().compareTo(bill.getAmount()) >= 0) {
                                bill.setPaid(true);
                            } else {
                                bill.setPaid(false);
                            }
                            bill.setPaidAmount(bill.getPaidAmount().add(amount));

                            bill.setDate(LocalDate.now());

                        }
                        billManager.updateBill(bill);
                    }


                } else if (choice.equals("4")) {
                    payCheck = addPaycheck(sc);

                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    running = false;
                }
            }



        }
    }

    private static PayCheck addPaycheck(Scanner sc){

        IO.println("How much is your paycheck?");
        BigDecimal payCheckAmount = sc.nextBigDecimal();
        sc.nextLine();
        System.out.println("Paycheck Amount of: $" + payCheckAmount + " added!");

        return new PayCheck(payCheckAmount);

    }



    private static Bill createBill(Scanner sc) {

        IO.println("Please enter bill name");
        String name = sc.nextLine();

        IO.println("Please enter bill amount");
        BigDecimal amount = sc.nextBigDecimal();
        sc.nextLine();

        IO.println("Please enter bill date: MM/DD/YYYY");
        LocalDate date = LocalDate.parse(sc.nextLine(), formatter);

        return new Bill(name, 0, amount, BigDecimal.ZERO, date, false);
    }
}