<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:guestLayout>

    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <h1>Access Denied!</h1>
            <p>We are sorry that you didn't grant the access authority for Secure Video.</p>
            <p><a class="btn btn-primary btn-lg" href="/" role="button">Go back</a></p>
        </div>
    </jsp:attribute>

</template:guestLayout>