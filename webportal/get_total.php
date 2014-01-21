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

$bd = mysql_connect($mysql_hostname, $mysql_user, $mysql_password) or die("Could not connect database");
mysql_select_db($mysql_database, $bd) or die("Could not select database");

$crtable = sprintf("CREATE TABLE IF NOT EXISTS `transactions` ( 
    `id` int NOT NULL AUTO_INCREMENT, 
    `registered_email` varchar(64) NOT NULL, 
    `tokens_purchased` varchar(16) NOT NULL, 
    `total_paid` varchar(16) NOT NULL, 
    `first_name` varchar(40) NOT NULL, 
    `last_name` varchar(40) NOT NULL,
    `paypal_email` varchar(64) NOT NULL,
    `date` varchar(64) NOT NULL,
    PRIMARY KEY (id))");
mysql_query($crtable);

$crtable_purchases = sprintf("CREATE TABLE `packages_purchased` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `tokens` varchar(16) NOT NULL,
  `rank` varchar(16) NOT NULL,
  `date` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)) 
  ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16;");
mysql_query($crtable_purchases);

$get_users = mysql_query("SELECT * FROM dep");
$get_users_list = mysql_num_rows($get_users);

$get_packages_purchased = mysql_query("SELECT * FROM packages_purchased");
$purchased_packages = mysql_num_rows($get_packages_purchased);

$failed_access = mysql_query("SELECT `id`, `access_granted` FROM `admin_access_log` WHERE access_granted = 'no'");
$total_failed_access = mysql_num_rows($failed_access);

$total_tokens_spent = sprintf("SELECT sum(tokens) AS tokens_sum FROM packages_purchased");
$spent_tokens = mysql_query($total_tokens_spent);

$total_income = sprintf("SELECT sum(total_paid) AS total_paid_sum FROM transactions");
$value = mysql_query($total_income);

if (!$value) {
    $message  = 'Invalid query: ' . mysql_error() . "\n";
    $message .= 'Whole query: ' . $query;
    die($message);
}

if (!$spent_tokens) {
    $message  = 'Invalid query: ' . mysql_error() . "\n";
    $message .= 'Whole query: ' . $query;
    die($message);
}

while ($row = mysql_fetch_assoc($value)) {
    $get_total = $row['total_paid_sum'];
}

while ($row = mysql_fetch_assoc($spent_tokens)) {
    $tokens_spent = $row['tokens_sum'];
}
?>
