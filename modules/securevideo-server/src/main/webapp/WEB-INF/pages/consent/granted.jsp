<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:guestLayout>

    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <h1>Access Granted.</h1>
            <p>Congraturations! The users in your Azure Active Directory can sign in Secure Video now.</p>
            <p><a class="btn btn-primary btn-lg" href="/login" role="button">Continue</a></p>
        </div>
    </jsp:attribute>

</template:guestLayout>