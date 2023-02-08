package FunktionellProgrammering.DatabaseHappyFeet;

import java.util.Scanner;

public class Shopping {
    private final int stage = 1;
    Repository r = new Repository();
    Scanner scanner = new Scanner(System.in);
    int currentShoesId;
    int currentCustomerId;
    int currentOrderId = 0;
    boolean loggenIn;

    public void logIn() throws InterruptedException {
        boolean loggedIn = false;
        while (true) {
            String name;
            String password;
            System.out.println("Skriv in ditt namn: ");
            name = scanner.nextLine();
            System.out.println("Skriv in ditt password: ");
            password = scanner.nextLine();
            currentCustomerId = r.logIn(name, password);
            if (currentCustomerId != -1) {
                shoppingShoes();
            } else {
                System.out.println("Du har skrivit fel namn och/eller lösenord. Försök igen");
            }
        }
    }

    public void shoppingShoes() throws InterruptedException {
        r.showAllShoesItems();
        String brand;
        String color;
        int size;
        System.out.println("Vilken skomärke vill du köpa?");
        brand = scanner.next();
        System.out.println("Vilken färg?");
        color = scanner.next();
        System.out.println("Vilken Storlek?");
        size = scanner.nextInt();
        currentShoesId = r.foundShoes(brand, color, size);
        chooseShoes();
    }

    public void chooseShoes() throws InterruptedException {
        if (currentShoesId != -1) {
            if (currentOrderId == 0) {
                currentOrderId = r.callAddToCart(currentCustomerId, 0, currentShoesId);
            } else {
                r.callAddToCart(currentCustomerId, currentOrderId, currentShoesId);
            }
            System.out.println("Varan las till i beställningen!");
        } else {

            System.out.println("Fel uppstod och varan kunde inte läggas in :/ ");
        }
        continueShoppingOrView();
    }
    public void continueShoppingOrView() throws InterruptedException {
        System.out.println("Vill du fortsätta handla eller se din beställning?" +
                "\nOm du vill fortsätta handla tryck 1 om du vill se din beställning tryck 2");
        int input = scanner.nextInt();
        if (input == 1) {
            shoppingShoes();
        } else if (input == 2) {
            r.showOrder(currentOrderId);
            System.out.println("För att fortsätta handla tryck 1");
            if (scanner.nextInt() == 1){
                shoppingShoes();
            }
        }

    }
}
