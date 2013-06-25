<?php
// ---------------------------
// --- Begin Configuration ---
// ---------------------------
$mysql_hostname = "localhost";
$mysql_user = "root";
$mysql_password = "";
$mysql_database = "";

//Provide the email address where you want to get notifications
$admin_email = "youremail@domain.com";

//Provide your site name or server name here
$website_name = "Minecraft Community";

//Provide the url where Donator Express is installed
//DO NOT include a trailing slash at the end.
$dep_url = "http://donate.mywebsite.com";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//The name of your virtual currency. You may choose to change this value.
$virtual_currency = "Tokens";

//The email where you will be accepting payments to.
$paypal = "your_paypal_email@domain.com";

//The currency symbol you will be accepting payments in.
$currency_symbol = " $";
//The currency code in which you will be accepting payments in.
$currency_code = "USD";

//By Default 1 Token sells for $1 USD. Users once registered have the option to 
//purchase additional tokens in the Donator Express Portal. Currently there are
//four configurable Virtual Currency/Token packages users can purchase to sustain
//their balance. Specify the price for them below, or leave them if you would like
//to use the default values.

//Diamond Ore One: Input the price below for 10 Tokens in your currency
$diamond1 = "10";
//Diamond Ore Two: Input the price below for 25 Tokens in your currency
$diamond2 = "25";
//Diamond Ore Three: Input the price below for 50 Tokens in your currency
$diamond3 = "50";
//Diamond Ore Four: Input the price below for 100 Tokens in your currency
$diamond4 = "100";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//For better security. Get a random string from this link: http://tinyurl.com/randstr
//And paste it below.
$securekey = "";

// -------------------------
// --- End Configuration ---
// -------------------------
?>