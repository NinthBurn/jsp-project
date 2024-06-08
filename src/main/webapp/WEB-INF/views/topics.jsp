<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.webforum.lab9web.backend.model.Topic" %>
<%@ page import="com.webforum.lab9web.backend.dao.UserDAO" %>
<%@ page isELIgnored="false"%>
<% List<Topic> topics = (List<Topic>)request.getAttribute("topicList");
    UserDAO userDAO = new UserDAO();
    int userId = (int)session.getAttribute("user_id");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Topics</title>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
    <style><%@include file="/WEB-INF/styles/topic.css"%></style>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/topic.js" defer></script>
</head>
<body style="height: 100vh; width: 100vw;">
    <script type="text/javascript">
        function clickedLogout() {
            console.log('clicked logout');
            const url = window.location.href.split('/').slice(0, -1).join('/') + '/logout'
            window.location.replace(url);
            return false;
        }

        function clickedNew() {
            console.log('clicked new');
            const url = window.location.href.split('/').slice(0, -1).join('/') + '/topics/new'
            console.log(url)
            window.location.replace(url);
            return false;
        }
    </script>
    <div class="animatedBackground" style=" margin: 0; padding: 0; top: 0;">
        <div class="forumHeader">
            <div style="width: 25%; display: flex; justify-content: space-evenly">
                <div style="align-self: center;">
                    <button class="mainButton" style="margin: 15px" onclick="clickedLogout()">Logout</button>
                    <button class="mainButton" style="margin: 15px" onclick="clickedNew()">New topic</button>

                </div>
                <div class="welcomeMessage">
                    Welcome, <%= userDAO.getUser(userId).getUserName() %>.
                </div>
            </div>

            <div style="width: 75%"></div>
        </div>

        <div class="topicPanel">
            <% for(Topic topic : topics) { %>
                <div class="topicContainer">
                    <div class="topicPresentation">
                        <div class="topicDetails">
                            <div class="topicDetailsElement">
                              <%= userDAO.getUser(topic.getUserId()).getUserName() %>
                            </div>
                            <div class="topicDetailsElement">
                                <%= topic.getUploadTime() %>
                            </div>
                        </div>
                        <div>
                          <h3 onclick="clickedTopicTitle(<%= topic.getId() %>)" class="topicLink"><%= topic.getTitle() %></h3>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>

        <div class="forumFooter">
            <div style="align-self: center; margin-right: 5%">
                @2024 copyright?
            </div>
        </div>
    </div>
</body>
</html>
