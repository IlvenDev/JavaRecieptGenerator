import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Receipt {

    private static String rightPad(String name){
        String productName = name;

        while (productName.length() < 12) {
            productName += " ";
        }

        return productName;
    }

    private static float calcTotalCostNetto23(Product[] products){
        float totalCost = 0;

        for (Product product : products) {
            if (product.getVatAmount() == "23%") {
                totalCost += product.getCost();
            }
        }

        return totalCost;
    };

    private static float calcTotalCostNetto8(Product[] products){
        float totalCost = 0;

        for (Product product : products) {
            if (product.getVatAmount() == "8%") {
                totalCost += product.getCost();
            }
        }

        return totalCost;
    };

    private static float calcTotalVat23(Product[] products){
        float totalTax = 0;

        for (Product product : products) {
            if (product.getVatAmount() == "23%") {
                totalTax += VatCalculator.calcTax23(product.getCost());
            }
        }

        return totalTax;
    };

    private static float calcTotalVat8(Product[] products){
        float totalTax = 0;

        for (Product product : products) {
            if (product.getVatAmount() == "8%") {
                totalTax += VatCalculator.calcTax8(product.getCost());
            }
        }

        return totalTax;
    };

    
    public static void createReciept(Product[] products){
        float totalCostNetto23 = calcTotalCostNetto23(products);
        float totalCostNetto8 = calcTotalCostNetto8(products);
        float totalTax23 = calcTotalVat23(products);
        float totalTax8 = calcTotalVat8(products);

        try{
            File receipt = new File("receipt.md");
            if (receipt.createNewFile()) {
                System.out.println("File created " + receipt.getName());
            } else {
                System.out.println("File already exists");
            }

            FileWriter receiptWriter = new FileWriter("receipt.md");
            receiptWriter.write("|               | Total netto |  VAT Amount |\n");
            receiptWriter.write("| Vat 8%        | " + rightPad(Float.toString(totalCostNetto8)) + "| " + rightPad(Float.toString(totalTax8)) + "|\n");
            receiptWriter.write("| Vat 23%       | " + rightPad(Float.toString(totalCostNetto23)) + "| " + rightPad(Float.toString(totalTax23)) + "|\n");
            receiptWriter.close();
        } catch(IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    };

    public static void main(String[] args) {
        Product[] products = new Product[3];
        products[0] = new Product("Clean Code, Robert C. Martin", 100.00, "8%");
        products[1] = new Product("Applying UML and Patterns, C. Larman", 300.00, "8%");
        products[2] = new Product("Shipping", 50.00, "23%");

        Receipt.createReciept(products);
    }
}


/* 
    |               | Total netto |     X     |
    |---------------|-------------|-----------|
    | VAT 8%        |      X      |    X`     |
    | VAT 23%       |      X      |    X``    |
*/