package sqlSession;

import bean.Function;
import bean.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MapperProxy implements InvocationHandler {
    private SqlSession sqlSession;
    private Configuration configuration;

    public MapperProxy(Configuration configuration,SqlSession sqlSession){
        this.configuration=configuration;
        this.sqlSession=sqlSession;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean=configuration.readMapper("UserMapper.xml");
        if(!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }
        List <Function> list =mapperBean.getList();
        if(list.size()!=0||list!=null){
            for (Function f:
                 list) {
                if (method.getName().equals(f.getFuncName())){
                    return sqlSession.selectOne(f.getSql(),String.valueOf(args[0]));
                }
            }
        }        return null;
    }
}
