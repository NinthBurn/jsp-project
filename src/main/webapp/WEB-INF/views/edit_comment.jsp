<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.webforum.lab9web.backend.model.UserComment" %>
<%@ page isELIgnored="false"%>
<% UserComment comment = (UserComment) request.getAttribute("comment");
    request.setAttribute("comment", comment);
%>
<html>
<head>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
    <style><%@include file="/WEB-INF/styles/topic.css"%></style>

    <title>Edit comment</title>
</head>
<body>
<% if(comment.getId() == -1) { %>
<script type="text/javascript">
    function navigateToTopics(){
        const url = window.location.href.split('/').slice(0,-2).join('/') + "/topics"
        window.location.replace(url);
    }

    alert("You cannot edit a comment you have not posted.")
    navigateToTopics();
</script>
<% } else { %>
<div class="animatedBackground">
    <div class="loginPanel" style="width: 50vw; height: 50vh; min-width: max-content;min-height: max-content;">
        <div class="loginForm" style="width: 100%; height: 100%">
            <h1>Edit comment</h1>
            <form autocomplete="off" action="<%= request.getContextPath() %>/comment/update" method="post" id="editTopicForm" >
                <input type="hidden" id="action" value="login"/>
                <input type="hidden" name="comment_id" value="<%= comment.getId() %>" />
                <input type="hidden" name="topic_id" value="<%= comment.getTopicId() %>" />

                <label for="topic_text">Content:</label><br><br>
                <textarea style="resize: none;width: 60%;height: 50%;" id="topic_text" name="topic_text" required><%= comment.getText() %></textarea><br>
                <br>

                <input type="submit" value="Save" />
                <br><br>
            </form>
        </div>
    </div>
</div>
<% } %>
</body>
</html>
