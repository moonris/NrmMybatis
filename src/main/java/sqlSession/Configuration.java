package sqlSession;

import bean.Function;
import bean.MapperBean;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*Configuration
待读取 config.xml 后，
将属性和连接数据库的操作封装在Configuration对象中供后面的组件调用。
*/
public class Configuration {
    private static ClassLoader loader=ClassLoader.getSystemClassLoader();
    //开始处理数据库配置xml
    public Connection build(String resource){
        try {
            InputStream inputStream=loader.getResourceAsStream(resource);
            SAXReader saxReader=new SAXReader();
            Document document=saxReader.read(inputStream);
            Element element=document.getRootElement();
            return DataSource(element);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    //处理数据库配置xml，且返回数据库连接对象
    private Connection DataSource(Element element) {
        if(!element.getName().equals("database")){
            throw new RuntimeException("no <database>");
        }
        String driverClassName=null;
        String url=null;
        String username=null;
        String password=null;

        for(Object item:element.elements("property")){
            Element i=(Element)item;
            String name=i.attributeValue("name");
            String value=i.hasContent()?i.getText():i.attributeValue("value");
            if(name==null||value==null){
                throw new RuntimeException("no name value");
            }
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("no switch");
            }
        }
        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection= DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    /*读取UserMapper,返回一个对象*/
    public MapperBean readMapper(String path){
        MapperBean mapperBean=new MapperBean();
        Element element = null;
        try {
            InputStream inputStream=loader.getResourceAsStream(path);
            SAXReader saxReader=new SAXReader();
            Document document=saxReader.read(inputStream);
            element=document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //数据处理
        /*
        <mapper nameSpace="mapper.UserMapper">
            <select id="getUserById" resultType="bean.User">
                select * from user where id = ?
            </select>
        </mapper>
        */
        //存储类名
        mapperBean.setInterfaceName(element.attributeValue("nameSpace").trim());
        //存储方法名
        List<Function> list=new ArrayList<>();
        for (Iterator iterator=element.elementIterator();iterator.hasNext();){
            Function function=new Function();
            Element element1= (Element) iterator.next();
            String sqlType=element1.getName().trim();
            String funcName= element1.attributeValue("id").trim();
            String resultType=element1.attributeValue("resultType").trim();
            String sql =element1.getText().trim();

            function.setSqlType(sqlType);
            function.setSql(sql);
            function.setFuncName(funcName);
            Object newInstance=null;
            //通过反射获取到User
            try {
                //TODO : java废弃newInstance方法,测试中
                newInstance=Class.forName(resultType).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            function.setResultType(newInstance);
            list.add(function);
        }
        mapperBean.setList(list);
        return mapperBean;
    }
}
