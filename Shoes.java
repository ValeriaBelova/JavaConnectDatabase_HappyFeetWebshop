package FunktionellProgrammering.DatabaseHappyFeet;

public class Shoes {
    private int id;
    private String brand;
    private int priceKr;
    private int sizeId;
    private int colorId;
    private int amountStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPriceKr() {
        return priceKr;
    }

    public void setPriceKr(int priceKr) {
        this.priceKr = priceKr;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getAmountStock() {
        return amountStock;
    }

    public void setAmountStock(int amountStock) {
        this.amountStock = amountStock;
    }

    /*@Override
    public String toString() {
        return "Shoes{" + "id=" + id + ", brand='" + brand + '\'' + ", priceKr='" + priceKr + '}';
    }*/
}
