class Printer {
    private int availablePages = 10;

    // Synchronized method to print pages
    public synchronized void printPages(int pages) {
        System.out.println("Requested to print " + pages + " pages.");

        while (availablePages < pages) {
            System.out.println("Not enough pages! Waiting for refill...");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        availablePages -= pages;
        System.out.println("Printed " + pages + " pages. Remaining pages: " + availablePages);
    }

    // Synchronized method to refill pages
    public synchronized void refillPages(int pages) {
        System.out.println("Refilling " + pages + " pages...");
        availablePages += pages;
        System.out.println("Refill complete. Available pages: " + availablePages);
        notify();
    }
}

// Thread for printing
class PrintJob extends Thread {
    Printer printer;

    PrintJob(Printer printer) {
        this.printer = printer;
    }

    public void run() {
        printer.printPages(15);
    }
}

// Thread for refilling
class RefillJob extends Thread {
    Printer printer;

    RefillJob(Printer printer) {
        this.printer = printer;
    }

    public void run() {
        try {
            Thread.sleep(1000); // Simulating delay for refill
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printer.refillPages(10);
    }
}

public class PrinterTask {
    public static void main(String[] args) {
        Printer printer = new Printer();

        PrintJob printJob = new PrintJob(printer);
        RefillJob refillJob = new RefillJob(printer);

        printJob.start();
        refillJob.start();
    }
}