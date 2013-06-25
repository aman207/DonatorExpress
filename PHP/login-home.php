<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");
require_once("./getbal.php");
$getuseremail = $fgmembersite->UserEmail();

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Members Area</title>
      <link rel="stylesheet" href="style/member-area.css" type="text/css" media="screen">
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
</head>
<body>
<div id="wrapper" class="section">

<table border="0" width="100%" cellpadding="1">
<tr>
<td><h2><font color="white">Members Area</font></h2></td>
<td align="right"><u>Account Management</u><br><br><a href='logout.php'>Logout</a><br><a href='change-pwd.php'>Change Password</a></td>
</tr>
</table>

Welcome back <?= $fgmembersite->UserFullName(); ?>!<br>
You currently have <font color="#01DFD7"><?php echo "$getusertoken $virtual_currency";?></font> in your account!<br><br>

<table border="0" width="100%">
<tr>
<td><center><img src="/generate/diamond.php?text=<?php echo $diamond1; ?>"></center></td>
<td><center><img src="/generate/diamond.php?text=<?php echo $diamond2; ?>"></center></td>
</tr>
<tr>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 10 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $diamond1; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="10"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><input type='submit' class="btn" value='Purchase 10 <?php echo $virtual_currency; ?>' /></center>
</form>
<br>
</td>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 25 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $diamond2; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="25"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><input type='submit' class="btn" value='Purchase 25 <?php echo $virtual_currency; ?>' /></center>
</form>
<br>
</td>
</tr>
<tr>
<td><center><img src="/generate/diamond.php?text=<?php echo $diamond3; ?>"></center></td>
<td><center><img src="/generate/diamond.php?text=<?php echo $diamond4; ?>"></center></td>
</tr>
<tr>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 50 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $diamond3; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="50"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><input type='submit' class="btn" value='Purchase 50 <?php echo $virtual_currency; ?>' /></center>
</form>
</td>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 100 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $diamond4; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url, $_SERVER["REQUEST_URI"]; ?>" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="100"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><input type='submit' class="btn" value='Purchase 100 <?php echo $virtual_currency; ?>' /></center>
</form>
</td>
</tr>
</table> 

<hr>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>
