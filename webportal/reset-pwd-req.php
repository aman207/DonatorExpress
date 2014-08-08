<?PHP
require_once("./include/membersite_config.php");

$emailsent = false;
if(isset($_POST['submitted']))
{
   if($fgmembersite->EmailResetPasswordLink())
   {
        $fgmembersite->RedirectToURL("reset-pwd-link-sent.php");
        exit;
   }
}

?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Reset Password</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
      <link rel="stylesheet" href="themes/pwdwidget.css" type="text/css" media="screen">
	
  
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
	
    <span id='resetreq_email_errorloc' class='error'></span>
    <span class='error'><?php echo $fgmembersite->GetErrorMessage(); ?></span>
	
	        <div class="row">
          <div class="col-lg-12">
            <div class="page-header">
              <h1 id="forms">Reset Password</h1>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well" id='fg_membersite'>
              <form class="bs-example form-horizontal" id='resetreq' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
                <fieldset>
                  <legend><font size="3">A link to reset your password will be sent to the email address you have registered your account with.</font></legend>
				<input type='hidden' name='submitted' id='submitted' value='1'/>
                  <div class="form-group">
                    <label for="inputEmail" class="col-lg-2 control-label">Email:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='email' id='email' value='<?php echo $fgmembersite->SafeDisplay('email') ?>' maxlength="50" placeholder="Email Address">
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
				<a href="login.php" class="list-group-item">
				<h4 class="list-group-item-heading">Login</h4>
				<p class="list-group-item-text">Already have an account? Click here to login to the Donator Express Portal.</p>
				</a>
				<a href="register.php" class="list-group-item">
				<h4 class="list-group-item-heading">Register</h4>
				<p class="list-group-item-text">Click here to register for an account on the Donator Express Portal, purchase  <?php echo $virtual_currency; ?> to obtain in-game ranks and items easily!</p>
				</a>
				</div>
			</div>
		</div>
	
<!-- client-side Form Validations:
Uses the excellent form validation script from JavaScript-coder.com-->

<script type='text/javascript'>
// <![CDATA[

    var frmvalidator  = new Validator("resetreq");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();

    frmvalidator.addValidation("email","req","Please provide the email address used to sign-up<br>");
    frmvalidator.addValidation("email","email","Please provide the email address used to sign-up");

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