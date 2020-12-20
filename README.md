# NrmMybatis

### 1.前言

我的博客：https://www.nroma.cn

### 2.NroServer介绍

简单模拟Mybatis

##### 2.1 部署项目



```
git clone https://github.com/moonris/NrmMybatis.git 
```



##### 2.2 启动项目

```
src/main/java/test/Run
```



### 3、思路



##### 1.解析xml文件，获得mysql配置文件并建立连接返回

##### 2.解析mapper文件，获得sql对象并返回

##### 3.创建Excutor及Excutor实现类

##### 4.通过Session来搭建Configuration和执行器Excutor的桥梁

##### 5.创建MapperProxy，使用动态代理生成Mapper对象

