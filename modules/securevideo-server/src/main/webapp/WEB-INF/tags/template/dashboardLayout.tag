<%@tag description="guest pages layout" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@attribute name="content" fragment="true" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Secure Video</title>

    <link type="text/css" rel="stylesheet" href="<c:url value='/static/lib/bootstrap/dist/css/bootstrap.min.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/lib/font-awesome/css/font-awesome.min.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/lib/admin-lte/dist/css/AdminLTE.min.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/lib/admin-lte/dist/css/skins/_all-skins.min.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/lib/iCheck/square/blue.css'/>"/>
    <link type="text/css" rel="stylesheet" href="<c:url value='/static/css/custom.css'/>"/>

    <script type="text/javascript" src="<c:url value='/static/lib/jquery/dist/jquery.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/lib/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/lib/iCheck/icheck.min.js'/>"></script>

    <!--[if lt IE 9]>
    <script type="text/javascript" src="<c:url value='/static/lib/html5shiv.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/static/lib/respond.min.js'/>"></script>
    <![endif]-->
</head>

<body class="skin-blue fixed login-page">
<input type="hidden" id="_currentNamespace" name="_currentNamespace" value="${requestScope._currentTenant.namespace}"/>

<div class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="<c:url value='/' />" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>A</b>LT</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>SecureVideo</b></span>
        </a>

        <div class="errorPanel" id="errorPanel">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong id="errormsg">Error!</strong>
            </div>
        </div>

        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->
                    <li class="dropdown messages-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-envelope-o"></i>
                            <span class="label label-success">4</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 4 messages</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li><!-- start message -->
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="/static/img/user2-160x160.jpg" class="img-circle" alt="User Image"/>
                                            </div>
                                            <h4>
                                                Support Team
                                                <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                    <!-- end message -->

                                </ul>
                            </li>
                            <li class="footer"><a href="#">See All Messages</a></li>
                        </ul>
                    </li>
                    <!-- Notifications: style can be found in dropdown.less -->
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">10</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 10 notifications</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                        </a>
                                    </li>

                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>
                    </li>

                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="/static/img/user2-160x160.jpg" class="user-image" alt="User Image"/>
                            <span class="hidden-xs"><s:authentication property="name"/></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="/static/img/user2-160x160.jpg" class="img-circle" alt="User Image"/>

                                <p>
                                    Alexander Pierce - Web Developer
                                    <small>Member since Nov. 2012</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="col-xs-4 text-center">
                                    <a href="#">Followers</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Sales</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Friends</a>
                                </div>
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a href="/logout" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel -->


            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul id="leftmenu" class="sidebar-menu">
                <li class="header">MAIN NAVIGATION</li>

                <li class="treeview active" id="list">
                    <a href="<c:url value='/video/list'/>">
                        <i class="fa fa-video-camera"></i>
                        <span>Video List</span>
                    </a>
                </li>
                <li class="treeview" id="key">
                    <a href="<c:url value='/key/pemKey'/>">
                        <i class="fa fa-lock"></i>
                        <span>User Key</span>
                    </a>
                </li>

            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <script>function setActive(obj) {
            var clickLi = obj;
            $("#leftmenu").find('li').each(function () {
                if ($(this).attr("class").indexOf("active") != -1) {
                    $(this).attr("class", "treeview")
                }
            });

            clickLi.attr("class", "treeview active");
        }</script>
        <jsp:invoke fragment="content"/>
        <!-- Main content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer text-center">

        <strong>Copyright &copy; 2014-2015 SecureVideo.</strong> All rights reserved.
    </footer>
</div>
<!-- ./wrapper -->

</div>


<script>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
//
        <!--left menu active-->


    });
</script>

</body>
</html>