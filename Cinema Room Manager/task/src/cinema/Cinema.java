package cinema;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Cinema {

    static int ROWS;
    static int COLUMNS;
    static int SEATS;
    final static char FREE_SEAT = 'S';
    final static char BOOKED_SEAT= 'B';
    static char[][] cinema;
    static int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int choice;

        System.out.println("Enter the number of rows:");
        ROWS = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        COLUMNS = scanner.nextInt();
        SEATS = ROWS * COLUMNS;

        generatePlan();

        while (!exit) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printPlan();
                    break;
                case 2:
                    boolean taken = false;
                    while (!taken) {
                        System.out.println("Enter a row number:");
                        int selectedRow = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        int selectedColumn = scanner.nextInt();
                        taken = bookSeat(selectedRow, selectedColumn);
                    }
                    break;
                case 3:
                    statistics();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Not a valid choice!");
                    break;
            }
        }
    }

    public static void generatePlan() {
        cinema = new char[ROWS + 1][COLUMNS + 1];
        cinema[0][0] = ' ';
        for (int i = 1; i < ROWS + 1; i++) {
            for (int j = 1; j < COLUMNS + 1; j++) {
                cinema[i][j] = FREE_SEAT;
            }
        }
        for (int k = 1; k < COLUMNS + 1; k++) {
            cinema[0][k] = (char)(k + '0');
            if (k < ROWS + 1) {
                cinema[k][0] = (char)(k + '0');
            }
        }
    }

    public static void printPlan() {
        System.out.println("\nCinema:");
        for (char[] i: cinema) {
            for (char j: i) {
                System.out.print(j + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    public static boolean bookSeat(int row, int column) {
        int price;
        if (row > ROWS || column > COLUMNS) {
            System.out.println("Wrong input!");
            return false;
        } else if (cinema[row][column] == BOOKED_SEAT) {
            System.out.println("That ticket has already been purchased!");
            return false;
        } else {
            if (!(SEATS > 60)) {
                price = 10;
            } else if (row <= ROWS / 2) {
                price = 10;
            } else {
                price = 8;
            }
            currentIncome += price;
            System.out.println("\nTicket Price: $" + price);
            cinema[row][column] = BOOKED_SEAT;
            return true;
        }
    }

    public static void statistics() {
        System.out.println("Number of purchased tickets: " + checkBooked());
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println("Percentage: " + df.format((double) checkBooked() / (double) SEATS * 100) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + maxIncome());
    }

    public static int checkBooked() {
        int countedTickets = 0;
        for (char[] i: cinema) {
            for (char j: i) {
                if (j == BOOKED_SEAT) {
                    countedTickets++;
                }
            }
        }
        return countedTickets;
    }

    public static int maxIncome() {
        int frontHalf = ROWS / 2;
        int backHalf = ROWS - frontHalf;
        int income = ((frontHalf * COLUMNS * 10) + (backHalf * COLUMNS * 8));
        return income;
    }
}