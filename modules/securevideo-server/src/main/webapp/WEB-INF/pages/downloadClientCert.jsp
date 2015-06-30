<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:dashboardLayout>
    <jsp:attribute name="content">
        <div style="with:100%;margin:auto;text-align: center">
            <h1>Permission Denied</h1>
            <h3>
                You don't have a valid client certificate. Please download and install your client certificate first!
            </h3>
            <h3>
                You can download your client certificate here: <a href="static/cert/garret.p12">garret.p12</a>
            </h3>
            <hr/>
            <h3>
                You can follow these instructions to install your client certificate:
            </h3>
            <ul>
                <li>1. TBD</li>
                <li>2. TBD</li>
                <li>3. TBD</li>
            </ul>
        </div>
    </jsp:attribute>

</template:dashboardLayout>