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
$usertoken = $fgmembersite->UserEmail();

$bd = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
mysql_select_db($mysql_database, $bd) or die("Could not select database");

$query = sprintf("SELECT `tokens`, `email` FROM dep WHERE email = '$usertoken'");
$result = mysql_query($query);

if (!$result) {
    $message  = 'Invalid query: ' . mysql_error() . "\n";
    $message .= 'Whole query: ' . $query;
    die($message);
}


while ($row = mysql_fetch_assoc($result)) {
    $getusertoken = $row['tokens'];
    $getuseremail = $row['email'];
}

?>
