<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:guestLayout>
    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <h1>Permission Denied</h1>
            <h3>
                You don't have a valid client certificate. Please contact your administrator to get one!
            </h3>
        </div>
    </jsp:attribute>

</template:guestLayout>