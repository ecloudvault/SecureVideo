<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:guestLayout>
    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <form class="form-signin" method="post" action="/login">
                <h2 class="form-signin-heading">User Login</h2>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">Login Failed!
                        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                        </c:if>
                    </div>
                </c:if>
                <label for="username" class="sr-only">Username</label>
                <input id="username" name="username" class="form-control" placeholder="Please type your username" required autofocus></br>
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Please input your password" required></br>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
                <hr/>
                <a href="/signin/waad">
                    <img src="/static/img/waad_30x30.png"> Login with WAAD
                </a>
            </form>
        </div>
    </jsp:attribute>

</template:guestLayout>