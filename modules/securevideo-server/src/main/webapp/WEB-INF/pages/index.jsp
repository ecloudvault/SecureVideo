<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:guestLayout>
    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <h1>Secure Video Demonstration</h1>
            <p><a class="btn btn-primary btn-lg" href="/video/list" role="button">Enter Demo</a></p>
            <br/><br/>
            <p>If you are an administrator of an Azure Active Directory, you can grant access for Secure Video by clicking the button below.</p>
            <p><a class="btn btn-default btn-lg" href="/consent" role="button">Grant Access</a></p>
        </div>
    </jsp:attribute>

</template:guestLayout>