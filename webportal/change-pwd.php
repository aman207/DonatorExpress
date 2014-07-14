<?PHP
require_once("./include/membersite_config.php");

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}

if(isset($_POST['submitted']))
{
   if($fgmembersite->ChangePassword())
   {
        $fgmembersite->RedirectToURL("changed-pwd.php");
   }
}

?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Change Password</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
	
  
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>  
      <link rel="STYLESHEET" type="text/css" href="style/pwdwidget.css" />
	  
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
    <span id='changepwd_oldpwd_errorloc' class='error'></span>
    <span id='changepwd_newpwd_errorloc' class='error'></span>
    <span class='error'><?php echo $fgmembersite->GetErrorMessage(); ?></span>
	
	        <div class="row">
          <div class="col-lg-12">
            <div class="page-header">
              <h1 id="forms">Change Password</h1>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well" id='fg_membersite'>
              <form class="bs-example form-horizontal" id='changepwd' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
                <fieldset>
                  <legend><font size="3">Securely and easily manage your account login.</font></legend>
				<input type='hidden' name='submitted' id='submitted' value='1'/>
                  <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Current Password:</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" name='oldpwd' id='oldpwd' maxlength="50" placeholder="Current Password">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPassword" class="col-lg-2 control-label">Requested Password:</label>
                    <div class="col-lg-10">
                      <input type="password" class="form-control" name='newpwd' id='newpwd' maxlength="50" placeholder="New Password">
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

<!-- client-side Form Validations:
Uses the excellent form validation script from JavaScript-coder.com-->

<script type='text/javascript'>
// <![CDATA[
    var pwdwidget = new PasswordWidget('oldpwddiv','oldpwd');
    pwdwidget.enableGenerate = false;
    pwdwidget.enableShowStrength=false;
    pwdwidget.enableShowStrengthStr =false;
    pwdwidget.MakePWDWidget();
    
    var pwdwidget = new PasswordWidget('newpwddiv','newpwd');
    pwdwidget.MakePWDWidget();
    
    
    var frmvalidator  = new Validator("changepwd");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();

    frmvalidator.addValidation("oldpwd","req","Please provide your current password!<br>");
    
    frmvalidator.addValidation("newpwd","req","Please provide your new password!");

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