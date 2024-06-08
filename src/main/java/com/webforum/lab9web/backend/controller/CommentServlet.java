package com.webforum.lab9web.backend.controller;

import com.webforum.lab9web.backend.dao.TopicDAO;
import com.webforum.lab9web.backend.dao.UserCommentDAO;
import com.webforum.lab9web.backend.dao.UserDAO;
import com.webforum.lab9web.backend.model.Topic;
import com.webforum.lab9web.backend.model.UserComment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "commentServlet", value = "/comment/*")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 5L;
    private final TopicDAO topicDAO = new TopicDAO();
    private final UserDAO userDAO = new UserDAO();
    private final UserCommentDAO commentDAO = new UserCommentDAO();

    public CommentServlet(){
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
                    insertComment(req, resp);
                    break;

                case "/delete":
                    deleteComment(req, resp);
                    break;

                case "/update":
                    updateComment(req, resp);
                    break;

                case "/edit":
                    showEditCommentForm(req, resp);
                    break;

                default:
                    break;
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
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

    private void insertComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
        }

        int userId = (int)session.getAttribute("user_id");
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        String commentText = req.getParameter("comment_text");

        try{
            UserComment comment = new UserComment();
            comment.setTopicId(topicId);
            comment.setUserId(userId);
            comment.setText(commentText);
            commentDAO.registerComment(comment);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/topics/view?topic_id=" + topicId);
    }

    private void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);
        }

        int userId = (int)session.getAttribute("user_id");
        int topicId = 0;
        int commentId = Integer.parseInt(req.getParameter("comment_id"));

        try {
            UserComment comment = commentDAO.getComment(commentId);

            if(comment.getId() != -1) {
                topicId = comment.getTopicId();
                if (comment.getUserId() == userId) {
                    commentDAO.deleteComment(commentId);

                }else throw new Exception("User is not owner of topic.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

            req.setAttribute("rejected_delete", 1);
        }

        resp.sendRedirect(req.getContextPath() + "/topics/view?topic_id=" + topicId);
    }

    private void showEditCommentForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/edit_comment.jsp");

        if(session.getAttribute("user_id") == null){
            dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        }

        int commentId = Integer.parseInt(req.getParameter("comment_id"));
        int userId = (int)session.getAttribute("user_id");

        UserComment comment = new UserComment();

        try{
            comment = commentDAO.getComment(commentId);

            if(comment.getUserId() != userId)
                comment = new UserComment();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        req.setAttribute("comment", comment);

        dispatcher.forward(req, resp);
    }

    private void updateComment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") == null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(req, resp);

            return;
        }

        int userId = (int)session.getAttribute("user_id");

        String topicText = req.getParameter("topic_text");
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        int commentId = Integer.parseInt(req.getParameter("comment_id"));

        try{
            UserComment comment = commentDAO.getComment(commentId);
            comment.setText(topicText);

            if(comment.getUserId() == userId)
                commentDAO.updateComment(comment);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/topics/view?topic_id=" + topicId);
    }
}
