package codecool_shop.model;


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

    public void setId(Integer id) {
        if (id == null || id<0){
            throw new IllegalArgumentException("Id is not valid");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (getName() == null){
            throw new IllegalArgumentException("Name can not be null");
        }
        this.name = name;
    }

    public void setAddress(String address) {
        if (address.equals(null)){
            throw new IllegalArgumentException("Address can not be null");
        }
        this.address = address;
    }
}
