import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner readInput = new Scanner(System.in);
        int input;
        int id;
        String pass;

        User loginObj = new User();
        Manager managerObj = new Manager();
        Stud studObj = new Stud();

        while (true) {
            loginObj.loginMenu();
            input = readInput.nextInt();

            // select from loginMenu
            switch (input) {
                // login as manager
                case 1:
                    System.out.print("Enter manager id: ");
                    id = readInput.nextInt();
                    System.out.print("Enter password: ");
                    pass = readInput.next();
                    managerObj.managerLogin(id, pass);
                    break;
                // login as student
                case 2:
                    System.out.print("Enter student id: ");
                    id = readInput.nextInt();
                    System.out.print("Enter password: ");
                    pass = readInput.next();
                    studObj.studentLogin(id, pass);
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }
}
