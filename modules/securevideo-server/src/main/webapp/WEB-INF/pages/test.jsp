<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:dashboardLayout>
    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <form class="form-signin" method="get" action="<c:url value='/hello'/>">
                <h2 class="form-signin-heading">Test AJAX</h2>
                <input type="name" id="name" name="password" class="form-control" placeholder="Please input your name" required>
                <button class="btn btn-lg btn-primary btn-block" type="button" id="callBtn">Call</button>
            </form>

            <!--
            <iframe src="https://securevideo.lan:8443" width="100%" height="200"></iframe>
            -->
        </div>

        <script type="text/javascript">
            $(document).ready(function(){
                $('#callBtn').bind('click', callAjax);
            });

            function callAjax() {
                $.ajax({
                    type: 'GET',
                    url: 'https://securevideo.lan:8443/hello',
                    data: 'name=' + $('#name').val(),
                    success: function(msg){
                        alert(msg.greeting);
                    }
                });
            }
        </script>

    </jsp:attribute>

</template:dashboardLayout>