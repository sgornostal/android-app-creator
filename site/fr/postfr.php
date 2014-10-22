<?php
require '../androidbuild.php';
$perm1=$_POST['div1'];
$perm1=strip_tags($_POST['div1']);
$perm1=str_replace("&lt;","<",$perm1);
$perm1=str_replace("&gt;",">",$perm1);
$perm1=str_replace("&nbsp;","",$perm1);
$perm1=trim($perm1);
$about=$_POST['div2'];
$about=trim($about);
if($_POST['url']!="")
{  
if($_FILES['icon_file']['name']!="")
{
	if (!file_exists('images/attachments')) {
		mkdir('images/attachments');
	}
	$targetPath    = "images/attachments";
	$timetosave = date("YmdHis");
	$icon_file	= $timetosave."_".str_replace(array(' ', '-'), array('_','_'), basename($_FILES['icon_file']['name']));
	$_FILES['icon_file']['name'] = $photo;
	
	move_uploaded_file($_FILES['icon_file']['tmp_name'],"$targetPath/$icon_file");// or die('not upload');
	$file3 = pathinfo($targetPath."/".$icon_file);
	  
	$url = "http://".$_SERVER['HTTP_HOST']."/".$file3['dirname']."/".$file3['basename'];
	
}elseif($_POST['iconurl']!=""){

	$url = $_POST['iconurl'];
}

$Status = getStatus();
if(isset($_POST['splashsize'])) {
    $logotype=$_POST['splashsize'];
}
else {
    $logotype='choose';
}
if(isset($_POST['bgcolor'])) {
	$logocolor=$_POST['bgcolor'];
} else {
	$logocolor=$_POST['bgcolor'];
}	
if($Status != "") {
	$fields = array('url' => $_POST['url'], 'title' => $_POST['title'], 'icon' => $url, 'apptosd' =>$_POST['apptosd'], 'orientation' => $_POST['orientation'], 'statusbar' => $_POST['statusbar'] , 'lang' => $_POST['language'], 'email' => $_POST['email'] , 'version' => $_POST['version'], 'splash' => $_POST['splash'], 'logo' => $_POST['logo'], 'admob' => $_POST['admob'], 'admobid' => $_POST['id'], 'push' => $_POST['pushn'], 'pushid' => $_POST['id1'], 'pushkey' => $_POST['id2'], 'perm1' => $perm1, 'abouttext' => $about,'tabinfo' => $_POST['tabn1'],'logotype' => $logotype,'bgcolor' => $logocolor,'package' =>$_POST['package'], 'cache' => $_POST['cache'], 'browser' => $_POST['browser'],'zoomcontrol' => $_POST['zoom']);
	$res = buildApk($Status, $fields);
} 

 
header("Location: buildingfr.php?".$res);
exit;
}else{
header("Location: ".$_SERVER['HTTP_REFERER']."");
exit;
}
?>
