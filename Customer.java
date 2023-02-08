package FunktionellProgrammering.DatabaseHappyFeet;

public class Customer {
    private int id;
    private String name;
    private String password;
    private Long telephone;
    private String street;
    private Long houseNumber;
    private Long postcode;
    private String city;
    private String country;

    //public Customer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Long getPostcode() {
        return postcode;
    }

    public void setPostcode(Long postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /*@Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + '}';
    }*/
}
