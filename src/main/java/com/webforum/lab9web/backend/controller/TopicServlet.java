package com.webforum.lab9web.backend.controller;

import com.webforum.lab9web.backend.dao.TopicDAO;
import com.webforum.lab9web.backend.dao.UserCommentDAO;
import com.webforum.lab9web.backend.dao.UserDAO;
import com.webforum.lab9web.backend.model.Topic;
import com.webforum.lab9web.backend.model.User;
import com.webforum.lab9web.backend.model.UserComment;
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

@WebServlet(name = "topicServlet", value = "/topics/*")
public class TopicServlet extends HttpServlet {
    private static final long serialVersionUID = 3L;
    private final TopicDAO topicDAO = new TopicDAO();
    private final UserDAO userDAO = new UserDAO();
    private final UserCommentDAO commentDAO = new UserCommentDAO();

    public TopicServlet(){
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        System.out.println(action);

        if(action == null)
            action = "";

        System.out.println(action);
        try{
            switch(action){
                case "/insert":
                    insertTopic(req, resp);
                    break;

                case "/new":
                    showNewTopicForm(req, resp);
                    break;

                case "/delete":
                    deleteTopic(req, resp);
                    break;

                case "/update":
                    updateTopic(req, resp);
                    break;

                case "/edit":
                    showEditTopicForm(req, resp);
                    break;

                case "/view":
                    viewTopic(req, resp);
                    break;

                default:
                    getAllTopics(req, resp);
                    break;
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void getAllTopics(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/topics");
        }

        List<Topic> topicList = null;

        try{
            topicList = topicDAO.getAllTopics();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        req.setAttribute("topicList", topicList);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/topics.jsp");
        dispatcher.forward(req, resp);
    }

    private void viewTopic(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
        }

        Topic topic = null;
        List<UserComment> comments = null;
        int topicId = Integer.parseInt(req.getParameter("topic_id"));

        try{
            comments = commentDAO.getAllCommentsByTopic(topicId);
            topic = topicDAO.getTopic(topicId);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        req.setAttribute("commentList", comments);
        req.setAttribute("topic", topic);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion.jsp");
        dispatcher.forward(req, resp);
    }

    protected void showNewTopicForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/create_topic.jsp");

        if(session.getAttribute("user_id") == null){
            dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        }

        dispatcher.forward(req, resp);
    }

    protected void showEditTopicForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/edit_topic.jsp");

        if(session.getAttribute("user_id") == null){
            dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        }
        
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        int userId = (int)session.getAttribute("user_id");
        Topic topic = new Topic();

        try{
            topic = topicDAO.getTopic(topicId);

            if(topic.getUserId() != userId)
                topic = new Topic();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        req.setAttribute("topic", topic);

        dispatcher.forward(req, resp);
    }

    private void updateTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        int userId = (int)session.getAttribute("user_id");

        String topicText = req.getParameter("topic_text");
        String topicTitle = req.getParameter("topic_title");
        int topicId = Integer.parseInt(req.getParameter("topic_id"));

        try{
            Topic topic = topicDAO.getTopic(topicId);
            topic.setText(topicText);
            topic.setTitle(topicTitle);

            if(topic.getUserId() == userId)
                topicDAO.updateTopic(topic);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/topics");
    }

    private void insertTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        int userId = (int)session.getAttribute("user_id");

        String topicText = req.getParameter("topic_text");
        String topicTitle = req.getParameter("topic_title");

        try{
            Topic topic = new Topic();
            topic.setUserId(userId);
            topic.setText(topicText);
            topic.setTitle(topicTitle);

            topicDAO.registerTopic(topic);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/topics");
    }

    private void deleteTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        int userId = (int)session.getAttribute("user_id");
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        int status = 0;

        try {
            Topic topic = topicDAO.getTopic(topicId);

            if(topic.getId() != -1) {
                if (topic.getUserId() == userId) {
                    topicDAO.deleteTopic(topicId);
                    resp.sendRedirect(req.getContextPath() + "/topics");

                }else throw new Exception("User is not owner of topic.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

            resp.sendRedirect(req.getContextPath() + "/topics/view?topic_id=" + topicId);
            req.setAttribute("rejected_delete", 1);
        }

//        resp.sendRedirect("topics");
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