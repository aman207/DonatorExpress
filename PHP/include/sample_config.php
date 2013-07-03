<?php
// ---------------------------
// --- Begin Configuration ---
// ---------------------------

//Database Information
$mysql_hostname = "localhost";
$mysql_user = "root";
$mysql_password = "";
$mysql_database = "donator";

//Provide the email address where you want to get notifications
$admin_email = "your_email@domain.com";

//Provite your site name or server name here
$website_name = "A Minecraft Community";

//Provide the url where Donator Express is installed
//DO NOT include a trailing slash at the end
$dep_url = "http://donate.yourwebsite.com";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//The name of your virtual currency. You may choose to change this value.
$virtual_currency = "Tokens";

//The email where you will be accepting payments to.
$paypal = "your_paypal@domain.com";

//The currency symbol you will be accepting payments in.
$currency_symbol = " $";
//The currency code in which you will be accepting payments in.
$currency_code = "USD";

//By Default 1 Token sells for 1 $USD. Users once registered have the option to 
//purchase additional tokens in the Donator Express Portal. Currently there are
//four configurable Virtual Currency/Token packages users can purchase to increase
//their balance. Specify the price for them below, or leave them if you would like
//to use the default values.

//Chest One: Input the price below for 10 Tokens
$diamond1 = "10";
//Chest Two: Input the price below for 25 Tokens
$diamond2 = "25";
//Chest Three: Input the price below for 50 Tokens
$diamond3 = "50";
//Chest Four: Input the price below for 100 Tokens
$diamond4 = "100";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//For better security. Get a random string from this link: http://tinyurl.com/randstr
$securekey = "";

//Would you like to log successful admin logins to the admin control panel in the web portal? 
//Unsuccessful admin logins are automatically logged into the database. 
//Set to false if you would rather not log successful logins.

$log_admin_access = "true";

// -------------------------
// --- End Configuration ---
// -------------------------
?>