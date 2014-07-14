<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");
require_once("./getbal.php");
require_once("./check_admin.php");

$getuseremail = $fgmembersite->UserEmail();

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}

?>

<html lang="en">
  <head>
     <title>[Portal] Donator Express - Members Area</title>
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
		  <?php if ($is_admin == "true") { echo "<li class=\"divider\"></li><li><a href=\"admin.php\">Admin Control Panel</a></li>"; } ?>
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
                <td><h1>Members Area</h1></td>
				</tr>
				</table>
				<br>
                <p>Welcome back <?= $fgmembersite->UserFullName(); ?>!<br>
				You currently have <font color="#01DFD7"><?php echo "$getusertoken $virtual_currency";?></font> in your account!</p>
              </div>
            </div>
          </div>
        </div>
	
		  
	 <div class="row">
          <div class="col-lg-12">
            <div class="bs-example">
              <div class="jumbotron">

<div class="bs-example table-responsive">
<table class="table table-striped table-bordered table-hover">
<tr>
<td><center><img src="generate/ore.php?text=<?php echo $ore1; ?>" width="125" height="125"></center></td>
<td><center><img src="generate/ore.php?text=<?php echo $ore2; ?>" width="125" height="125"></center></td>
<td><center><img src="generate/ore.php?text=<?php echo $ore3; ?>" width="125" height="125"></center></td>
<td><center><img src="generate/ore.php?text=<?php echo $ore4; ?>" width="125" height="125"></center></td>
</tr>
<tr>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 10 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $ore1; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url; ?>/login-home.php">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url; ?>/login-home.php" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="10"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><button type='submit' class="btn btn-primary">Purchase 10 <?php echo $virtual_currency; ?></button></center>
</form>
</td>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 25 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $ore2; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url; ?>/login-home.php">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url; ?>/login-home.php" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="25"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><button type='submit' class="btn btn-primary">Purchase 25 <?php echo $virtual_currency; ?></button></center>
</form>
</td>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 50 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $ore3; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url; ?>/login-home.php">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url; ?>/login-home.php" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="50"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><button type='submit' class="btn btn-primary">Purchase 50 <?php echo $virtual_currency; ?></button></center>
</form>
</td>
<td>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_self">
<input type="hidden" name="cmd" value="_xclick" />
<input type="hidden" name="business" value="<?php echo $paypal; ?>"/>
<input type="hidden" name="item_name" value="Purchase 100 <?php echo $virtual_currency; ?> for account <?php echo $getuseremail; ?>"/>
<input type="hidden" name="item_number" value="<?php echo $getuseremail; ?>"/>
<input type="hidden" name="amount" value="<?php echo $ore4; ?>"/>
<input type="hidden" name="return" value="<?php echo $dep_url; ?>/login-home.php">
<input type="hidden" name="cancel_return" value="<?php echo $dep_url; ?>/login-home.php" />
<input type="hidden" name="notify_url" value="<?php echo $dep_url; ?>/ipn.php">
<input type="hidden" name="custom" value="100"/>
<input type="hidden" name="currency_code" value="<?php echo $currency_code; ?>"/>
<center><button type='submit' class="btn btn-primary">Purchase 100 <?php echo $virtual_currency; ?></button></center>
</form>
</td>
</tr>
</table>
					</div>
				</div>
            </div>
          </div>
        </div>
 <footer>
        <div class="row">
          <div class="col-lg-12">
		  <?php if ($enable_contact == "false") { } else { echo '<ul class="list-unstyled"><li class="pull-right"><a href="contact.php">Administrative Contact</a></li></ul>'; }?>
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