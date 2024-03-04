import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Receipt {

    private static String rightPad(String name){
        while (name.length() < 12) {
            name += " ";
        }

        return name;
    }

    private static float calculateTotalVat23(Product[] products){
        float totalTax = 0;

        for (Product product : products) {
            if ("23%".equals(product.getVatAmount())) {
                totalTax += VatCalculator.calculateTax23(product.getCost());
            }
        }

        return totalTax;
    };

    private static float calculateTotalVat8(Product[] products){
        float totalTax = 0;

        for (Product product : products) {
            if ("8%".equals(product.getVatAmount())) {
                totalTax += VatCalculator.calculateTax8(product.getCost());
            }
        }

        return totalTax;
    };

    private static Map<String, Float> calculateTotalVAT(Product[] products){
        Map<String, Float> taxes = new HashMap<>();
        float vat23 = 0, vat8 = 0;

        for (Product product : products) {
            if ("23%".equals(product.getVatAmount())) {
                vat23 += VatCalculator.calculateTax23(product.getCost());
            } else if ("8%".equals(product.getVatAmount())){
                vat8 += VatCalculator.calculateTax8(product.getCost());
            };
        }

        taxes.put("23%", vat23);
        taxes.put("8%", vat8);

        return taxes;
    };

    private static Map<String, Float> calculateTotalNetto(Product[] products){
        Map<String, Float> costs = new HashMap<>();
        float netto23 = 0, netto8 = 0;

        for (Product product : products) {
            if ("23%".equals(product.getVatAmount())) {
                netto23 += product.getCost();
            } else if ("8%".equals(product.getVatAmount())){
                netto8 += product.getCost();
            };
        }

        costs.put("23%", netto23);
        costs.put("8%", netto8);

        return costs;
    };

    
    public static void createReciept(Product[] products){
        Map<String, Float> totalCostNetto = calculateTotalNetto(products);
        Map<String, Float> totalVAT = calculateTotalVAT(products);

        try{
            File receipt = new File("receipt.md");
            if (receipt.createNewFile()) {
                System.out.println("File created " + receipt.getName());
            } else {
                System.out.println("File already exists");
            }

            FileWriter receiptWriter = new FileWriter("receipt.md");
            receiptWriter.write("|               | Total netto |  VAT Amount |\n");
            receiptWriter.write("| Vat 8%        | " + rightPad(Float.toString(totalCostNetto.get("8%"))) + "| " + rightPad(Float.toString(totalVAT.get("8%"))) + "|\n");
            receiptWriter.write("| Vat 23%       | " + rightPad(Float.toString(totalCostNetto.get("23%"))) + "| " + rightPad(Float.toString(totalVAT.get("23%"))) + "|\n");
            receiptWriter.close();
        } catch(IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    };

    public static void main(String[] args) {
        Product[] products = new Product[]{new Product("Clean Code, Robert C. Martin", 100.00, "8%"), new Product("Applying UML and Patterns, C. Larman", 300.00, "8%"),new Product("Shipping", 50.00, "23%")};
        /* products[0] = new Product("Clean Code, Robert C. Martin", 100.00, "8%");
        products[1] = new Product("Applying UML and Patterns, C. Larman", 300.00, "8%");
        products[2] = new Product("Shipping", 50.00, "23%"); */

        Receipt.createReciept(products);
    }
}