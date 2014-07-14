<?PHP
require_once("./include/membersite_config.php");

if(isset($_POST['submitted']))
{
   if($fgmembersite->RegisterUser())
   {
        $fgmembersite->RedirectToURL("thank-you.php");
   }
}

?>

<html lang="en">
  <head>
     <title>[Portal] Donator Express - Register an Account</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
	  <link rel="STYLESHEET" type="text/css" href="themes/pwdwidget.css" />
	
  
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>    
	  
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

<?php

if ($_POST['doVerify']) {
  $verify = recaptcha_check_answer($privkey, $_SERVER['REMOTE_ADDR'], $_POST['recaptcha_challenge_field'], $_POST['recaptcha_response_field']);
  if ($verify->is_valid) {
    # Enter Success Code
    echo "Your response was correct!";
  }
  else {
    # Enter Failure Code
    echo "You did not enter the correct words.  Please try again.";
  }
}
?>

    <span id='register_name_errorloc' class='error'></span>
    <span id='register_email_errorloc' class='error'></span>
    <span id='register_username_errorloc' class='error'></span>
    <div id='register_password_errorloc' class='error' style='clear:both'></div>
    <span class='error'><?php echo $fgmembersite->GetErrorMessage(); ?></font></span>

        <div class="row">
          <div class="col-lg-12">
            <div class="page-header">
              <h1 id="forms">Register</h1>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well" id='fg_membersite'>
              <form class="bs-example form-horizontal"  id='register' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
                <fieldset>
                  <legend><font size="3">Register for a secure account on our Donator Express portal to purchase <?php echo $virtual_currency; ?> which can be used to gain in-game ranks and items! </font></legend>
				<input type='hidden' name='submitted' id='submitted' value='1'/>
                <input type='text'  class='spmhidip' name='<?php echo $fgmembersite->GetSpamTrapInputName(); ?>' />
                  <div class="form-group">
                    <label for="inputDefault" class="col-lg-2 control-label">Name:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='name' id='name' value='<?php echo $fgmembersite->SafeDisplay('name') ?>' maxlength="50" placeholder="Full Name">
                    </div>
                  </div>
				   <div class="form-group">
                    <label for="inputDefault" class="col-lg-2 control-label">Email:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='email' id='email' value='<?php echo $fgmembersite->SafeDisplay('email') ?>' maxlength="50" placeholder="Email Address">
                    </div>
                  </div>
				   <div class="form-group">
                    <label for="inputDefault" class="col-lg-2 control-label">Username:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='username' id='username' value='<?php echo $fgmembersite->SafeDisplay('username') ?>' maxlength="50" placeholder="Minecraft Username">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Password:</label>
                    <div class="col-lg-10">
					<input type='password' class="form-control" name='password' id='password' maxlength="50" placeholder="Password" />
                    </div></div>
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
                <a href="login.php" class="list-group-item">
                  <h4 class="list-group-item-heading">Login</h4>
                  <p class="list-group-item-text">Already have an account? Click here to login to the Donator Express Portal.</p>
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
    var pwdwidget = new PasswordWidget('thepwddiv','password');
    pwdwidget.MakePWDWidget();
    
    var frmvalidator  = new Validator("register");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();
    frmvalidator.addValidation("name","req","<div class='alert alert-danger'>Please provide your Full Name<br>");

    frmvalidator.addValidation("email","req","<div class='alert alert-danger'>Please provide your Email Address<br>");

    frmvalidator.addValidation("email","email","<div class='alert alert-danger'>Please provide a valid Email Address<br>");

    frmvalidator.addValidation("username","req","<div class='alert alert-danger'>Please provide your Minecraft Username<br>");
    
    frmvalidator.addValidation("password","req","<div class='alert alert-danger'>Please provide a Password for your account");

// ]]>
</script>

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