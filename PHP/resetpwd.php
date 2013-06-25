<?PHP
require_once("./include/membersite_config.php");

$success = false;
if($fgmembersite->ResetPassword())
{
    $success=true;
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Password Reset</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
      <link rel="STYLESHEET" type="text/css" href="style/pwdwidget.css" />
      <script src="scripts/pwdwidget.js" type="text/javascript"></script>       
</head>
<body>
<div id="wrapper" class="section">

<?php
if($success){
?>
<h2><font color="white">Password has been Reset Successfully</font></h2>
Your new password has been sent to your email address.<br>
<?php
}else{
?>
<h2><font color="white">Error</font></h2>
<span class='error'><?php echo $fgmembersite->GetErrorMessage(); ?></span>
<?php
}
?>
<br>
<hr>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>