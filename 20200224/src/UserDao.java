import java.sql.*;

public class UserDao {
    public User login(User loginuser) {


        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user1 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
            String sql = "select * from wapuser where username ='"+loginuser.getUsername()+ "' and password ='"+loginuser.getPassword()+"'";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1, loginuser.getUsername());
//            ps.setString(2, loginuser.getPassword());
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user1 = new User();
                user1.setUsername(resultSet.getString("username"));
                user1.setPassword(resultSet.getString("password"));
                user1.setId(resultSet.getInt("id"));
            } else {
                user1 = null;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return  user1;

    }

}