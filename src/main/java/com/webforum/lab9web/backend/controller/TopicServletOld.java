package com.webforum.lab9web.backend.controller;

import com.webforum.lab9web.backend.dao.TopicDAO;
import com.webforum.lab9web.backend.dao.UserDAO;
import com.webforum.lab9web.backend.model.Topic;
import com.webforum.lab9web.backend.model.User;
import jakarta.json.*;

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
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "topicServletOld", value = "/no")
public class TopicServletOld extends HttpServlet {
    private static final long serialVersionUID = 33L;
    private final TopicDAO topicDAO = new TopicDAO();
    private final UserDAO userDAO = new UserDAO();
    public TopicServletOld(){
        super();
    }

    protected void doGeta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try{
            switch(action){
                case "/new":
                    showNewTopicForm(req, resp);
                    break;

                case "/delete":
                    deleteTopic(req, resp);
                    break;

                case "/edit":
                    showEditTopicForm(req, resp);
                    break;

                case "/get":
                    getTopic(req, resp);
                    break;

                default:
                    getAllTopics(req, resp);
                    break;
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void showNewTopicForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/create_topic.jsp");

        if(session.getAttribute("user_id") == null){
            dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        dispatcher.forward(req, resp);
    }

    protected void showEditTopicForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/edit_topic.jsp");

        if(session.getAttribute("user_id") == null){
            dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        dispatcher.forward(req, resp);
    }

    private void insertTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        int userId = (int)session.getAttribute("user_id");
        int status = 0;

        Topic result = new Topic();
        JsonObject respJson;
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

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

        result.setUserId(userId);
        result.setTitle(jsonObject.getString("topic_title"));
        result.setText(jsonObject.getString("topic_text"));
        result.setUploadTime(new Date(jsonObject.getString("topic_date")));

        try {
            status = topicDAO.registerTopic(result);
            if(status > 0){
                responseBuilder.add("status", "success");

            }else{
                responseBuilder.add("status", "failure");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseBuilder.add("status", "An server-side error occured.");
        }

        respJson = responseBuilder.build();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Response: " + respJson.toString());
        resp.getWriter().write(respJson.toString());
        resp.getWriter().flush();
    }

    private void deleteTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        int userId = (int)session.getAttribute("user_id");
        int status = 0;

        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

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

        int topicId = jsonObject.getInt("topic_id");

        try {
            Topic topic = topicDAO.getTopic(topicId);

            if(topic.getId() != -1){
                if(topic.getUserId() == userId){
                    topicDAO.deleteTopic(topicId);
                    responseBuilder.add("status", "success");

                }else{
                    responseBuilder.add("status", "not_owner");
                }

            }else{
                responseBuilder.add("status", "failure");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseBuilder.add("status", "An server-side error occured.");
        }

        JsonObject respJson;
        respJson = responseBuilder.build();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Response: " + respJson.toString());
        resp.getWriter().write(respJson.toString());
        resp.getWriter().flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().append("Served at: ").append(req.getContextPath());
        HttpSession session = req.getSession();

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion.jsp");
        dispatcher.forward(req, resp);

        if(session.getAttribute("user_id") == null){
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
        }

        try{
            getAllTopics(req, resp);

        }catch(Exception e) {}

//        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion.jsp");
//        dispatcher.forward(req, resp);

//        List<Topic> topics = null;
//
//        try{
//            topics = topicDAO.getAllTopics();
//
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        req.setAttribute("topicList", topics);
//
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/topics.jsp");
//        dispatcher.forward(req, resp);
    }

    private void getAllTopics(HttpServletRequest req, HttpServletResponse resp) throws ClassNotFoundException, SQLException, IOException{
        JsonArray respJson;
        JsonArrayBuilder respJsonArray = Json.createArrayBuilder();
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        HttpSession session = req.getSession();

        List<Topic> topics = topicDAO.getAllTopics();

        for(Topic topic : topics){
            respJsonArray.add(getTopicJson(topic.getId()));
        }

        respJson = respJsonArray.build();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Response: " + respJson.toString());
        resp.getWriter().write(respJson.toString());
        resp.getWriter().flush();
    }

    private void getTopic(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        JsonObject respJson;
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        HttpSession session = req.getSession();

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

        int topicId = jsonObject.getInt("topic_id");

        respJson = getTopicJson(topicId);
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Response: " + respJson.toString());
        resp.getWriter().write(respJson.toString());
        resp.getWriter().flush();
    }

    private JsonObject getTopicJson(int topicId) throws SQLException, ClassNotFoundException {
        Topic topic = topicDAO.getTopic(topicId);

        if(topic.getUserId() == -1)
            return null;

        // non-null foreign key; user should always exist, no need to check
        User user = userDAO.getUser(topic.getUserId());

        JsonObjectBuilder topicBuilder = Json.createObjectBuilder();

        topicBuilder.add("topic_id", topic.getId());
        topicBuilder.add("topic_title", topic.getTitle());
        topicBuilder.add("topic_date", topic.getUploadTime().toString());
        topicBuilder.add("topic_text", topic.getText());
        topicBuilder.add("user_id", topic.getUserId());
        topicBuilder.add("user_name", user.getUserName());

        return topicBuilder.build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        doGet(req, resp);
    }
}