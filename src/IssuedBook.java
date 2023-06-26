import java.sql.*;

public class IssuedBook implements DBTable{

    private int id;
    private int BookId;
    private Date issueDate;
    private int StudentId;
    private String StudentName;
    private String StudentEmail;

    IssuedBook(int BookId, Date issueDate, int StudentId, String StudentName, String StudentEmail){
        /**constructor to create new Books**/
        this.BookId=BookId;
        this.issueDate=issueDate;
        this.StudentId=StudentId;
        this.StudentName=StudentName;
        this.StudentEmail=StudentEmail;
        String query="INSERT INTO BooksIssued (BookId, IssueDate, StudentId, StudentName, StudentEmail) VALUES("
                +BookId+", "+issueDate.toString()+","+StudentId+",'"+StudentName+"','"+StudentEmail+")";
        try {
            this.id=this.addToDb(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    IssuedBook(int id) {
        /**fetches data from the DB to create the object**/
        String query = "SELECT * FROM BooksIssued WHERE id=" + id + "";
        try (Connection conn = SQLiteJDBCDriverConnection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            this.id = resultSet.getInt(1);
            this.BookId=resultSet.getInt(2);
            this.issueDate = resultSet.getDate(3);
            this.StudentId = resultSet.getInt(4);
            this.StudentName = resultSet.getString(5);
            this.StudentEmail = resultSet.getString(6);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return BookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public int getStudentId() {
        return StudentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    @Override
    public String toString() {
        return "IssueId=" + id +
                ", issueDate=" + issueDate +
                ", StudentId=" + StudentId +
                ", StudentName='" + StudentName + '\'' +
                ", StudentEmail='" + StudentEmail + '\'';
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
}
