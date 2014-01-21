<?php
require_once("../include/config.php");
$hostname = gethostbyaddr($_SERVER['REMOTE_ADDR']);
$remote_version = file_get_contents('http://zogo.pw/donatorexpress/current_released_version.txt'); 
$local_version = file_get_contents(getcwd() . '/version.txt'); 
if ($remote_version == $local_version) {
   echo "No new updates found for Donator Express";
} else {
$to      = $admin_email;
$subject = 'Donator Express - Update Available for ' . $website_name;
$message = 'Dear Admin,' . "\r\n" . 'An update for Donator Express is available at: http://dev.bukkit.org/server-mods/Donator-Express it is advised that you update as soon as possible to address any security issues that may exist with your current installation.'
. "\r\n" . "\r\n" . 'Current Version: ' . $local_version . "\r\n" . 'New Version: ' . $remote_version . "\r\n" . "\r\n" . 'For questions, concerns or comments feel free to contact us at our developer page.' . "\r\n" . "\r\n" . '==============================='
. "\r\n" . 'This email has been automatically sent from: ' . $dep_url;
$headers = 'From: noreply@'. $hostname;
mail($to, $subject, $message, $headers);
   echo "An update has been found!";
}
?>