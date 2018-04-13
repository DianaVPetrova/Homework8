package bg.softuni.diana.foodfacts.api;

public class Product {

    String code; //the code may have a leading zero
    String product_name;
    String ingredients_text;

    public Product(String code, String product_name, String ingredients_text) {
        this.code = code;
        this.product_name = product_name;
        this.ingredients_text = ingredients_text;
    }

    public String getCode() {
        return code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }
}
