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

        boolean running = true;

        while(running) {

            IO.println("""
                    ***********
                    Welcome to the Bill System!
                    1. Add Bill
                    2. View Bills
                    3. Update Bill
                    4. Exit
                    ***********""");

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
                billManager.getBills().forEach(bill -> {
                    System.out.println("************************");
                    System.out.println("Bill Name: " + bill.getName());
                    System.out.println("Bill Amount: $" + bill.getAmount());
                    System.out.println("Bill Due Date: " + bill.getDate().format(formatter));
                    System.out.println("Bill paid: " + bill.isPaid());
                    System.out.println("************************");
                });

            }else if(choice.equals("3")) {
                System.out.println("Enter bill name: ");
                String name = sc.nextLine();
                for (Bill bill : billManager.getBills()) {
                    if (bill.getName().equals(name)) {
                        IO.println("Enter amount to be paid: ");
                        BigDecimal amount = sc.nextBigDecimal();
                        if(bill.getAmount().compareTo(amount) <= 0) {
                            bill.setPaid(true);
                        }else {
                            bill.setPaid(false);
                        }
                        bill.setAmount(bill.getAmount().subtract(amount));

                        bill.setDate(LocalDate.now());

                    }
                    billManager.updateBill(bill);
                }


            } else if (choice.equals("4")) {
                System.out.println("Goodbye!");
                running = false;
            }

        }
    }


    private static Bill createBill(Scanner sc) {

        IO.println("Please enter bill name");
        String name = sc.nextLine();

        IO.println("Please enter bill amount");
        BigDecimal amount = sc.nextBigDecimal();
        sc.nextLine();

        IO.println("Please enter bill date: DD/MM/YYYY");
        LocalDate date = LocalDate.parse(sc.nextLine(), formatter);

        return new Bill(name, 0, amount, date, false);
    }
}