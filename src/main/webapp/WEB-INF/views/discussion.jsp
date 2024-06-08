<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.webforum.lab9web.backend.model.Topic" %>
<%@ page import="com.webforum.lab9web.backend.dao.UserDAO" %>
<%@ page import="com.webforum.lab9web.backend.model.UserComment" %>
<%@ page isELIgnored="false"%>
<% List<UserComment> comments = (List<UserComment>)request.getAttribute("commentList");
    UserDAO userDAO = new UserDAO();
    Topic topic = (Topic)request.getAttribute("topic");
    int userId = (int)session.getAttribute("user_id");
    int rejectedDelete = 0;
    if(request.getAttribute("rejected_delete") != null){
        rejectedDelete = 1;
        request.setAttribute("rejected_delete", null);
    }
%>
<html>
<head>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
    <style><%@include file="/WEB-INF/styles/topic.css"%></style>
    <title>Discussion</title>

    <script type="text/javascript">
        function clickedLogout() {
            console.log('clicked logout');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/logout';
            window.location.replace(url);
            return false;
        }

        function clickedNew() {
            console.log('clicked new');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/topics/new';
            console.log(url);
            window.location.replace(url);
            return false;
        }

        function clickedDelete(topic_id) {
            console.log('clicked delete');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/topics/delete?topic_id=' + topic_id;

            if (confirm("Are you sure you want to delete this entry?") === true)
                window.location.replace(url);

            return false;
        }

        function clickedEdit(topic_id) {
            console.log('clicked edit');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/topics/edit?topic_id=' + topic_id;
            console.log(url);
            window.location.replace(url);
            return false;
        }

        function clickedDeleteComment(comment_id) {
            console.log('clicked delete');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/comment/delete?comment_id=' + comment_id;

            if (confirm("Are you sure you want to delete this entry?") === true)
                window.location.replace(url);

            return false;
        }

        function clickedEditComment(comment_id) {
            console.log('clicked edit');
            const url = window.location.href.split('/').slice(0, -2).join('/') + '/comment/edit?comment_id=' + comment_id;
            console.log(url);
            window.location.replace(url);
            return false;
        }
    </script>
</head>
<body>
    <div class="animatedBackground" style=" margin: 0; padding: 0; top: 0;">
        <div class="forumHeader">
            <div style="width: 25%; display: flex; justify-content: space-evenly">
                <div style="align-self: center;">
                    <button class="mainButton" style="margin: 15px" onclick="clickedLogout()">Logout</button>
                    <button class="mainButton" style="margin: 15px" onclick="clickedNew()">New topic</button>

                </div>
                <div class="welcomeMessage">
                    Welcome, <%= userDAO.getUser(userId).getUserName()%>.
                </div>
            </div>

            <div style="width: 50%"></div>
            <div style="width: 25%; display: flex; justify-content: end">
                <div style="align-self: center;">
                    <button class="mainButton" style="margin: 15px" onclick="clickedDelete(<%= topic.getId()%>)">Delete topic</button>
                    <button class="mainButton" style="margin: 15px" onclick="clickedEdit(<%= topic.getId()%>)">Edit topic</button>
                </div>
            </div>
        </div>

        <div class="topicPanel" style="display:flex; flex-direction: column; align-items: center">
            <div class="topicContent" style="height: min-content; width: 80%;">
                <div>
                    <h2 style="text-align: center; margin-bottom: 2%"><%= topic.getTitle() %></h2>
                </div>
                <p style="white-space: pre-wrap;"> <%= topic.getText() %> </p>
                <h2 class="topicInfo">Posted by <%= userDAO.getUser(topic.getUserId()).getUserName() %></h2>
                <h3 class="topicInfo">At <%= topic.getUploadTime() %></h3>
            </div>

            <div class="topicCommentForm">
                <form autocomplete="off" action="<%= request.getContextPath() %>/comment/insert" method="post" id="commentForm">
                    <input type="hidden" id="action" value="comment"/>
                    <input type="hidden" name="topic_id" value="<%= topic.getId() %>" />

                    <textarea style="height: 70%; width: 60%; resize: none" id="comment_text" name="comment_text" required></textarea><br>
                    <br>

                    <input class="mainButton" type="submit" value="Post comment" />
                    <br><br>
                </form>
            </div>

            <%
                if(rejectedDelete == 1){
                    rejectedDelete = 0; %>
                <script type="text/javascript">
                    alert("You cannot delete a topic you did not post.");
                </script>
            <% }
                for(int i = 0; i < comments.size(); ++i) {
                UserComment currentComment = comments.get(i);
                if (i % 2 == 0) { %>
                    <div class="topicCommentRight">
                        <div class="topicCommentPicture">
                            <img src="${pageContext.request.contextPath}/images/default_user.svg">
                        </div>
                        <div class="topicCommentContent">
                            <p style="white-space: pre-wrap;"><%= currentComment.getText() %> </p>
                            <h3 class="topicInfo">Posted by <%= userDAO.getUser(currentComment.getUserId()).getUserName() %></h3>
                            <h4 class="topicInfo">At <%= currentComment.getUploadTime() %></h4>
                            <div style="align-self: center; text-align: end">
                                <button class="mainButton" style="margin-left: 10px" onclick="clickedDeleteComment(<%= currentComment.getId()%>)">Delete comment</button>
                                <button class="mainButton" style="margin-left: 10px" onclick="clickedEditComment(<%= currentComment.getId()%>)">Edit comment</button>
                            </div>
                        </div>
                    </div>
            <% } else {%>
                <div class="topicCommentLeft">
                    <div class="topicCommentPicture">
                        <img src="${pageContext.request.contextPath}/images/default_user.svg">
                    </div>
                    <div class="topicCommentContent">
                        <p style="white-space: pre-wrap;"><%= currentComment.getText() %> </p>
                        <h3 class="topicInfo">Posted by <%= userDAO.getUser(currentComment.getUserId()).getUserName() %></h3>
                        <h4 class="topicInfo">At <%= currentComment.getUploadTime() %></h4>
                        <div style="align-self: center; text-align: end">
                            <button class="mainButton" style="margin-left: 10px" onclick="clickedDeleteComment(<%= currentComment.getId()%>)">Delete comment</button>
                            <button class="mainButton" style="margin-left: 10px" onclick="clickedEditComment(<%= currentComment.getId()%>)">Edit comment</button>
                        </div>
                    </div>
                </div>
            <% } } %>


        </div>

        <div class="forumFooter">
            <div style="align-self: center; margin-right: 5%">
                @2024 copyright?
            </div>
        </div>
    </div>
</body>
</html>
