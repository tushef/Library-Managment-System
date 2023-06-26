/**
 * Library Management System
 * Author: Fatjon Tushe
 * December 2021
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Base64;

public class Admin {
    //salts for better security protection
    private static String salt1="*SALT123*";
    private static String salt2="=SALT=";

    private int id;
    private String name;
    private String username;

    Admin(String username){
        /** constructor to create an object based on the data from the database **/
        String query = "SELECT * FROM Admins WHERE username='"+username+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            this.id=rs.getInt(1);
            this.name=rs.getString(2);
            this.username=rs.getString(3);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
        String query = "UPDATE Admins SET Name = ?" +
                "WHERE id = ?";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, name);
            ps.setInt(2, this.id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        String query = "UPDATE Admins SET username = ?" +
                "WHERE id = ?";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, username);
            ps.setInt(2, this.id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void setPassword(String password){
        String query = "UPDATE Admins SET password = ?" +
                "WHERE id = ?";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            password += salt2;
            password = password.substring(0, 0)+salt1+password;
            String encodedPass= Base64.getEncoder().encodeToString(password.getBytes());
            ps.setString(1, encodedPass);
            ps.setInt(2, this.id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Admin:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'';
    }

    public void addLibrarian(String name, String username, String password,String email, String address,String city){
        new Librarian(name, username, password, email, address,city);
    }

    public void viewLibrarian(int id){
        System.out.println(new Librarian(id).toString());
    }

    public void viewLibrarian(String username){
        System.out.println(new Librarian(username).toString());
    }

    public void deleteLibrarian(int id){
        String query = "DELETE FROM Librarians WHERE id="+id;
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
        PreparedStatement ps = conn.prepareStatement(query);){
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteLibrarian(String username){
        String query = "DELETE FROM Librarians WHERE username='"+username+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query);){
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
