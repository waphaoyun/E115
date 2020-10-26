public class User {
    private String username;
    private  String password;
    private int id;
//   User
//    public String getMysql_car_name() {
//        return mysql_car_name;
//    }
//
//    public void setMysql_car_name(String mysql_car_name) {
//        this.mysql_car_name = mysql_car_name;
//    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
