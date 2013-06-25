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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Login</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
</head>
<body>
<div id="wrapper" class="section">
    <span id='login_username_errorloc' class='error'></span>
    <span id='login_password_errorloc' class='error'></span>
    <span class='error'><font color="#FF2B2B"><?php echo $fgmembersite->GetErrorMessage(); ?></font></span>
<!-- Form Code Start -->
<div id='fg_membersite'><br>
<form id='login' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
<fieldset >
<legend><font color="white">Donator Express Login</font></legend>
<font size="2">Welcome to the Donator Express Portal, please login or register to purchase <?php echo $virtual_currency; ?>. If you have any questions please contact us.<br>
<a href="register.php" target="_blank">Register</a> | <a href="reset-pwd-req.php" target="_blank">Lost your password?</a></p>
</font>
<input type='hidden' name='submitted' id='submitted' value='1'/>
<table border="0" frame="box">
<tr>
<div class='container'>
<td><label for='username' >MC Username:</label></td>
<td><input type='text' name='username' id='username' value='<?php echo $fgmembersite->SafeDisplay('username') ?>' maxlength="50" /></td>
</div>
</tr>
<tr>
<div class='container'>
<td><label for='password'><img src="img/secure.png"> Password:</label></td>
<td><input type='password' name='password' id='password' maxlength="50" /><br/></td>
</div>
</tr>
<tr>
<div class='container'>
<td>&nbsp;</td>
<td align="right"><input type='submit' name='Submit' value='Submit' /></td>
</div>
</tr>
</table>
</fieldset>
</form>
<!-- client-side Form Validations:
Uses the excellent form validation script from JavaScript-coder.com-->

<script type='text/javascript'>
// <![CDATA[

    var frmvalidator  = new Validator("login");
    frmvalidator.EnableOnPageErrorDisplay();
    frmvalidator.EnableMsgsTogether();

    frmvalidator.addValidation("username","req","Please provide your Minecraft Username.<br>");
    
    frmvalidator.addValidation("password","req","Please provide the password you registered with.");

// ]]>
</script>
</div><br>
&copy; <?php echo date("Y") ?> Donator Express</div>
</body>
</html>