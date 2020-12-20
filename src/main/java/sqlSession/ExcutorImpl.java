package sqlSession;

import bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExcutorImpl implements Excutor{
    private Configuration xmlConfiguration=new Configuration();

    @Override
    public <T> T query(String statement, Object paramenter){
        Connection connection= xmlConfiguration.build("config.xml");
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;
        try{
            preparedStatement=connection.prepareStatement(statement);
            preparedStatement.setString(1,paramenter.toString());
            resultSet=preparedStatement.executeQuery();
            User user=new User();

            while(resultSet.next()){
                user.setId(resultSet.getString(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
            }
            System.out.println(user.getId()+user.getPassword()+user.getUsername());
            return (T)user;
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if (resultSet!=null){
                    resultSet.close();
                }
                if(preparedStatement!=null){
                    preparedStatement.close();
                }
                if(connection!=null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
