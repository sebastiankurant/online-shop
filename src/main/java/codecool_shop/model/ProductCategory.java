package codecool_shop.model;

import codecool_shop.utilities.SlugGenerator;

/**
 * Created by pgurdek on 10.05.17.
 */
public class ProductCategory {
    private Integer id;
    private String name;
    private String description;
    private String slug;


    private Boolean equal;


    public ProductCategory() {

    }

    public ProductCategory(Integer id, String name, String description, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.equal = false;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = SlugGenerator.toSlug(slug);
    }

    public Boolean getEqual() {
        return equal;
    }

    public void setEqual(Boolean equal) {
        this.equal = equal;
    }
}
