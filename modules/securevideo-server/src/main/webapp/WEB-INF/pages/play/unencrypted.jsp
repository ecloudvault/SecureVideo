<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:dashboardLayout>
    <jsp:attribute name="content">
        <ul>
            <li><a href="/play/unencrypted">Unencrypted WebM</a></li>
            <li><a href="/play/encrypted">Encrypted WebM(Ourself)</a></li>
            <li><a href="/play/encryptedwebm">Encrypted WebM(From online)</a></li>
        </ul>
      <div style="with:100%;margin:auto;text-align: center;height:100%;">
          <h2 class="text-center">Unencrypted Video (WebM)</h2>
          <div class="text-center">
              <video id="encryptedVideo" width="480" height="270" controls autoplay>
                  <source src="<c:url value='/video/BigBuckBunny_320x180.webm'/>" type="video/webm">
              </video>
          </div>
      </div>
        <script>
            var key = {
                kty: 'oct',
                alg: 'A128KW',
                kid: 'MDEyMzQ1Njc4OTAxMjM0NQ',
                k: '691i8WgU0nto7xIq_OSuPA'
            };
        </script>
        <script type="text/javascript" src="<c:url value='/static/js/index.js'/>"></script>
    </jsp:attribute>

</template:dashboardLayout>