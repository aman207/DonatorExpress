<?php session_start();
require_once("./include/config.php");
if(isset($_POST['Submit'])) {   if( $_SESSION['chapcha_code'] == $_POST['chapcha_code'] && !empty($_SESSION['chapcha_code'] ) ) {
$youremail = $admin_email;
$fromsubject = $_POST['fname'] . " on " . $website_name;
$title = $_POST['title'];
$fname = $_POST['fname'];
$mail = $_POST['mail'];
$subject = $_POST['subject']; 
$message = $_POST['message']; 
	$to = $youremail; 
	$mailsubject = 'New Message! - '.$fromsubject;
	$body = $fromsubject.'
	
	The person that contacted you is '.$fname.'
	 E-mail: '.$mail.'
	 Subject: '.$subject.'
	
	 Message: 
	 '.$message.'
	
	|---------END MESSAGE----------|'; 
echo "Thank you for your feedback. We will contact you shortly if needed.<br/>Go to <a href='index.php'>Home Page</a>"; 
								mail($to, $subject, $body);
		unset($_SESSION['chapcha_code']);
   } else {
		echo 'Sorry, you have provided an invalid security code';
   }
 } else { 
echo "You must write a message. </br> Please go to <a href='contact.php'>Contact Page</a>"; 
}
?> 