package codecool_shop.model;

public class User {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String type;
    private Basket basket;

    public User(String username, String firstName, String lastName, String password, String type) {
        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
