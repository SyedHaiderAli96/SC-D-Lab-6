class JointAccount {
    private int balance = 50000;

    // Synchronized method to handle withdrawal
    public synchronized void withdraw(String user, int amount) {
        System.out.println(user + " is trying to withdraw ₹" + amount);

        if (balance >= amount) {
            System.out.println(user + " has enough balance. Processing...");
            balance -= amount;
            System.out.println(user + " successfully withdrew ₹" + amount);
        } else {
            System.out.println("Not enough balance for " + user + ". Available: ₹" + balance);
        }
        System.out.println("Remaining balance: ₹" + balance + "\n");
    }
}

// Thread for User A
class UserA extends Thread {
    JointAccount account;

    UserA(JointAccount account) {
        this.account = account;
    }

    public void run() {
        account.withdraw("User A", 45000);
    }
}

// Thread for User B
class UserB extends Thread {
    JointAccount account;

    UserB(JointAccount account) {
        this.account = account;
    }

    public void run() {
        account.withdraw("User B", 20000);
    }
}

public class BankTask {
    public static void main(String[] args) {
        JointAccount account = new JointAccount();

        UserA userA = new UserA(account);
        UserB userB = new UserB(account);

        userA.start();
        userB.start();
    }
}