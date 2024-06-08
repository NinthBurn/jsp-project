<html>
<head>
    <style><%@include file="/WEB-INF/styles/cool_background.css"%></style>
    <style><%@include file="/WEB-INF/styles/main.css"%></style>
    <style><%@include file="/WEB-INF/styles/topic.css"%></style>

    <title>Create topic</title>
</head>
<body>
    <div class="animatedBackground">
        <div class="loginPanel" style="width: 50vw; height: 50vh; min-width: max-content;min-height: max-content;">
            <div class="loginForm" style="width: 100%; height: 100%">
                <h1>Edit topic</h1>
                <form autocomplete="off" action="<%= request.getContextPath() %>/topics/insert" method="post" id="editTopicForm" >
                    <input type="hidden" id="action" value="login"/>

                    <label for="topic_title">Topic title:</label><br><br>
                    <input id="topic_title" type="text" name="topic_title" required /><br>
                    <br>

                    <label for="topic_text">Content:</label><br><br>
                    <textarea style="resize: none;width: 60%;height: 50%;" id="topic_text" name="topic_text" required></textarea><br>
                    <br>

                    <input type="submit" value="Create" />
                    <br><br>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
