package bg.softuni.diana.foodfacts.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProductItem {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "product_name")
    private String product_name;

    @ColumnInfo(name = "ingredients_text")
    private String ingredients_text;

    public ProductItem(String code, String product_name, String ingredients_text) {
        this.code = code;
        this.product_name = product_name;
        this.ingredients_text = ingredients_text;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public void setIngredients_text(String ingredients_text) {
        this.ingredients_text = ingredients_text;
    }
}
