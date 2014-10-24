<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Building | ApkCreator - App Maker Pro</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<style>
@charset "utf-8";
html, body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
    background-color:#4fb0e1;
	margin: 0;
	padding: 0;
	color: #000;
	height: 100%;
}
ul, ol, dl { 
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 
	padding-right: 15px;
	padding-left: 15px; 
}
h1 { align:center;
}
a img { 
	border: none;
}
a:link {
	color: #42413C;
	text-decoration: underline;
}
a:visited {
	color: #6E6C64;
	text-decoration: underline;
}
a:hover, a:active, a:focus { 
	text-decoration: none;
}
.container {
	width: 100%;
    min-height: 100%;
    height: auto !important;
    height: 100%;
    margin: 0 auto -20px; 
}
.content {
	height:auto;
}
.push {
	height:10px;
}
.footer {
	height:20px;
}

.container2 {
	width: 100%;
    min-height: 100%;
    height: auto !important;
    height: 100%;
    margin: 0 auto -71px; 
}
.content2 {
	height:auto;
}
.push2 {
	height:71px;
}
.footer2 {
	height:71px;
}

.fltrt {  
	float: right;
	margin-left: 8px;
}
.fltlft { 
	float: left;
	margin-right: 8px;
}
.clearfloat { 
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}
a:hover {color:#cf0207;}
a:link {text-decoration:none;}
.box1 {
margin-top:20px;
position:relative;
height:auto;
min-height:232px;
width:95%;
margin-left:auto;
margin-right:auto;
left:0px;right:0px;
background: none repeat scroll 0 0 #4498c4;
border: 2px solid #539bc4;
border-radius: 5px 5px 5px 5px;
}
.box2 {
margin-top:20px;
position:relative;
margin-bottom:10px;
height:auto;
width:95%;
margin-left:auto;
margin-right:auto;
left:0px;right:0px;
background: none repeat scroll 0 0 #4498c4;
border: 2px solid #539bc4;
border-radius: 5px 5px 5px 5px;
}
</style>
</head>
<body>
<div id="phase1" class="container">
<div class="content">
<div style="height:53px;"><center><img src="images/loading.gif" style="top:14px;left:-8px;position:relative" height="25px" width="20px" />
<font style="font-family: Roboto, helvetica, arial, sans-serif;font-weight: 400;color:#ffffff;font-size : 15px;top:7px;left:-8px;position:relative">Creating your app..</font><br/><br/>
</center></div>
<div style="left:0px;right:0px;margin-left:auto;margin-right:auto;border-bottom: 1px solid #ffffff;width:100%;position:relative"></div>
<div class="box1">
<div id="wrap" style="width:auto;text-align:center; height:230px; overflow-y:scroll;margin-left:auto;margin-right:auto;left:0px;right:0px;">
<?php error_reporting(E_ALL);

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
$permissionsFile = $tmpfldr.'\\permissions.txt';
$infoFile = $tmpfldr.'\\info.txt'; //replace for linux
file_put_contents ( $infoFile , $props['info']);
file_put_contents ( $permissionsFile , $props['permissions']);
$props['app.info'] = $infoFile;
$props['app.permissions'] = $permissionsFile;

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

$propsStr = str_replace('\\', '\\\\', $propsStr);

$propsFile = $tmpfldr.'\\'.$app_name.'.properties';
file_put_contents ( $propsFile , $propsStr);
chdir("C:\\Bitnami\\app-creator-pro");
$dirToSave = $_SERVER['DOCUMENT_ROOT']."/packages/";
$script = "gradlew.bat generateApk -Pconfig=".$propsFile." -Ptarget=".$dirToSave; // generateApk -Pconfig=temp/'.$app_name.'.properties >> log/'.$app_name.'.log';
if( ($fp = popen($script, "r")) ) {
    while( !feof($fp) ){
        $line = fread($fp, 1024);        
        flush();
        ob_flush();
        echo $line."<br/>";
        echo '<script type="text/javascript">var objDiv=document.getElementById("wrap");objDiv.scrollTop = objDiv.scrollHeight;</script>';
    }
    $code = pclose($fp);
    $refer = $_SERVER['HTTP_REFERER'];
	$host =  $_SERVER['HTTP_HOST'];	
    if($code == 0) {        	
    	$downloadLink = $host."/packages/".$app_name.".apk";
    	sleep(1);
    	echo "<script type='text/javascript'> window.location.href = '".$refer."builded.php?status=success&url=".$downloadLink."';</script>"; 	
    } else {    	
    	echo "<script type='text/javascript'> window.location.href = 'http://".$host."/building.php?status=error';</script>";
    }        
}
?>
</div>                                                   
</div>
</div>
<script type="text/javascript">var objDiv=document.getElementById("wrap");objDiv.scrollTop = objDiv.scrollHeight;</script>
<div style="left:0px;right:0px;margin-left:auto;margin-right:auto;border-bottom: 1px solid #ffffff;width:100%;margin-top:20px;"></div>
<br/>
<div class="push"></div>
</div>
<div id="phase11" class="footer">
<center><font style="font-family:'Lucida Sans Unicode', 'Lucida Grande', sans-serif;font-size:11px;">COPYRIGHT © | 2014 FRANKWEBDESIGN</font></center>
</div>
<div id="phase2" class="container2" style="display:none;">
<div class="content2">
<div style="height:53px;"><center>
<font style="font-family: Roboto, helvetica, arial, sans-serif;font-weight: 400;color:#ffffff;font-size : 15px;top:15px;position:relative">Application created!</font><br/><br/>
</center></div>
<div style="left:0px;right:0px;margin-left:auto;margin-right:auto;border-bottom: 1px solid #ffffff;width:100%;position:relative"></div>
<div class="box2">
<center>
<font style="font-family: Roboto, helvetica, arial, sans-serif;font-weight: 400;font-size: 13px;color:#000000">Your app has been created successfully!<br/><br/>
<br/>
<br/>
Thank you for using ApkCreator!<br/>
<a target="_blank" href="https://play.google.com/store/apps/details?id=com.apkcreator.fwd">Rate our app Here</a></font>
</center>
</div>
</div>
<div style="left:0px;right:0px;margin-left:auto;margin-right:auto;border-bottom: 1px solid #ffffff;width:100%;margin-top:20px;"></div>
<div class="push2"></div>
</div>
<center><a href="/index.html"><img src="images/backhome.png" style="margin-bottom:-12px;margin-top:3px;-webkit-tap-highlight-color: rgba(0,0,0, 0.0);outline: none;" /></a></center>
<center><font style="font-family:'Lucida Sans Unicode', 'Lucida Grande', sans-serif;font-size:11px;">COPYRIGHT © | 2014 FRANKWEBDESIGN</font></center>
</div>
</body>
</html>
