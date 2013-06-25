<?php
/**
 *  PHP-PayPal-IPN Example
 *
 *  This shows a basic example of how to use the IpnListener() PHP class to 
 *  implement a PayPal Instant Payment Notification (IPN) listener script.
 *
 *  For a more in depth tutorial, see my blog post:
 *  http://www.micahcarrick.com/paypal-ipn-with-php.html
 *
 *  This code is available at github:
 *  https://github.com/Quixotix/PHP-PayPal-IPN
 *
 *  @package    PHP-PayPal-IPN
 *  @author     Micah Carrick
 *  @copyright  (c) 2011 - Micah Carrick
 *  @license    http://opensource.org/licenses/gpl-3.0.html
 */
 
 
ini_set('log_errors', true);
ini_set('error_log', 'ipn_errors.log');


// instantiate the IpnListener class
include('ipnlistener.php');
include('include/config.php');
$listener = new IpnListener();

$listener->use_sandbox = false;

/*
By default the IpnListener object is going  going to post the data back to PayPal
using cURL over a secure SSL connection. This is the recommended way to post
the data back, however, some people may have connections problems using this
method. 

To post over standard HTTP connection, use:
$listener->use_ssl = false;

To post using the fsockopen() function rather than cURL, use:
$listener->use_curl = false;
*/

/*
The processIpn() method will encode the POST variables sent by PayPal and then
POST them back to the PayPal server. An exception will be thrown if there is 
a fatal error (cannot connect, your server is not configured properly, etc.).
Use a try/catch block to catch these fatal errors and log to the ipn_errors.log
file we setup at the top of this file.

The processIpn() method will send the raw data on 'php://input' to PayPal. You
can optionally pass the data to processIpn() yourself:
$verified = $listener->processIpn($my_post_data);
*/
try {
    $listener->requirePostMethod();
    $verified = $listener->processIpn();
} catch (Exception $e) {
    error_log($e->getMessage());
    exit(0);
}


if ($verified) {
$errmsg = '';
    if ($_POST['payment_status'] != 'Completed') { 
        exit(0); 
    }
    if ($_POST['receiver_email'] != $paypal) {
        $errmsg .= "'receiver_email' does not match: ";
        $errmsg .= $_POST['receiver_email']."\n";
    }
    if ($_POST['mc_currency'] != $currency_code) {
        $errmsg .= "'mc_currency' does not match: ";
        $errmsg .= $_POST['mc_currency']."\n";
    }
    if (empty($errmsg)) {
    $useremail = $_POST['item_number'];
    error_log($useremail);
    $gross = $_POST['mc_gross'];
    error_log($gross);
    $newtoken = $_POST['custom'];
    $firstname = $_POST['first_name'];
    $lastname = $_POST['last_name'];
    $payeremail = $_POST['payer_email'];
    $current_date = date('l jS \of F Y h:i:s A');
    $package1 = "10";
    $package2 = "25";
    $package3 = "50";
    $package4 = "100";
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
    }

    if ($_POST['custom'] == $package1) {
       if ($_POST['mc_gross'] == $diamond1) {
            $updatetbl = sprintf("UPDATE dep SET tokens = tokens + $package1 WHERE email = '$useremail'");
            $record_transaction = sprintf("INSERT INTO transactions (registered_email, tokens_purchased, total_paid, first_name, last_name, paypal_email, date)
             VALUES ('$useremail','$newtoken','$gross','$firstname','$lastname','$payeremail','$current_date')");
            mysql_query($updatetbl);
            mysql_query($record_transaction);
       }
    }
    if ($_POST['custom'] == $package2) {
       if ($_POST['mc_gross'] == $diamond2) {
            $updatetbl = sprintf("UPDATE dep SET tokens = tokens + $package2 WHERE email = '$useremail'");
            $record_transaction = sprintf("INSERT INTO transactions (registered_email, tokens_purchased, total_paid, first_name, last_name, paypal_email, date)
             VALUES ('$useremail','$newtoken','$gross','$firstname','$lastname','$payeremail','$current_date')");
            mysql_query($updatetbl);
            mysql_query($record_transaction);
       }
    }
    if ($_POST['custom'] == $package3) {
       if ($_POST['mc_gross'] == $diamond3) {
            $updatetbl = sprintf("UPDATE dep SET tokens = tokens + $package3 WHERE email = '$useremail'");
            $record_transaction = sprintf("INSERT INTO transactions (registered_email, tokens_purchased, total_paid, first_name, last_name, paypal_email, date)
             VALUES ('$useremail','$newtoken','$gross','$firstname','$lastname','$payeremail','$current_date')");
            mysql_query($updatetbl);
            mysql_query($record_transaction);
       }
    }
    if ($_POST['custom'] == $package4) {
       if ($_POST['mc_gross'] == $diamond4) {
            $updatetbl = sprintf("UPDATE dep SET tokens = tokens + $package4 WHERE email = '$useremail'");
            $record_transaction = sprintf("INSERT INTO transactions (registered_email, tokens_purchased, total_paid, first_name, last_name, paypal_email, date)
             VALUES ('$useremail','$newtoken','$gross','$firstname','$lastname','$payeremail','$current_date')");
            mysql_query($updatetbl);
            mysql_query($record_transaction);
       }
    }

} else {
    mail($admin_email, 'Donator Express - Invalid Payment Detected - Payment Log', $listener->getTextReport());
}

?>