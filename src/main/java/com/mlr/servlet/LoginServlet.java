package com.mlr.servlet;

import com.mlr.mapper.UserMapper;
import com.mlr.pojo.User;
import com.mlr.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      1.接收数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");

//        2.MyBatis完成查询

//        2.1 获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
//        2.2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        2.3.获取Mapper对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        2.4 调用方法
        User user = userMapper.Login(username, password);
//        2.5释放资源
        sqlSession.close();

//        4.获取字符输出流
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        if (user != null) {
            writer.write("欢迎" + username + "登录成功");
        } else {
            writer.write("登录失败");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
