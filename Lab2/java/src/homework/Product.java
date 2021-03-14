package homework;

enum Product {
    OXYGEN,
    BOOTS,
    BACKPACK,
    ERROR;

    public static Product getProduct(String product) {
        Product result = ERROR;
        try {
            result = valueOf(product);
        } catch (IllegalArgumentException e) {
            System.out.println("No such product: " + product + ". The product will be omitted.");
        }
        return result;
    }
}
