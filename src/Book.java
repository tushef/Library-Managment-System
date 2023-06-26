/**
 * Library Management System
 * Author: Fatjon Tushe
 * December 2021
 * Project For Personal Portfolio
 * */

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Book implements DBTable{
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int quantity;
    private Date dateAdded;
    private boolean issued;
    private Hashtable<Integer, IssuedBook> booksIssued = new Hashtable<Integer, IssuedBook>();

    Book(String title, String author,String publisher, int quantity, Date dateAdded){
        /**constructor to create new Books**/
        this.title=title;
        this.author=author;
        this.publisher=publisher;
        this.quantity=quantity;
        this.dateAdded=dateAdded;
        this.issued=false;
        String query="INSERT INTO Books (Title, Author, Publisher, Quantity, DateAdded, Issued) VALUES('"
                +title+"', '"+author+"','"+publisher+"',"+quantity+",'"+dateAdded.toString()+"',"+issued+")";
        try {
            this.id=this.addToDb(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    Book(String title){
        /** Constructor to create an object based on the data from the database**/
        String query = "SELECT * FROM Books WHERE Title = '"+title+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();

            this.id=rs.getInt(1);
            this.title=rs.getString(2);
            this.author=rs.getString(3);
            this.publisher = rs.getString(4);
            this.quantity = rs.getInt(5);
            this.dateAdded = rs.getDate(6);
            this.issued = rs.getBoolean(7);
            if(this.issued){
                String query1 = "SELECT * FROM BooksIssued WHERE BookId="+id+"";
                try(PreparedStatement psmt = conn.prepareStatement(query1)){
                    ResultSet resultSet = psmt.executeQuery();
                    System.out.println(resultSet.toString());
                    while(resultSet.next()) {
                        this.booksIssued.put(resultSet.getInt(1), new IssuedBook(resultSet.getInt(1)));
                    }
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    Book(int id){
        /** Constructor to create an object based on the data from the database**/
        String query = "SELECT * FROM Books WHERE id = "+id+"";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            this.id=rs.getInt(1);
            this.title=rs.getString(2);
            this.author=rs.getString(3);
            this.publisher = rs.getString(4);
            this.quantity = rs.getInt(5);
            this.dateAdded = rs.getDate(6);
            this.issued = rs.getBoolean(7);
            if(this.issued){
                String query1 = "SELECT * FROM BooksIssued WHERE BookId="+id+"";
                try(PreparedStatement psmt = conn.prepareStatement(query1)){
                    ResultSet resultSet = psmt.executeQuery();
                    System.out.println(resultSet.toString());
                    while(resultSet.next()) {
                        this.booksIssued.put(resultSet.getInt(1), new IssuedBook(resultSet.getInt(1)));
                    }
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void selfIssue(int StudentId, String StudentName, String StudentEmail){
        if(this.isIssued() && this.getQuantity()==0) {
            System.out.println("Book Already Issued and there is none left");
        }else if(this.isIssued()){
            this.setQuantity(this.getQuantity()-1);
            IssuedBook issue= new IssuedBook(this.id, new Date(System.currentTimeMillis()), StudentId, StudentName, StudentEmail);
            this.booksIssued.put(issue.getId(), issue);
        }else{
            this.setIssued(true);
            this.setQuantity(this.getQuantity()-1);
            IssuedBook issue= new IssuedBook(this.id, new Date(System.currentTimeMillis()), StudentId, StudentName, StudentEmail);
            this.booksIssued.put(issue.getId(), issue);
        }
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        String sql = "UPDATE Books SET "
                +    "Title = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, title);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        String sql = "UPDATE Books SET "
                +    "Author = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, author);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
        String sql = "UPDATE Books SET "
                +    "Publisher = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, publisher);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        String sql = "UPDATE Books SET "
                +    "Quantity = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded=dateAdded;
        String sql = "UPDATE Books SET "
                +    "DateAdded = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setDate(1, (java.sql.Date) dateAdded);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
        String sql = "UPDATE Books SET "
                +    "Issued = ? "
                +    "WHERE id = ? ";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setBoolean(1, issued);
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Book:" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                ", dateAdded=" + dateAdded +
                ", issued=" + issued);
        Enumeration<Integer> e = this.booksIssued.keys();
        while(e.hasMoreElements()){
            int key = e.nextElement();
            str.append(this.booksIssued.get(key).toString());
        }
        return str.toString();
    }
}
