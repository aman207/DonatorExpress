<?php
// ---------------------------
// --- Begin Configuration ---
// ---------------------------

//Database Information
$mysql_hostname = "localhost";
$mysql_user = "root";
$mysql_password = "";
$mysql_database = "";

//Provide the email address where you want to get notifications
$admin_email = "example@domain.com";

//Provide your site name or server name here
$website_name = "Example Minecraft Community";

//Provide the url to your server website, if you don't have a 
//main website insert a octothorpe/hashtag (#)
$website_url = "http://example.com/";

//Provide the url where Donator Express is installed
//DO NOT include a trailing slash at the end.
$dep_url = "http://donate.example.com";

//Set the theme for your web portal 
//(Pre-loaded Themes: simple, professional, amelia)
//Themes By: Thomas Park
$theme = "simple";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//The name of your virtual currency. You may choose to change this value.
$virtual_currency = "Tokens";

//The email where you will be accepting payments to.
$paypal = "paypal_email@domain.com";

//The currency symbol you will be accepting payments in. (ex. €, $) 
$currency_symbol = "$";
//The currency code in which you will be accepting payments in. (ex. USD, CAD, EUR)
$currency_code = "USD";

//By Default 1 Token sells for 1 $USD. Users once registered have the option to 
//purchase additional tokens in the Donator Express Portal. Currently there are
//four configurable Virtual Currency/Token packages users can purchase to increase
//their balance. Specify the price for them below, or leave them if you would like
//to use the default values.

//Ore One: Input the price below for 10 Tokens
$ore1 = "10";
//Ore Two: Input the price below for 25 Tokens
$ore2 = "25";
//Ore Three: Input the price below for 50 Tokens
$ore3 = "50";
//Ore Four: Input the price below for 100 Tokens
$ore4 = "100";

// -----------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------

//For better security. Get a random string from this link: http://tinyurl.com/randstr
$securekey = "";

//Would you like to log successful admin logins to the admin control panel in the web portal? 
//Unsuccessful admin logins are automatically logged into the database. 
//Set to false if you would rather not log successful logins.
$log_admin_access = "true";

//Enable built in contact form?
//This will allow users to contact you on the Admin Email you specified earlier.
$enable_contact = "true";

//Show contents of config.php in admin panel?
//This is useful for debugging.
$show_config = "true";

// -------------------------
// --- End Configuration ---
// -------------------------
?>