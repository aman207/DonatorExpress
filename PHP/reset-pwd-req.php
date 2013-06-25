<?PHP
require_once("./include/membersite_config.php");

$emailsent = false;
if(isset($_POST['submitted']))
{
   if($fgmembersite->EmailResetPasswordLink())
   {
        $fgmembersite->RedirectToURL("reset-pwd-link-sent.html");
        exit;
   }
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Reset Password</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <link rel="STYLESHEET" type="text/css" href="style/pwdwidget.css" />
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>       
</head>
<body>
<div id="wrapper" class="section">
    <span id='resetreq_email_errorloc' class='error'></span>
    <span class='error'><font color="#FF2B2B"><?php echo $fgmembersite->GetErrorMessage(); ?></font></span>
<!-- Form Code Start -->
<div id='fg_membersite'><br>
<form id='resetreq' action='<?php echo $fgmembersite->GetSelfScript(); ?>' method='post' accept-charset='UTF-8'>
<fieldset >
<legend><font color="white">Reset Password</font></legend>

<input type='hidden' name='submitted' id='submitted' value='1'/>

<div class='container'>
    <label for='username' >Email Address:</label><br/>
    <input type='text' name='email' id='email' value='<?php echo $fgmembersite->SafeDisplay('email') ?>' maxlength="50" /><br/>
</div>
<div class='short_explanation'>A link to reset your password will be sent to the email address you have registered your account with.</div><br>
<div class='container'>
    <input type='submit' name='Submit' value='Submit' />
</div>

</fieldset>
</form>
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
<br>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>