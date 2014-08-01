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
     <title>[Portal] Donator Express - Admin Control Panel</title>
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
			  
			  <table border="0" width="100%" cellpadding="1">
				<tr>
                <td><h1>Admin Control Panel</h1></td>
				</tr>
				</table>
				<br>
                <p>Welcome <?= $fgmembersite->UserFullName(); ?> to your Admin Panel. <br>
				Here you can perform administrative tasks!</p>
              </div>
            </div>
          </div>
        </div>
		
<div class="modal fade" id="noIncome">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title">No Income</h4>
      </div>
      <div class="modal-body">
        <p>You currently don't have any income, therefore no information has been logged!</p>
		<p>But don't stress, when someone buys <?php echo $virtual_currency; ?> this button will become an active link.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Okay</button>
      </div>
    </div>
  </div>
</div>

<div class="panel panel-default">
<div class="panel-heading">
<h3 class="panel-title">Statistics</h3>
</div>
<div class="panel-body">

<div class="bs-example">
<ul class="nav nav-pills">
<?php
echo "<li class='active'><a href='admin.php?page=users'>Total Users<span class='badge'>$get_users_list</span></a></li>";
if ($get_total > 0) {
echo "<li class='active'><a href='admin.php?page=income'>Total Income<span class='badge'>$currency_symbol$get_total $currency_code</span></a></li>";
}
else
   { $zero = "0"; echo "<li class='active'><a data-toggle=\"modal\" href='#noIncome'>Total Income<span class='badge'>$currency_symbol$zero $currency_code</span></a></li>"; }

if ($tokens_spent > 0) {
echo "<li class='active'><a href='admin.php?page=purchases'>Total $virtual_currency Spent<span class='badge'>$tokens_spent</span></a></li>";
}
else
   { echo "<li class='active'><a href='admin.php?page=purchases'>Total $virtual_currency Spent<span class='badge'>0</span></a></li>"; }
if ($purchased_packages > 0) {
echo "<li class='active'><a href='admin.php?page=packagespurchased'>Total Packages Purchased<span class='badge'>$purchased_packages</span></a></li>";
}
?>

</div>
</div>
</div>

            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Admin Tools</h3>
              </div>
              <div class="panel-body">
			  
			  <ul class="nav nav-tabs" style="margin-bottom: 15px;">
				<li class="active"><a href="#accesslog" data-toggle="tab">Access Log</a></li>
				<?php if ($show_config == "false") { } else { echo '<li class=""><a href="#viewconfig" data-toggle="tab">View Config</a></li>';} ?>
				<li class=""><a href="#gethelp" data-toggle="tab">Get Help</a></li>
				<li class=""><a href="#about" data-toggle="tab">About</a></li>
				</ul>
		<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade active in" id="accesslog">
    			  <?php if ($total_failed_access > 0) {
			echo "<span class='label label-danger'>Total Failed Admin Access: $total_failed_access</span><br><br>"; } ?>
			
			<div class="bs-example table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <th>Registered Email</th>
                    <th>IP Address</th>
                    <th>Hostname</th>
                    <th>Access Granted</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
				<?php 
				$bdc = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
				mysql_select_db($mysql_database, $bdc) or die("Could not select database");
				$get_transactions = "SELECT * FROM admin_access_log ORDER BY id DESC, id DESC LIMIT 15";
				$get_transactions_list = mysql_query($get_transactions);
				$num= mysql_numrows($get_transactions_list);
				mysql_close();
				$i=0;while ($i < $num) {$gt_regemail=mysql_result($get_transactions_list,$i,"registered_email");
				$gt_ip = mysql_result($get_transactions_list,$i,"ip_address");
				$gt_hname = mysql_result($get_transactions_list,$i,"host_name");
				$gt_access = mysql_result($get_transactions_list,$i,"access_granted");
				$gt_date = mysql_result($get_transactions_list,$i,"date");?>	
				
                  <tr>
                    <td><?php echo $gt_regemail; ?></td>
                    <td><?php echo $gt_ip; ?></td>
                    <td><?php echo $gt_hname; ?></td>
                    <td><?php echo $gt_access; ?></td>
                    <td><?php echo $gt_date; ?></td>
                  </tr>
				<?php $i++;} ?>
                </tbody>
              </table>
			  * The table above shows 15 of the most recent data.
			  </div>
			
	</div>
 
<?php
    if ($show_config == "false") {
	
	}
	else {
	echo '<div class="tab-pane fade" id="viewconfig">';
	$file = "include/config.php";
    $content = file_get_contents($file);
    echo "<textarea class=\"form-control\" rows=\"80\" disabled>" . htmlspecialchars($content) . "</textarea></div>";
	}
	?>
<div class="tab-pane fade" id="gethelp">
<iframe src="http://aman207.net/support" width=100% height=100%>Woah.. iFrames seems to be disabled here. Please visit: http://aman207.net/support instead.</iframe>
  </div>
<div class="tab-pane fade" id="about">
<pre>
  _____                    _               ______                              
 |  __ \                  | |             |  ____|                             
 | |  | | ___  _ __   __ _| |_ ___  _ __  | |__  __  ___ __  _ __ ___  ___ ___ 
 | |  | |/ _ \| '_ \ / _` | __/ _ \| '__| |  __| \ \/ / '_ \| '__/ _ \/ __/ __|
 | |__| | (_) | | | | (_| | || (_) | |    | |____ >  <| |_) | | |  __/\__ \__ \
 |_____/ \___/|_| |_|\__,_|\__\___/|_|    |______/_/\_\ .__/|_|  \___||___/___/
                                                      | |                      
       Web Portal Developed By: AzroWear              |_|                      
       Plugin Developed By: aman207

<p>
For commands and configuration please visit
our bukkit developer page at: <a href="http://dev.bukkit.org/bukkit-plugins/donator-express/">http://dev.bukkit.org/bukkit-plugins/donator-express/</a>

Please keep all copyright notices intact which are included in the footer of the web pages.
You are free to modify the Donator Express Portal files in compliance to our license.
If you have any questions feel free to contact us.

If you find our plugin useful help spread the word, it really helps :)

Also be sure to visit our sponsor: ZogoHost, formally known as ZogoPW.
ZogoPW is a Reliable Minecraft Hosting service that provides an exceptional experience.
ZogoHost offers a <b>$7.50 per GB</b> of ram exclusively <b>only to Donator Express Users</b>.
Contact ZogoHost Sales Department to receive a 25% discount for life on any package!

ZogoHost (ZogoPW) - <a href="http://zogohost.com">http://zogohost.com</a>
</p>													  
</pre>
</div>

</div>
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
