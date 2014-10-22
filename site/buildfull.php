<?php

function startsWith($haystack, $needle) {
    return $needle === "" || strpos($haystack, $needle) === 0;
}

$app_name = $_POST['app_name'];
$tmpfldr = "C:\\temp\\".$app_name; //replace for linux
mkdir($tmpfldr);

$props = array();
foreach ($_POST as $key => $value) {
	$props[$key] = $value;
}

//save info
$infoFile = $tmpfldr.'\\info.txt'; //replace for linux
file_put_contents ( $infoFile , $props['info']);
$props['app.info'] = $infoFile;

foreach ($_FILES as $key => $file) {	
	if(trim($file['tmp_name']) == true) {
		$fileName =  $tmpfldr."/".$file['name'];				
		move_uploaded_file($file['tmp_name'], $fileName);
		$keyf = str_replace("_file", "", $key);		
		$props[$keyf] = str_replace('/', '\\', $fileName); //replace for linux
	}	
}
$propsStr = '';
//save into properties
foreach ($props as $key => $value) {	
	$propVal = str_replace("\\", "\\\\", $value); //replace for linux
	if(!empty($propVal)) {		
		if(startsWith($propVal, 'http')) {
			$remoteFile = substr($propVal, strrpos($propVal, '/') + 1);
			file_put_contents($tmpfldr."/".$remoteFile, file_get_contents($propVal));
			$propsStr .= str_replace("_", ".", $key)."=".$tmpfldr."/".$remoteFile.PHP_EOL;
		} else {			
			$propsStr .= str_replace("_", ".", $key)."=".$propVal.PHP_EOL;
		}
	}
}
$propsFile = $tmpfldr.'\\'.$app_name.'.properties';
file_put_contents ( $propsFile , $propsStr);
chdir("C:\\Bitnami\\app-creator-pro");
$script = "gradlew.bat generateApk -Pconfig=".$propsFile." -i >> ".$tmpfldr."/log.log 2>&1"; // generateApk -Pconfig=temp/'.$app_name.'.properties >> log/'.$app_name.'.log';
echo shell_exec($script);
?>

