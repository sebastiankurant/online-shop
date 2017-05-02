import com.codecool.shop.controller.ProductController;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProductController controller = new ProductController();
        controller.listProducts();
    }


}
