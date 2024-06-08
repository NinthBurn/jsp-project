package com.webforum.lab9web.backend.controller;

import com.webforum.lab9web.backend.dao.UserDAO;
import com.webforum.lab9web.backend.model.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@WebServlet(name = "userLoginServlet", value = "/login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;
    private final UserDAO userDAO = new UserDAO();

    public UserLoginServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("Served at: ").append(req.getContextPath());

        HttpSession session = req.getSession(true);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject respJson;
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        HttpSession session = req.getSession();

        String userName, email, password;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = req.getReader();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }

        String json = sb.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        email = jsonObject.getString("user_email");
        password = jsonObject.getString("user_password");

        if(email == null || password == null) {
            responseBuilder.add("status", "Empty field detected.");

        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            try {
                userDAO.loginUser(user);
                if(user.getId() != -1){
                    responseBuilder.add("status", "login");
                    session.setAttribute("user_name", user.getUserName());
                    session.setAttribute("user_id", user.getId());

                }else{
                    responseBuilder.add("status", "Account does not exist.");
                    session.invalidate();
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                responseBuilder.add("status", "An server-side error occured.");
                session.invalidate();
            }

            respJson = responseBuilder.build();
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            System.out.println("Response: " + respJson.toString());
            resp.getWriter().write(respJson.toString());
            resp.getWriter().flush();
        }
    }
}
