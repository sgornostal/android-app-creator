<?php
include('config.php');
include('dbconnect.php');
$email=$_REQUEST['email'];
$query="SELECT email FROM emailAddress
		WHERE email='$email'";
	    $result = mysql_query($query)or die (mysql_error());
		$count=mysql_num_rows($result);
		if($count==0){
		 echo"noemail";
		}
		else{
		echo"correct";
		}
			
?>