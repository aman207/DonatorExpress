<?php  session_start();
class captcha {
var $font = 5; 
function random($nr) {
  $letters="23456789bcdfghjkmnpqrstvwxyz";
  while ($i < $nr) {
  if($i==rand(0,$nr-1)) { 
  $text .= substr(strtoupper($letters), mt_rand(0, strlen($letters)-1), 1);
  }
  else
  {
  $text .= substr($letters, mt_rand(0, strlen($letters)-1), 1);
  }
  $i++;
  }
  return $text;
  }
  function captcha($width,$height,$nru) {
$text=$this->random($nru);
$image = @imagecreate($width, $height) or die('unable create image!');
$bg_col = imagecolorallocate($image, 0, 191, 255);
  $txt_col = imagecolorallocate($image, 255, 255, 255);
  $rand_col = imagecolorallocate($image, 0, 0, 0);
  imagefill($image, 100, 100, $bg_col);
  imageantialias($image, true);
  imagestring($image, $this->font , rand(1, 10), rand(1,10), $text, $txt_col); 
  imageline($image, rand(0,$width), rand(0,$height), rand(0,$width), rand(0,$height), $rand_col);
  for( $i=0; $i<6; $i++ ) {
  imagefilledellipse($image, rand(0,$width), rand(0,$height), 1, 1, $rand_col);
  } 
header('Content-Type: image/jpeg');
  imagejpeg($image);
  imagedestroy($image);
  $_SESSION['chapcha_code'] = strtolower($text);
  }
  }
  $width='70';
  $height='25';
  $nru='5';
  $iesire = new captcha($width,$height,$nru);
  ?>
  