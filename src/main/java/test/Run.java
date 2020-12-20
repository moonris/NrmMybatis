package test;

import bean.User;
import mapper.UserMapper;
import sqlSession.SqlSession;

public class Run {
    public static void main(String[] args) {
        SqlSession sqlSession=new SqlSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        User user=userMapper.getUserById("1");
        //System.out.println(user);
    }
}
