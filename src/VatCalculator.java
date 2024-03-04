public class VatCalculator {

    public static double calculateTax8(double productPrice) {

        return productPrice * 0.08;
    };

    public static double calculateTax23(double productPrice) {

        return productPrice * 0.23;
    };

    public static double calculateVAT(Product product){
        if (product.getVatAmount().equals("23%")) {
            return product.getCost() * 0.23;
        } else {
            return product.getCost() * 0.08;
        }
    };
}
