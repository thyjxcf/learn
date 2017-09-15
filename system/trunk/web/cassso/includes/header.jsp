<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta charset="utf-8">
    <title>用户服务单点登录示例</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<script type="text/javascript" src="_assets/js/thirdparty/jquery/2.0.3/jquery.js"></script>
	<script type="text/javascript" src="_assets/js/thirdparty/jquery/plugins/jquery.json-2.2.min.js"></script>
	<script type="text/javascript" src="_assets/js/thirdparty/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="_assets/js/thirdparty/utbrowser/browserContext.js"></script>
    <!-- Le styles -->
    <link href="_assets/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="_assets/css/casdemo.css">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      h1 {
        font-family: Arial,微软雅黑;
      }
    </style>
    <link href="_assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="_assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="_assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="_assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="_assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="_assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="_assets/ico/favicon.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">畅言用户服务</a>
          <div class="nav-collapse collapse">
            <!-- <ul class="nav">
              <li><a href="../index.html">首页</a></li>
              <li><a href="../php.html">PHP 演示</a></li>
              <li class="active"><a href="../java.html">Java 演示</a></li>
              <li><a href="../dotnet.html">ASP.net 演示</a></li>
              <li><a href="../delphi.html">Delphi 演示</a></li>
            </ul> -->
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
