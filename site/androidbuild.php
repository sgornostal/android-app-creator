<?php
function getStatus() {
	return "localhost";
}
function buildApk($ip, $fields = array()) {
	echo "Building apk!";
	echo "\n";
	echo "ip address<br>";
	echo $ip;
	echo "<br>";
	$url = "http://". $ip ."/buildfull.php";
	$fields_string="";
	foreach($fields as $key=>$value) { $fields_string .= $key.'='.$value.'&'; }
	rtrim($fields_string, '&');
	while(!checkServer($ip)) {
	}
	//open connection
	$ch = curl_init();
	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
	curl_setopt($ch, CURLOPT_TIMEOUT, 40);
	curl_setopt($ch,CURLOPT_POST, count($fields));
	curl_setopt($ch,CURLOPT_POSTFIELDS, $fields_string);
	curl_setopt($ch,CURLOPT_RETURNTRANSFER, true);
 	curl_setopt($ch, CURLOPT_BINARYTRANSFER, true);
	$ret=curl_exec($ch);
	curl_close($ch);
	echo "Returning $ret";
	return $ret;
}
function checkServer($ip) {
    $url = "http://".$ip."/";
    $ch = curl_init();
    curl_setopt ($ch, CURLOPT_URL,$url );
    curl_setopt ($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt ($ch,CURLOPT_VERBOSE,false);
    curl_setopt($ch, CURLOPT_TIMEOUT, 5);
    $page=curl_exec($ch);
    $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);
    if($httpcode >= 200 && $httpcode < 300){ 
    	return true;
    }
    else {
    	return false;
    }
}

?>
