package codecool_shop.model;

import java.util.List;

/**
 * Created by pgurdek on 16.05.17.
 */
public class Basket {

    private Integer id;
    private List<Product> productsInBasket;

    public Basket(Integer id, List<Product> productsInBasket) {
        this.id = id;
        this.productsInBasket = productsInBasket;
    }
}
