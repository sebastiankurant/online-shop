package codecool_shop.model;

import java.util.List;

/**
 * Created by pgurdek on 16.05.17.
 */
public class Basket {

    private Integer id;

    private List<Product> productsInBasket;

    public Basket(List<Product> productsInBasket) {
        this.productsInBasket = productsInBasket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProductsInBasket() {
        return productsInBasket;
    }

    public void setProductsInBasket(List<Product> productsInBasket) {
        this.productsInBasket = productsInBasket;
    }

    public Integer getPrice(){

        Integer basketPrice = 0;
        for (Product product:this.productsInBasket){
            basketPrice+=product.getPrice();

        }
        return basketPrice;
    }


}
