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
        $fgmembersite->RedirectToURL("changed-pwd.html");
   }
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Change Password</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <link rel="STYLESHEET" type="text/css" href="style/pwdwidget.css" />
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>       
</head>
<body>
<div id="wrapper" class="section">
    <span id='changepwd_oldpwd_errorloc' class='error'></span>
    <span id='changepwd_newpwd_errorloc' class='error'></span>
    <span class='error'><font color="#FF2B2B"><?php echo $fgmembersite->GetErrorMessage(); ?></span></font>
<!-- Form Code Start -->
<div id='fg_membersite'><br>
<form id='changepwd' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
<fieldset >
<legend><font color="white">Change Password</font></legend>
Securely and easily manage your account login. | <a href='login-home.php'>Members Area</a><br><br>
<input type='hidden' name='submitted' id='submitted' value='1'/>


<div class='container'>
    <label for='oldpwd' >Current Password:</label><br/>
    <div class='pwdwidgetdiv' id='oldpwddiv' ></div><br/>
    <noscript>
    <input type='password' name='oldpwd' id='oldpwd' maxlength="50" />
    </noscript>    
</div>

<div class='container'>
    <label for='newpwd' >New Password:</label><br/>
    <div class='pwdwidgetdiv' id='newpwddiv' ></div>
    <noscript>
    <input type='password' name='newpwd' id='newpwd' maxlength="50" />
    </noscript>
</div>

<br><br>
<div class='container'>
    <input type='submit' name='Submit' value='Submit' />
</div>

</fieldset>
</form>
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
</div><br>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>