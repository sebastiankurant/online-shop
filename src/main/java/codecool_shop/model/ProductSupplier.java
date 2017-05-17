package codecool_shop.model;

/**
 * Created by monika on 16.05.17.
 */
public class ProductSupplier {
    private Integer id;
    private String name;
    private String address;

    public ProductSupplier() {

    }

    public ProductSupplier(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
