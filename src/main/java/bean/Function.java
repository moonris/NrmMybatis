package bean;

public class Function {
    private String sqlType;
    private String funcName;
    private String sql;
    private Object resultType;
    private String paramenterType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }

    public String getParamenterType() {
        return paramenterType;
    }

    public void setParamenterType(String paramenterType) {
        this.paramenterType = paramenterType;
    }
}
