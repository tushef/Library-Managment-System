/**
 * Library Management System
 * Author: Fatjon Tushe
 * December 2021
 * Project For Personal Portfolio
 * */
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    //salts for better security protection
    private static String salt1="*SALT123*";
    private static String salt2="=SALT=";

    public static boolean loginAdmin(String username, String password) {
        String query = "SELECT username, password FROM Admins WHERE username='"+username+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            //System.out.println(rs.getString(1) + " " + rs.getString(2));
            password += salt2;
            password = password.substring(0, 0)+salt1+password;
            if(rs!=null){
                String encodedPass= Base64.getEncoder().encodeToString(password.getBytes());
                if(encodedPass.equals(rs.getString(2))){
                    System.out.println("Admin Login Successful");
                    return true;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean loginLibrarian(String username, String password){
        String query = "SELECT username, password FROM Librarians WHERE username='"+username+"'";
        try(Connection conn = SQLiteJDBCDriverConnection.connect();
            PreparedStatement ps = conn.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            //System.out.println(rs.getString(1) + " " + rs.getString(2));
            password += salt2;
            password = password.substring(0, 0)+salt1+password;
            if(rs!=null){
                String encodedPass= Base64.getEncoder().encodeToString(password.getBytes());
                if(encodedPass.equals(rs.getString(2))){
                    System.out.println("Librarian Login Successful");
                    return true;
                }
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args){
        while(true){
            System.out.println("Login Window ");
            System.out.println("Login as Admin: Press 1 ");
            System.out.println("Login as Librarian: Press 2 ");
            Scanner sc = new Scanner(System.in);
            int pressedButton = sc.nextInt();
            if(pressedButton==1){
                // username: admin pass: admin123
                System.out.println("Admin Login: ");
                System.out.println("username: ");
                String temp = sc.nextLine();
                temp = null;
                System.gc();
                String username = sc.nextLine();
                System.out.println("password: ");
                String password = sc.nextLine();
                if(loginAdmin(username, password)){
                    //set pass to null and delete
                    Admin admin1= new Admin(username);
                    /**
                     * Output Functionalities
                     *
                     * Add/View/Delete Librarians
                     * Log out
                     * **/
                    while(true){
                        System.out.println("Press number to initiate functionalities:");
                        System.out.println("1) Add Librarians");
                        System.out.println("2) View Librarians");
                        System.out.println("3) Delete Librarians");
                        System.out.println("4) Log Out");
                        pressedButton = sc.nextInt();
                        if(pressedButton==1){
                            /**
                             * Add Librarian
                             * Name
                             * Username
                             * Password
                             * Email
                             * Address
                             * City
                             * **/
                            System.out.println("Add Librarian");
                            System.out.println("Please enter the name:");
                            String name = sc.nextLine();
                            System.out.println("Please enter the username:");
                            String usrname = sc.nextLine();
                            System.out.println("Please enter the wished password:");
                            String pass = sc.nextLine();
                            System.out.println("Please enter the email:");
                            String email = sc.nextLine();
                            System.out.println("Please enter the address(Street and Housenumber):");
                            String address = sc.nextLine();
                            System.out.println("Please enter the city:");
                            String city = sc.nextLine();
                            admin1.addLibrarian(name, usrname, pass, email, address, city);
                        }else if(pressedButton==2){
                            /**
                             * View Librarian
                             * Id || Name
                             * **/
                            System.out.println("View Librarian");
                            System.out.println("Please press to search by 1) username or 2) id:");
                            pressedButton = sc.nextInt();
                            if(pressedButton==1){
                                System.out.println("Please enter the username:");
                                String temp1 = sc.nextLine();
                                temp1 = null;
                                System.gc();
                                String usernamePar = sc.nextLine();
                                try{
                                    admin1.viewLibrarian(usernamePar);
                                }catch(Exception e){
                                    System.out.println("Librarian View didnt show correctly");
                                }
                            }else if(pressedButton==2){
                                System.out.println("Please enter the id");
                                int id = sc.nextInt();
                                try{
                                    admin1.viewLibrarian(id);
                                }catch(Exception e){
                                    System.out.println("Librarian View didnt show correctly");
                                }
                            }

                        }else if(pressedButton==3){
                            /**
                             * Delete Librarian
                             * id || username
                             * */
                            System.out.println("Delete Librarian");
                            System.out.println("Please press to delete using 1) username or 2) id:");
                            pressedButton = sc.nextInt();
                            if(pressedButton==1){
                                System.out.println("Please enter the username:");
                                String temp1 = sc.nextLine();
                                temp1 = null;
                                System.gc();
                                String usernamePar = sc.nextLine();
                                try{
                                    admin1.deleteLibrarian(usernamePar);
                                    System.out.println("Librarian deleted successfully");
                                }catch(Exception e){
                                    System.out.println("Librarian was not deleted successfully");
                                }
                            }else if(pressedButton==2){
                                System.out.println("Please enter the id");
                                int id = sc.nextInt();
                                try{
                                    admin1.deleteLibrarian(id);
                                    System.out.println("Librarian deleted successfully");
                                }catch(Exception e){
                                    System.out.println("Librarian was not deleted successfully");
                                }
                            }
                        }else if(pressedButton==4){
                            System.out.println("Logging Out...");
                            break;
                        }
                    }

                }else{
                    System.out.println("Admin Login not successful");
                }
            }else if(pressedButton==2){
                System.out.println("Librarian Login: ");
                System.out.println("username: ");
                String temp = sc.nextLine();
                temp = null;
                System.gc();
                String username = sc.nextLine();
                System.out.println("password: ");
                String password = sc.nextLine();
                if(loginLibrarian(username, password)){
                    Librarian librarian1= new Librarian(username);
                    /**
                     * Output Functionalities
                     *
                     * Add/View Books
                     * Issue Books
                     * View Issued Books
                     * Return Books
                     * Log out
                     * **/

                    while(true){
                        System.out.println("Press number to initiate functionalities:");
                        System.out.println("1) Add Book");
                        System.out.println("2) View Book");
                        System.out.println("3) Issue Book");
                        System.out.println("4) View Issued Books");
                        System.out.println("5) Return Book");
                        System.out.println("6) Log Out");
                        pressedButton = sc.nextInt();
                        if(pressedButton == 1){
                            System.out.println("Add Book:");
                            System.out.println("Title:");
                            String temp2 = sc.nextLine();
                            temp2 = null;
                            System.gc();
                            String title = sc.nextLine();
                            System.out.println("Author:");
                            String author = sc.nextLine();
                            System.out.println("Publisher");
                            String publisher = sc.nextLine();
                            System.out.println("Quantity");
                            int quantity = sc.nextInt();
                            try{
                                librarian1.addBook(title, author, publisher, quantity, new Date(System.currentTimeMillis()));
                                System.out.println("Book Added with success");
                            }catch(Exception e){
                                System.out.println(e.getMessage());
                            }
                        }else if(pressedButton==2){
                            System.out.println("View Book - use 1) Title or 2) Id:");
                            pressedButton = sc.nextInt();
                            if (pressedButton==1){
                                System.out.println("Write the Title:");
                                String temp2 = sc.nextLine();
                                temp2 = null;
                                System.gc();
                                String title = sc.nextLine();
                                librarian1.viewBook(title);
                            }else if (pressedButton == 2){
                                System.out.println("Write the Id:");
                                int id = sc.nextInt();
                                librarian1.viewBook(id);
                            }
                        }else if (pressedButton==3){
                            System.out.println("Issue Book:");
                            /**
                             * Issue a book
                             * BookID
                             * StudentID
                             * StudentName
                             * StudentEmail
                             * **/
                            System.out.println("Book ID:");
                            int BookId = sc.nextInt();
                            System.out.println("Student ID:");
                            int StudentId = sc.nextInt();
                            System.out.println("Student Name:");
                            String temp2 = sc.nextLine();
                            temp2=null;
                            System.gc();
                            String StudentName=sc.nextLine();
                            System.out.println("Student Email:");
                            String temp3 = sc.nextLine();
                            temp2=null;
                            System.gc();
                            String StudentEmail = sc.nextLine();
                            librarian1.issueBook(BookId, StudentId, StudentName, StudentEmail);
                        }else if(pressedButton==4){
                            /**
                             * View Issued Books
                             * no args
                             * **/
                            librarian1.viewIssuedBooks();
                        }else if(pressedButton==5){
                            /**
                             * Return Books
                             * Issue Id
                             * **/
                            System.out.println("Issue Id for the book being returned:");
                            int issueId = sc.nextInt();
                            librarian1.returnBook(issueId);
                        }else if(pressedButton==6){
                            System.out.println("Logging Out...");
                            break;
                        }else{
                            continue;
                        }
                    }
                }else{
                    System.out.println("Librarian Login not successful");
                }
            }
        }
    }
}
