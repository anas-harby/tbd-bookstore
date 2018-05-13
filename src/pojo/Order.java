package pojo;

public class Order {

    private String ISBN;
    private int quantity;

    public Order(String ISBN, int quantity) {
        this.ISBN = ISBN;
        this.quantity = quantity;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getQuantity() {
        return quantity;
    }
}
