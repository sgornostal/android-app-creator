<?php
if(isset($_GET['email']) && isset($_GET['code'])) {
	$email = $_GET['email'];
    $code = $_GET['code'];
	
	define('DB_HOST', 'conceptionssgbcom.ipagemysql.com');
	define('DB_USER', 'conceptions_sgb');
	define('DB_PASSWORD', 'Sgb-3888');
	define('DB_DATABASE', 'conceptions_sgb');
	
		if(!empty($email)) { 
		$connect = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
		if(!$connect) die('Failed to connect to server: ' . mysql_error());
		$db = mysql_select_db(DB_DATABASE);
		if(!$db) die("Unable to select database");
	
		mysql_query("INSERT INTO `emailAddress` (email) VALUES ('".$email."')"); 
		}
	
	$to = "apkcreator@frankwebstudio.com";
	$subject = "ApkCreator Registration";
	$body = "Email: " . trim($email) . "<br />\n Code: ".trim($code) ."<br />";
	
	$headers  = 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=utf-8' . "\r\n";
	if(mail($to, $subject, $body, $headers)) echo("1");
	else echo("-1.");
} else echo "-1";
?>