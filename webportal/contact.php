<?php
require_once("./include/membersite_config.php");
require_once("./include/config.php");
if ($enable_contact == "false")
	{ die; }
else { }
?>
<html lang="en">
  <head>
     <title>[Portal] Donator Express - Contact</title>
	 <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
	  
      <?php  echo '<link rel="stylesheet" href="themes/' . $theme . '/bootstrap.css" media="screen">
      <link rel="stylesheet" href="themes/' . $theme . '/bootswatch.min.css">'; ?>
	
  
      <script src="http://code.jquery.com/jquery-1.6.3.min.js" type="text/javascript" charset="utf-8"></script>
	  <script type='text/javascript' src='scripts/con_validate.js'></script>
	  
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
		<?php if($fgmembersite->CheckLogin()) { echo '	
		<li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Main Menu <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="change-pwd.php">Change Password</a></li>
          <li><a href="logout.php">Logout</a></li>
		  <li class="divider"></li><li><a href="login-home.php">Members Area</a></li>'; } ?>
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
            <div class="page-header">
              <h1 id="forms">Contact</h1>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-6">
            <div class="well">
              <form class="bs-example form-horizontal" name="contact_form" method="post" action="sendmail.php" onSubmit="return evalid()">
                <fieldset>
                  <legend><font size="3">Have a question or concern? Simply fill out the fields below and we will get back to you shortly!</font></legend>
                  <div class="form-group">
                    <label class="col-lg-2 control-label">Name:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name="fname" value='<?php if($fgmembersite->CheckLogin()) { echo "" . $fgmembersite->UserFullName() . ""; }?>' maxlength="50" placeholder="First Name">
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-lg-2 control-label">E-Mail:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name="mail" value='<?php if($fgmembersite->CheckLogin()) { echo "" . $fgmembersite->UserEmail() . ""; }?>' maxlength="50" placeholder="Your E-Mail Address">
                    </div>
                  </div>
				                    <div class="form-group">
                    <label class="col-lg-2 control-label">Subject:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" name='subject' value='' maxlength="50" placeholder="Message Subject">
                    </div></div>
					
				<div class="form-group">
				<label class="col-lg-2 control-label">Message:</label>
					<div class="col-lg-10">
				<textarea class="form-control" name="message" onkeyup="return limitarelungime(this, 255)" rows="5"></textarea>
				</div>
                  </div>
				  <div class="form-group">
                    <label class="col-lg-2 control-label">Code:</label>
                    <div class="col-lg-10">
                      <input type="text" class="form-control" id="chapcha_code"  name="chapcha_code" value='' maxlength="50" placeholder="Security Code">
					  <img src="security_image.php" border="0" />
                    </div></div>
                  <div class="form-group">
                    <div class="col-lg-10 col-lg-offset-2">
                      <button type="submit" class="btn btn-primary" name='Submit'>Submit</button> 
					  <button class="btn btn-default" type="reset" name="reset">Reset</button>
                    </div>
                  </div>
                </fieldset>
              </form>
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