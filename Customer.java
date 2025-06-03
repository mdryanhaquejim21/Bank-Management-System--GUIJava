class Customer extends Person {
    private double balance;

    public Customer(String name, String id, double balance) {
        super(name, id);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void showInfo() {
        super.showInfo();
        System.out.println("Balance: $" + balance);
    }
}
