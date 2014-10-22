<?php
function checkServer($ip) {
    $url = "http://".$ip.":8181"."/beta/";
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

function buildApk($ip, $data = array()) {
    echo "Building apk!";
    echo "\n";
    echo "ip address<br>";
    echo $ip;
    echo "<br>";
    $url = "http://". $ip .":8181"."/beta/buildfull.php";    
    while(!checkServer($ip)) {
    }
    //open connection
    $ch = curl_init();
    //set the url, number of POST vars, POST data
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data);    
    $ret=curl_exec($ch);
    curl_close($ch);
    echo "Returning $ret";
    return $ret;
}
print_r(array_merge($_FILES, $_POST));
$res = buildApk("localhost", array_merge($_POST, $_FILES));
header("Location: /beta/building.php");
exit;

?>
