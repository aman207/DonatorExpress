<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");
require_once("./check_admin.php");
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
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Admin Control Panel - Coming Soon!</title>
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
          </ul>

        </div>
      </div>
    </div>
	
    <div class="container">

		 <div class="row">
          <div class="col-lg-12">
            <div class="bs-example">
              <div class="jumbotron">
                <td><h1>Coming Soon!</h1></td>
				<br>
                <p>We are constantly pushing updates and making changes, this part of the Admin Panel is currently unavailable. Please check again soon!</p>
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

</body>
</html>

