<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Builded | ApkCreator - App Maker Pro</title>
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

<div id="phase2" class="container2" style="display:block;">
<div class="content2">
<div style="height:53px;"><center>
<font style="font-family: Roboto, helvetica, arial, sans-serif;font-weight: 400;color:#ffffff;font-size : 15px;top:15px;position:relative">Application created!</font><br/><br/>
</center></div>
<div style="left:0px;right:0px;margin-left:auto;margin-right:auto;border-bottom: 1px solid #ffffff;width:100%;position:relative"></div>
<div class="box2">
<center>
<font style="font-family: Roboto, helvetica, arial, sans-serif;font-weight: 400;font-size: 13px;color:#000000">Your app has been created successfully!<br/><br/>
Status:
<?php 
if($_GET['status'] == 'success') {
	echo '<i style="color:#49E20E;">Apk file created</i><br><br><img src=images/ctd.png width=96 height=11><br><a href=http://'.$_GET["url"].'><img src=images/apkfile.png width=40 height=40> <br> '.$_GET["name"].'</a>';
} else {
	echo '<i style="color:#fde517;">Apk build error</i><br><br><img src=images/apkfile2.png width=40 height=40> <br>Quick Troubleshoot<br>-Do not use special character<br>-Check website URL format<br>-Check package name format<br>-See our website FAQ for help ';
}
?>
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
<div id="phase22" class="footer2" style="display:block;">
<center><a href="/index.html"><img src="images/backhome.png" style="margin-bottom:-12px;margin-top:3px;-webkit-tap-highlight-color: rgba(0,0,0, 0.0);outline: none;" /></a></center>
<center><font style="font-family:'Lucida Sans Unicode', 'Lucida Grande', sans-serif;font-size:11px;">COPYRIGHT © | 2014 FRANKWEBDESIGN</font></center>
</div>
</body>
</html>
