package FunktionellProgrammering.DatabaseHappyFeet;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Repository {
    List<Color> allColors = new ArrayList<>();
    List<Size> allSizes = new ArrayList<>();

    public int logIn(String name, String password) {
        List<Customer> allCustomers = new ArrayList<>();
        Connection con = null; //startläge
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Main.settingsDirectory));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            assert con != null; // checkar om connection etablerat
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");


            while (rs.next()) {
                Customer temp = new Customer();
                temp.setId(rs.getInt("id"));
                temp.setName(rs.getString("name"));
                temp.setPassword(rs.getString("password"));
                temp.setTelephone(rs.getLong("telephone"));
                temp.setStreet(rs.getString("street"));
                allCustomers.add(temp); //lägger till customers enligt kolumner
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        for (Customer customer : allCustomers) {
            if (customer.getName().equals(name) && customer.getPassword().equals(password)) {
                return customer.getId(); //plockar fram rätt
            }
        }
        return -1;
    }

    public void showAllShoesItems() {
        List<Shoes> allShoes = new ArrayList<>();
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Main.settingsDirectory));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shoes WHERE amountStock != 0");

            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setBrand(rs.getString("brand"));
                temp.setPriceKr(rs.getInt("priceKr"));
                temp.setSizeId(rs.getInt("sizeId"));
                temp.setColorId(rs.getInt("colorId"));
                temp.setAmountStock(rs.getInt("amountStock"));
                allShoes.add(temp); //lägger till shoes enligt kolumner
            }

            rs = stmt.executeQuery("SELECT * FROM color");

            while(rs.next()) {
                Color tempColor = new Color();
                tempColor.setId(rs.getInt("id"));
                tempColor.setName(rs.getString("name"));
                allColors.add(tempColor);//fyller color kolumn
            }

            rs = stmt.executeQuery("SELECT * FROM size");

            while(rs.next()) {
                Size size = new Size();
                size.setId(rs.getInt("id"));
                size.setSizeNumber(rs.getString("sizeNumber"));
                allSizes.add(size); //fyller size kolumn
            }
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }

        allShoes.forEach(shoes -> {
            Color foundColor = allColors.stream().filter(color -> color.getId() == shoes.getColorId()).findFirst().get();
            Size foundSize = allSizes.stream().filter(size -> size.getId() == shoes.getSizeId()).findFirst().get();
            System.out.println("Brand: " + shoes.getBrand() + "; Color: " + foundColor.getName()
                    + "; Size: " + foundSize.getSizeNumber() + "; Price: " + shoes.getPriceKr());
        });

    }

    public int foundShoes(String brand, String color, int size) {
        List<Shoes> allShoes = new ArrayList<>();
        int sizeId = -1, colorId = -1;
        Connection con;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Main.settingsDirectory));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shoes WHERE amountStock != 0");

            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setBrand(rs.getString("brand"));
                temp.setPriceKr(rs.getInt("priceKr"));
                temp.setSizeId(rs.getInt("sizeId"));
                temp.setColorId(rs.getInt("colorId"));
                temp.setAmountStock(rs.getInt("amountStock"));
                allShoes.add(temp);
            }


            PreparedStatement preparedStatement = con.prepareStatement("SELECT id FROM size WHERE sizeNumber = ?");
            preparedStatement.setInt(1, size);
            preparedStatement.execute();

            ResultSet row = preparedStatement.getResultSet();

            if(row.next()) {
                sizeId = row.getInt(1);
            } else throw new IllegalArgumentException("Det finns ingen sådan storlek");

            preparedStatement = con.prepareStatement("SELECT id FROM color WHERE name = ?");
            preparedStatement.setString(1, color);
            preparedStatement.execute();

            row = preparedStatement.getResultSet();

            if(row.next()) {
                colorId = row.getInt(1);
            } else throw new IllegalArgumentException("Det finns ingen sådan färg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(sizeId != -1 && colorId != -1) {
            int finalSizeId = sizeId, finalColorId = colorId;
            Optional<Shoes> shoe = allShoes.stream().filter(a -> a.getColorId() == finalColorId
                    && a.getBrand().equals(brand)
                    && a.getSizeId() == finalSizeId).findFirst();
            if(shoe.isPresent()) return shoe.get().getId();
        }
        return -1;
    }

    public int callAddToCart(int newCustomerId, int newOrderId, int newShoesId) {
        Connection con = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Main.settingsDirectory));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));

            CallableStatement stm = con.prepareCall("CALL AddToCart(?,?,?)");
            stm.setInt(1, newCustomerId);
            stm.setInt(2, newOrderId);
            stm.setInt(3, newShoesId);
            ResultSet r = stm.executeQuery();
            if(r.next()){
                return r.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void showOrder(int currentOrderId) {
        List<Shoes> allShoes = new ArrayList<>();
        Connection con = null;

        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Main.settingsDirectory));
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    p.getProperty("connectionString"),
                    p.getProperty("name"),
                    p.getProperty("password"));


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contain");

            List<Contain> allContains = new ArrayList<>();
            while(rs.next()){
                Contain temp = new Contain(rs.getInt("orderId"),
                        rs.getInt("shoesId"),
                        rs.getInt("amount"));
                allContains.add(temp);
            }
            rs = stmt.executeQuery("SELECT * FROM shoes");

            while (rs.next()) {
                Shoes temp = new Shoes();
                temp.setId(rs.getInt("id"));
                temp.setBrand(rs.getString("brand"));
                temp.setPriceKr(rs.getInt("priceKr"));
                temp.setSizeId(rs.getInt("sizeId"));
                temp.setColorId(rs.getInt("colorId"));
                temp.setAmountStock(rs.getInt("amountStock"));
                allShoes.add(temp);
            }

            List<Contain> orderContains = allContains.stream()
                    .filter(contain -> contain.getOrderId()==currentOrderId).collect(Collectors.toList());
            orderContains.forEach(contain -> {
                Shoes shoe = allShoes.stream().filter(shoes -> shoes.getId() == contain.getShoesId()).findFirst().get();
                Color foundColor = allColors.stream().filter(color -> color.getId() == shoe.getColorId()).findFirst().get();
                Size foundSize = allSizes.stream().filter(size -> size.getId() == shoe.getSizeId()).findFirst().get();

                System.out.println("Brand: " + shoe.getBrand() + "; Color: " + foundColor.getName()
                        + "; Size: " + foundSize.getSizeNumber() + "; Price: " + shoe.getPriceKr());
            });

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
