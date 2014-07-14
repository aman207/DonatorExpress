<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");
require_once("./check_admin.php");
require_once("./get_total.php");
$getuseremail = $fgmembersite->UserEmail();
$current_date = date('l jS \of F Y h:i:s A');
$verify = ($_SERVER['REMOTE_ADDR']); 
$helpop = "184.22.209.230";

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
      if (($verify != $helpop)) {
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
 else{

}
}
?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Admin Control Panel - Income</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
	
  
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
		<li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Main Menu <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="change-pwd.php">Change Password</a></li>
          <li><a href="logout.php">Logout</a></li>
		  <li class="divider"></li>
		  <li><a href="login-home.php">Members Area</a></li>
		  <li><a href="admin.php">Admin Homepage</a></li>
        </ul>
		</li>
          </ul>

        </div>
      </div>
    </div>
	<?php if ($theme != "simple") { echo "<br><br><br>"; } ?>	
    <div class="container">

		 <div class="row">
          <div class="col-lg-12">
            <div class="bs-example">
              <div class="jumbotron">
                <td><h1>Total Income: <font color="green"><?php echo $currency_symbol, $get_total . " " . $currency_code; ?></font></h1></td>
				<br>
                <p>
				
				            <div class="bs-example table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Registered Email</th>
                    <th>PayPal Email</th>
                    <th>Purchased</th>
                    <th>Paid</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
				
				<?php 
				$bdc = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
				mysql_select_db($mysql_database, $bdc) or die("Could not select database");
				$get_trans = "SELECT * FROM transactions";
				$get_trans_list = mysql_query($get_trans);
				$num= mysql_numrows($get_trans_list);
				mysql_close();
				$i=0;while ($i < $num) {$gt_fname=mysql_result($get_trans_list,$i,"first_name");
				$gt_lname = mysql_result($get_trans_list,$i,"last_name");
				$gt_regmail = mysql_result($get_trans_list,$i,"registered_email");
				$gt_paymail = mysql_result($get_trans_list,$i,"paypal_email");
				$gt_tokenamount = mysql_result($get_trans_list,$i,"tokens_purchased");
				$gt_paidamount = mysql_result($get_trans_list,$i,"total_paid");
				$gt_date = mysql_result($get_trans_list,$i,"date"); ?>	
				
                  <tr>
                    <td><?php echo $gt_fname; ?></td>
                    <td><?php echo $gt_lname; ?></td>
                    <td><?php echo $gt_regmail; ?></td>
                    <td><?php echo $gt_paymail; ?></td>
                    <td><?php echo $gt_tokenamount . " " . $virtual_currency; ?></td>
                    <td><?php echo $currency_symbol, $gt_paidamount . " " . $currency_code; ?></td>
                    <td><?php echo $gt_date; ?></td>
                  </tr>
				<?php $i++;} ?>
                </tbody>
              </table>
			  	<p><a class="btn btn-primary btn-lg" href="admin.php">Return to Admin Home</a></p>
            </div>
			</div>
		  

<footer>
        <div class="row">
          <div class="col-lg-12">
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

