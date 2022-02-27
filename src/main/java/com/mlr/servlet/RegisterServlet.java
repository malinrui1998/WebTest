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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //      1.接收数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

//        2.MyBatis完成查询

//        2.1 获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
//        2.2.获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        2.3.获取Mapper对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        2.4 调用方法
        User user1 = userMapper.selectByUserName(username);

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter writer = resp.getWriter();
        if (user1 == null) {
            userMapper.RegisterUser(user);
            sqlSession.commit();
            writer.write("创建成功");
        } else {
            writer.write("用户已存在");
        }
        sqlSession.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
