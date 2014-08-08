<?PHP
require_once("./include/membersite_config.php");
require_once("./include/config.php");

if(!$fgmembersite->CheckLogin())
{
    $fgmembersite->RedirectToURL("login.php");
    exit;
}
?>

<?php
$useremail = $fgmembersite->UserEmail();
$getuserip = $_SERVER["REMOTE_ADDR"];
$getuserhostname = gethostbyaddr($_SERVER['REMOTE_ADDR']);

$bd = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
mysql_select_db($mysql_database, $bd) or die("Could not select database");

$admin_access_log = sprintf("CREATE TABLE IF NOT EXISTS `admin_access_log` ( 
    `id` int NOT NULL AUTO_INCREMENT, 
    `registered_email` varchar(64) NOT NULL, 
    `ip_address` varchar(64) NOT NULL, 
    `host_name` varchar(128) NOT NULL, 
    `access_granted` varchar(3) NOT NULL, 
    `date` varchar(64) NOT NULL,
    PRIMARY KEY (id))");
    mysql_query($admin_access_log);

$query = sprintf("SELECT `is_admin`, `email` FROM dep WHERE email = '$useremail'");
$result = mysql_query($query);

if (!$result) {
    $message  = 'Invalid query: ' . mysql_error() . "\n";
    $message .= 'Whole query: ' . $query;
    die($message);
}


while ($row = mysql_fetch_assoc($result)) {
    $is_admin = $row['is_admin'];
}

?>
