package codecool_shop.model;

import java.util.Date;
import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private Date date;
    private ProductSupplier supplier;
    private List<ProductCategory> categories = null;
    private String url;
    private Integer price;

    public Product() {

    }

    public Product(Integer id, String name, String description, Date date, List<ProductCategory> categories, String url, ProductSupplier supplier, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.supplier = supplier;
        this.categories = categories;
        this.url = url;
        this.price = price;
    }

    public Product(String name, String description, Date date, List<ProductCategory> categories, String url, ProductSupplier supplier, Integer price) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.supplier = supplier;
        this.categories = categories;
        this.url = url;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public String getClassSlug() {
        String classContainer = "";
        for (ProductCategory category : getCategories()) {
            classContainer += category.getSlug() + " ";
        }
        return classContainer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductSupplier getSupplier() {
        return supplier;
    }

    public void setSupplier(ProductSupplier supplier) {
        this.supplier = supplier;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
