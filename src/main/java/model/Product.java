package model;

import java.util.Date;
import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private Date date;
    private List<ProductCategory> categories = null;

    public Product() {

    }

    public Product(Integer id, String name, String description, Date date, List<ProductCategory> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.categories = categories;
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
}
