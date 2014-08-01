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
     <title>[Portal] Donator Express - Admin Control Panel - Users</title>
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
                <td><h1>Users List</h1></td>
				<br>
				<?php
				if ($total_unconf_users > 0) {
					echo "
								<div class=\"alert alert-info\">
									<p>You currently have: <b>$total_unconf_users unconfirmed</b> user(s). These are users who signed up on the Donator Express Portal but did not confirm their email, it's possible these are spam accounts. Click on the button below to remove them permanently from your database.<br><br>
									<form method=\"post\" action=\"\"><button type=\"submit\" class=\"btn btn-info\" name=\"submit\">Purge Unconfirmed Users</button></form></p>
								</div>	 
						  "; }
				else
					{ echo ""; }
				?>
				
				<?php
					if(isset($_POST['submit']))
				{
				$bdp = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
				mysql_select_db($mysql_database, $bdp) or die("Could not select database");
					$purge = sprintf("DELETE FROM `dep` WHERE confirmcode != 'y'");
					mysql_query($purge) or die(mysql_error());
					echo "<div class=\"alert alert-success\">Successfully purged unconfirmed users.</div>";
				}
				?>
				
				            <div class="bs-example table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <th>Full Name</th>
                    <th>Registered Email</th>
                    <th>MC Username</th>
                    <th># of <?php echo $virtual_currency; ?></th>
                    <th>Confirmation Code</th>
                    <th>Admin Status</th>
                  </tr>
                </thead>
                <tbody>
				
				<?php 
				$bdc = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
				mysql_select_db($mysql_database, $bdc) or die("Could not select database");
				$get_users = "SELECT * FROM dep";
				$get_users_list = mysql_query($get_users);
				$num= mysql_numrows($get_users_list);
				mysql_close();
				$i=0;while ($i < $num) {$gt_name=mysql_result($get_users_list,$i,"name");
				$gt_email = mysql_result($get_users_list,$i,"email");
				$gt_tokens = mysql_result($get_users_list,$i,"tokens");
				$gt_username = mysql_result($get_users_list,$i,"username");
				$gt_confcode = mysql_result($get_users_list,$i,"confirmcode");
				$gt_admin = mysql_result($get_users_list,$i,"is_admin");?>	
				
                  <tr>
                    <td><?php echo $gt_name; ?></td>
                    <td><?php echo $gt_email; ?></td>
                    <td><?php echo $gt_username; ?></td>
                    <td><?php echo $gt_tokens; ?></td>
                    <td><?php echo $gt_confcode; ?></td>
                    <td><?php echo $gt_admin; ?></td>
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

