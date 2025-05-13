package Utils;
import java.sql.*;
public class ConnectDB {
        public static void main(String arg[])
        {
            Connection connection = null;
            try {
                // below two lines are used for connectivity.
                Class.forName("Utils.ConnectDB");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/javaavanceeresto",
                        "root", "root");

                // mydb is database
                // mydbuser is name of database
                // mydbuser is password of database

                Statement statement;
                statement = connection.createStatement();
                ResultSet resultSet;
                resultSet = statement.executeQuery(
                        "select * from plats");
                int id;
                String nom;
                while (resultSet.next()) {
                    id = resultSet.getInt("id");
                    nom = resultSet.getString("nom").trim();
                    System.out.println("Code : " + id
                            + " Title : " + nom);
                }
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (Exception exception) {
                System.out.println(exception);
            }
    } // class ends
}
