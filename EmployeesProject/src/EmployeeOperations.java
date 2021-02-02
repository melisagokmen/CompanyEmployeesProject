
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeOperations {

    private Connection con = null;

    private Statement statement = null;

    private PreparedStatement preparedStatement = null;
    public ArrayList<Employee> getEmployee(){
        try {
            ArrayList<Employee> output=new ArrayList<Employee>();
            statement=con.createStatement();
            String query="Select * from calisanlar";
            ResultSet rs=statement.executeQuery(query);
            
            while(rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String surname=rs.getString("surname");
                String department=rs.getString("department");
                String salary=rs.getString("salary");
                output.add(new Employee(id, name, surname, department, salary));
                
                
            }
            return output;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } public void updateEmployee(int id,String new_name,String new_surname,String new_dept,String new_salary){
            
            try{
            String query="Update calisanlar set name=?,surname=?,department=?,salary=? where id=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1,new_name);
            preparedStatement.setString(2,new_surname);
            preparedStatement.setString(3,new_dept);
            preparedStatement.setString(4,new_salary);
            preparedStatement.setInt(5,id);
            
            preparedStatement.executeUpdate();
            } catch (SQLException ex) {
            Logger.getLogger(EmployeeOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public void deleteEmployee(int id){
            
            try{
            String query="Delete from calisanlar where id=?";
            preparedStatement=con.prepareStatement(query);
           
            
            preparedStatement.setInt(1,id);
            
            preparedStatement.executeUpdate();
            } catch (SQLException ex) {
            Logger.getLogger(EmployeeOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    public void addEmployee(String name,String surname,String department,String salary){
        try {
            String query="Insert into calisanlar(name,surname,department,salary) VALUES(?,?,?,?)";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, salary);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public boolean login(String user_name,String password){
        try {
            String query="Select * from adminler where username=? and password=?";
            preparedStatement=con.prepareStatement(query);
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, password);
            ResultSet rs=preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeOperations.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public EmployeeOperations() {
        String url = "jdbc:mysql://" + Database.host + ":" + Database.port + "/" + Database.db_name + "?useUnicode=true&characterEncoding=utf8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, Database.user_name, Database.password);
            System.out.println("Connection successful...");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found...");
        } catch (SQLException ex) {
            System.out.println("Connection unsuccessful...");
        }

    }

}
