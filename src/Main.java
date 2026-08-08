import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BillManager billManager = new BillManager();

        boolean running = true;

        while(running) {

            IO.println("""
                    1. Add Bill
                    2. View Bills
                    3. Exit
                    """);

            String choice = sc.nextLine();

            if(choice.equals("1")) {
                Bill bill = createBill(sc);
                billManager.addBill(bill);
            } else if (choice.equals("2")) {
                billManager.getBills().forEach(bill -> {
                    System.out.println(bill.getName());
                    System.out.println(bill.getAmount());
                    System.out.println(bill.getDate());
                    System.out.println(bill.isPaid());
                });

            }else if(choice.equals("3")) {
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

        IO.println("Please enter bill date: YYYY-MM-DD");
        LocalDate date = LocalDate.parse(sc.nextLine());

        return new Bill(name, 0, amount, date, false);
    }
}