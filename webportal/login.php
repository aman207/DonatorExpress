<?PHP
require_once("./include/membersite_config.php");

if(isset($_POST['submitted']))
{
   if($fgmembersite->Login())
   {
        $fgmembersite->RedirectToURL("login-home.php");
   }
}

?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Login</title>
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
          </ul>

        </div>
      </div>
    </div>
	<?php if ($theme != "simple") { echo "<br><br><br>"; } ?>
    <div class="container">
                <span id='login_username_errorloc' class='error'></span>
				<span id='login_password_errorloc' class='error'></span>
				<span class='error'>
				<?php echo $fgmembersite->GetErrorMessage(); ?>
		


	
        <div class="row">
          <div class="col-lg-12">
            <div class="page-header">
              <h1 id="forms">Login</h1>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well" id='fg_membersite'>
              <form class="bs-example form-horizontal" id='login' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
                <fieldset>
                  <legend><font size="3">Welcome to the Donator Express Portal, please login or register to purchase <?php echo $virtual_currency; ?>. If you have any questions please contact us.</font></legend>
				<input type='hidden' name='submitted' id='submitted' value='1'/>
                  <div class="form-group">
                    <label for="inputEmail" class="col-lg-2 control-label">Username:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='username' id='username' value='<?php echo $fgmembersite->SafeDisplay('username') ?>' maxlength="50" placeholder="Minecraft Username">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Password:</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" name='password' id='password' maxlength="50" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button type="submit" class="btn btn-primary" name='Submit'>Submit</button> 
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
          </div>
		  
	<div class="col-lg-4">
            <div class="bs-example">
              <div class="list-group">
                <a href="register.php" class="list-group-item">
                  <h4 class="list-group-item-heading">Register</h4>
                  <p class="list-group-item-text">Click here to register for an account on the Donator Express Portal, purchase  <?php echo $virtual_currency; ?> to obtain in-game ranks and items easily!</p>
                </a>
                <a href="reset-pwd-req.php" class="list-group-item">
                  <h4 class="list-group-item-heading">Lost your password?</h4>
                  <p class="list-group-item-text">Easily send yourself a password reset email!</p>
                </a>
              </div>
            </div>
          </div>
	
<!-- client-side Form Validations:
Uses the excellent form validation script from JavaScript-coder.com-->

<script type='text/javascript'>
// <![CDATA[

    var frmvalidator  = new Validator("login");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();

    frmvalidator.addValidation("username","req","<div class='alert alert-danger'>Please provide your Minecraft Username.<br>");
    
    frmvalidator.addValidation("password","req","<div class='alert alert-danger'>Please provide the password you registered with.");

// ]]>
</script>

      <footer>
        <div class="row">
          <div class="col-lg-12">
		  		  <?php if ($enable_contact == "false") { } else { echo '<ul class="list-unstyled"><li class="pull-right"><a href="contact.php">Administrative Contact</a></li></ul>'; }?>
		  <!--Please keep the Copyright footer intact as per the license agreement this software is released on-->
            <p>&nbsp; &nbsp; &copy; <?php echo date("Y") ?> Donator Express</p>
			
			<p>
			<?php 
			    /* Tracker helps the developers of Donator Express to improve the plugin and portal by tracking common
					user statistics such as operating system and web browser, please keep as-is to support development.   */
			echo '<img src="http://zogo.pw/ckc/tracker.png">'; ?>
			</p>
			
          </div>
        </div>
        
      </footer>
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="scripts/bootstrap.min.js"></script>
    <script src="scripts/bootswatch.js"></script>
</body>
</html>