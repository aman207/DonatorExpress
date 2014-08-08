<?php
$page = $_GET['page'];

switch($page){
     case income: include "admin-income.php"; break;
     case users: include "admin-users.php"; break;
     case purchases: include "admin-soon.php"; break;
     case packagespurchased: include "admin-soon.php"; break;
     default: include "admin-home.php";
}
?>