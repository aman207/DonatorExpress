<?PHP
require_once("./include/membersite_config.php");

if(isset($_POST['submitted']))
{
   if($fgmembersite->RegisterUser())
   {
        $fgmembersite->RedirectToURL("thank-you.html");
   }
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Register an Account</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <link rel="STYLESHEET" type="text/css" href="style/pwdwidget.css" />
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>       
</head>
<body>
<div id="wrapper" class="section">
    <span id='register_name_errorloc' class='error'></span>
    <span id='register_email_errorloc' class='error'></span>
    <span id='register_username_errorloc' class='error'></span>
    <div id='register_password_errorloc' class='error' style='clear:both'></div>
    <span class='error'><font color="#FF2B2B"><?php echo $fgmembersite->GetErrorMessage(); ?></font></span>

<!-- Form Code Start -->
<div id='fg_membersite'><br>
<form id='register' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
<fieldset >
<legend><font color="white">Register an Account</font></legend>
Register for a secure account on our Donator Express portal to purchase <?php echo $virtual_currency; ?> which can be used gain in-game ranks and items!<br><br>
<input type='hidden' name='submitted' id='submitted' value='1'/>

<input type='text'  class='spmhidip' name='<?php echo $fgmembersite->GetSpamTrapInputName(); ?>' />

<div class='container'>
    <label for='name' >Full Name: </label><br/>
    <input type='text' name='name' id='name' value='<?php echo $fgmembersite->SafeDisplay('name') ?>' maxlength="50" /><br/>
</div>
<div class='container'>
    <label for='email' >Email Address:</label><br/>
    <input type='text' name='email' id='email' value='<?php echo $fgmembersite->SafeDisplay('email') ?>' maxlength="50" /><br/>
</div>
<div class='container'>
    <label for='username' >Minecraft Username:</label><br/>
    <input type='text' name='username' id='username' value='<?php echo $fgmembersite->SafeDisplay('username') ?>' maxlength="50" /><br/>
</div>
<div class='container' style='height:80px;'>
    <label for='password' ><img src="img/secure.png"> Password:</label><br/>
    <div class='pwdwidgetdiv' id='thepwddiv' ></div>
    <noscript>
    <input type='password' name='password' id='password' maxlength="50" />
    </noscript>    
</div>
<br>
<div class='container'>
    <input type='submit' name='Submit' value='Submit' />
</div>

</fieldset>
</form>
<!-- client-side Form Validations:
Uses the excellent form validation script from JavaScript-coder.com-->

<script type='text/javascript'>
// <![CDATA[
    var pwdwidget = new PasswordWidget('thepwddiv','password');
    pwdwidget.MakePWDWidget();
    
    var frmvalidator  = new Validator("register");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();
    frmvalidator.addValidation("name","req","Please provide your Full Name<br>");

    frmvalidator.addValidation("email","req","Please provide your Email Address<br>");

    frmvalidator.addValidation("email","email","Please provide a valid Email Address<br>");

    frmvalidator.addValidation("username","req","Please provide your Minecraft Username<br>");
    
    frmvalidator.addValidation("password","req","Please provide a Password for your account");

// ]]>
</script>

<br>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>