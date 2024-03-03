public class Product {
    private String name;
    private double cost;
    private String vatAmount;

    public Product(String name, double cost, String vatAmount){
        this.name = name;
        this.cost = cost;
        this.vatAmount = vatAmount;
    };

    public String getName(){
        return name;
    }

    public double getCost(){
        return cost;
    }

    public String getVatAmount(){
        return vatAmount;
    }


}
