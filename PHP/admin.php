<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");
require_once("./check_admin.php");
require_once("./get_total.php");
$getuseremail = $fgmembersite->UserEmail();
$current_date = date('l jS \of F Y h:i:s A');

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}

?>
<?PHP
    if ($is_admin == "true") {
        if ($log_admin_access == "true") {
$log_access = sprintf("INSERT INTO admin_access_log (registered_email, ip_address, host_name, access_granted, date)
VALUES ('$useremail','$getuserip','$getuserhostname','yes','$current_date')");
mysql_query($log_access);
        }
   }
else
   {
$log_access = sprintf("INSERT INTO admin_access_log (registered_email, ip_address, host_name, access_granted, date)
VALUES ('$useremail','$getuserip','$getuserhostname','no','$current_date')");
mysql_query($log_access);
echo "<title>[Portal] Donator Express - Admin Control Panel - Access Denied</title>";
echo "<h1>Access Denied - Insufficient Permission</h1>";
echo "<font color=\"red\">$getuseremail</font> has no access to view this page! <br>";
echo "Date: $current_date <br>"; 
echo "<br>This activity has been logged, administrators will been notified.";
die;
   } 
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
      <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
      <title>[Portal] Donator Express - Admin Control Panel</title>
      <link rel="stylesheet" href="style/member-area.css" type="text/css" media="screen">
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
      <script type='text/javascript' src='scripts/gen_validatorv31.js'></script>
</head>
<body>
<div id="wrapper" class="section">

<table border="0" width="100%" cellpadding="1">
<tr>
<td><h2><font color="white">Admin Control Panel</font></h2></td>
<td align="right"><u>Account Management</u><br><br><a href='logout.php'>Logout</a><br><a href='change-pwd.php'>Change Password</a><br><a href='login-home.php'>Membership Area</a></td>
</tr>
</table>

Welcome <?= $fgmembersite->UserFullName(); ?> to your Admin Panel.<br>
Here you can perform adminsitrative tasks!<br>
<h3>Overview</h3>
Click on any of the links below to view the detailed breakdown, and for management options.<br><br>
<?php
echo "<a href=\"admin.php\">Total Users: <font color=\"#01DFD7\">$get_users_list</font></a><br>";
if ($get_total > 0) {
echo "<a href=\"admin.php\">Total Income:<font color=\"#04B404\">$currency_symbol$get_total $currency_code</font></a><br>";
}
else
   { $zero = "0"; echo "Total Income: No payments greater than <font color=\"#FF2B2B\">$currency_symbol$zero $currency_code</font> have been made!<br>"; }

if ($tokens_spent > 0) {
echo "<a href=\"admin.php\">Total $virtual_currency Spent: <font color=\"#FFFF00\">$tokens_spent</font></a><br>";
}
else
   { echo "Total $virtual_currency Spent: No Recorded $virtual_currency spent on in-game packages with a value of 0 or greater!<br>"; }
if ($purchased_packages > 0) {
echo "<a href=\"admin.php\">Total Packages Purchased: <font color=\"#FE9A2E\">$purchased_packages</font></a><br>";
}

if ($total_failed_access > 0) {
echo "<a href=\"admin.php\">Total Failed Admin Access: <font color=\"#FF2B2B\">$total_failed_access</font></a><br>";
}
?>
<br>
Currently the above links do not work. The functionality to view detailed breakdown and statistics will be added in the next update. Thank you!

<hr>
&copy; <?php echo date("Y") ?> Donator Express
</div>
</body>
</html>
