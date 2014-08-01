<?PHP
require_once("./check_admin.php");
require_once("./include/membersite_config.php");

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}
?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Password Updated!</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
	
  
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
	  
 <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="scripts/html5shiv.js"></script>
      <script src="scripts/respond.min.js"></script>
    <![endif]-->
	
</head>
<body>


    <div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <?php echo '<a href="' . $dep_url . '" class="navbar-brand">Donator Express</a>'; ?>
        </div>
        <div class="navbar-collapse collapse" id="navbar-main">
          <ul class="nav navbar-nav navbar-right">
            <?php echo '<li><a href="' . $website_url . '" target="_blank">' . $website_name . '</a></li>' ?>
		<li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Main Menu <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="login-home.php">Members Area</a></li>
          <li><a href="logout.php">Logout</a></li>
		  <?php if ($is_admin == "true") { echo "<li class=\"divider\"></li><li><a href=\"admin.php\">Admin Control Panel</a></li>"; } ?>
        </ul>
		</li>
          </ul>

        </div>
      </div>
    </div>
		<?php if ($theme != "simple") { echo "<br><br><br>"; } ?>
    <div class="container">
	
	<div class="row">
          <div class="col-lg-12">
            <div class="page-header">
              <h2 id="container">Password Updated!</h2>
            </div>
            <div class="bs-example">
              <div class="jumbotron">
                <p>You have successfully updated your account password!</p>
                <p><a class="btn btn-primary btn-lg" href="login-home.php">Members Area</a></p>
              </div>
            </div>
          </div>
        </div>

<footer>
        <div class="row">
          <div class="col-lg-12">
		  		  <?php if ($enable_contact == "false") { } else { echo '<ul class="list-unstyled"><li class="pull-right"><a href="contact.php">Administrative Contact</a></li></ul>'; }?>
		  <!--Please keep the Copyright footer intact as per the license agreement this software is released on-->
            <p>&nbsp; &nbsp; &copy; <?php echo date("Y") ?> Donator Express</p>
          </div>
        </div>
        
      </footer>
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="scripts/bootstrap.min.js"></script>
    <script src="scripts/bootswatch.js"></script>
</body>
</html>