<?PHP
require_once("./include/membersite_config.php");

$fgmembersite->LogOut();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Logout</title>
      <link rel="stylesheet" href="style/style.css" type="text/css" media="screen">
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
</head>
<body>
<div id="wrapper" class="section">
<h2><font color="white">Logged out</font></h2>
You have been successfully logged out.<br>
Would you like to <a href='login.php'>Login Again</a>?<br><br>
<hr>
&copy; <?php echo date("Y") ?> Donator Express

</div>

</body>
</html>