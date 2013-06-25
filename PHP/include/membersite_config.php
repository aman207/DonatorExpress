<?PHP
// -------------------- WARNING ------------------->
// Please edit the config.php file
// Do not touch, this is NOT the configuration file
// -------------------- WARNING ------------------->

include("./include/config.php");
require_once("./include/fg_membersite.php");
$fgmembersite = new FGMembersite();

$fgmembersite->SetWebsiteName($website_name);

$fgmembersite->SetAdminEmail($admin_email);

$fgmembersite->InitDB(/*hostname*/$mysql_hostname,
                      /*username*/$mysql_user,
                      /*password*/$mysql_password,
                      /*database name*/$mysql_database,
                      /*table name*/'dep');

$fgmembersite->SetRandomKey($securekey);

// -------------------- WARNING ------------------->
// Please edit the config.php file
// Do not touch, this is NOT the configuration file
// -------------------- WARNING ------------------->
?>