/**
 * Library Management System
 * Author: Fatjon Tushe
 * December 2021
 * Project For Personal Portfolio
 * */

import java.sql.*;
import java.sql.SQLException;
import java.util.Base64;

public class Librarian implements DBTable{

    //salts for better security protection
    private static String salt1="*SALT123*";
    private static String salt2="=SALT=";

    private int id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String city;

    Librarian(String name, String username, String password,String email, String address,String city){
        /**constructor to create new Librarian**/
        this.name=name;
        this.username=username;
        this.address=address;
        this.email=email;
        this.city=city;
        password += salt2;
        password = password.substring(0, 0)+salt1+password;
        String encodedPass= Base64.getEncoder().encodeToString(password.getBytes());
        String query = "INSERT INTO Librarians (Name, username, password, Email, Address, City) Values('"+name+"', '"
                +username+"', '"+encodedPass+"', '"+email+"', '"+address+"', '"+city+")";
        try{this.id=this.addToDb(query);}catch(SQLException e){System.out.println(e.getMessage());}
    }

    Librarian(String username){
        /** constructor to create an object based on the data from the database **/
        String query = "SELECT * FROM Librarians WHERE username='"+username+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            this.id=rs.getInt(1);
            this.name=rs.getString(2);
            this.username=rs.getString(3);
            this.email = rs.getString(5);
            this.address = rs.getString(6);
            this.city = rs.getString(7);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    Librarian(int id){
        /** constructor to create an object based on the data from the database **/
        String query = "SELECT * FROM Librarians WHERE id="+id+"";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            this.id=rs.getInt(1);
            this.name=rs.getString(2);
            this.username=rs.getString(3);
            this.email = rs.getString(5);
            this.address = rs.getString(6);
            this.city = rs.getString(7);
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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
        String sql = "UPDATE Librarians SET "
                +    "Name = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUsername(String username) {
        this.username = username;
        String sql = "UPDATE Librarians SET "
                +    "username = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, username);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setEmail(String email) {
        this.email = email;
        String sql = "UPDATE Librarians SET "
                +    "Email = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, email);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAddress(String address) {
        this.address = address;
        String sql = "UPDATE Librarians SET "
                +    "Address = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, address);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCity(String city) {
        this.city = city;
        String sql = "UPDATE Librarians SET "
                +    "City = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, city);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int addToDb(String query) throws SQLException {
        int id = -1;
        Connection conn = SQLiteJDBCDriverConnection.connect();
        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs != null && rs.next()){
            id = rs.getInt(1);
        }
        if(conn!=null){
            conn.close();
        }

        return id;
    }

    public void addBook(String title, String author,String publisher, int quantity, Date dateAdded){
        new Book(title, author, publisher, quantity, dateAdded);
    }

    public void viewBook(int id){
        System.out.println(new Book(id).toString());
    }

    public void viewBook(String title){
        System.out.println(new Book(title).toString());
    }

    public void deleteBook(int id){
        String query = "DELETE FROM Books WHERE id="+id;
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query);){
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteBook(String title){
        String query = "DELETE FROM Books WHERE title='"+title+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query);){
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void issueBook(int BookId, int StudentId, String StudentName, String StudentEmail){
        Book b1 = new Book(BookId);
        b1.selfIssue(StudentId, StudentName, StudentEmail);
    }

    public void returnBook(int IssueId){
        String sql = "DELETE FROM BooksIssued WHERE id = ?";

        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, IssueId);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewIssuedBooks(){
        String sql = "SELECT * FROM BooksIssued";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement pstm = conn.prepareStatement(sql)){
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                System.out.println(new IssuedBook(rs.getInt("id")).toString());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Librarian:" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'';
    }
}
