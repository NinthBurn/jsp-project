package com.webforum.lab9web.backend.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webforum.lab9web.backend.model.User;
import com.webforum.lab9web.backend.dao.UserDAO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

@WebServlet(name = "userRegisterServlet", value = "/register")
public class UserRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    public UserRegisterServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("Served at: ").append(req.getContextPath());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject respJson;
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        String userName, email, password, passwordConfirm;

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

        userName = jsonObject.getString("user_name");
        email = jsonObject.getString("user_email");
        password = jsonObject.getString("user_password");
        passwordConfirm = jsonObject.getString("user_password_confirm");

        if(userName == null || email == null || password == null || passwordConfirm == null) {
            responseBuilder.add("status", "Empty field detected.");

        } else if(!password.equals(passwordConfirm)) {
            responseBuilder.add("status", "Passwords do not match.");

        } else {
            User user = new User(userName, password, email);

            try {
                userDAO.registerUser(user);
                responseBuilder.add("status", "register");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                responseBuilder.add("status", "Email address is already used.");
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
