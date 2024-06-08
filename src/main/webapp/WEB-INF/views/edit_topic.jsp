<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.webforum.lab9web.backend.model.Topic" %>
<%@ page isELIgnored="false"%>
<% Topic topic = (Topic)request.getAttribute("topic");
    request.setAttribute("topic", topic);
%>
<html>
<head>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
    <style><%@include file="/WEB-INF/styles/topic.css"%></style>

    <title>Edit topic</title>
</head>
<body>
    <% if(topic.getId() == -1) { %>
        <script type="text/javascript">
            function navigateToTopics(){
                const url = window.location.href.split('/').slice(0,-1).join('/')
                window.location.replace(url);
            }

            alert("You cannot edit a topic you have not posted.")
            navigateToTopics();
        </script>
    <% } else { %>
    <div class="animatedBackground">
        <div class="loginPanel" style="width: 50vw; height: 50vh; min-width: max-content;min-height: max-content;">
            <div class="loginForm" style="width: 100%; height: 100%">
                <h1>Edit topic</h1>
                <form autocomplete="off" action="<%= request.getContextPath() %>/topics/update" method="post" id="editTopicForm" >
                    <input type="hidden" id="action" value="login"/>
                    <input type="hidden" name="topic_id" value="<%= topic.getId() %>" />

                    <label for="topic_title">Topic title:</label><br><br>
                    <input id="topic_title" type="text" name="topic_title" required value="<%= topic.getTitle()%>"/><br>
                    <br>

                    <label for="topic_text">Content:</label><br><br>
                    <textarea style="resize: none;width: 60%;height: 50%;" id="topic_text" name="topic_text" required><%= topic.getText() %></textarea><br>
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
