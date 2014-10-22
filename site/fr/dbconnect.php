<?php
    $connect = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
    if(!$connect) {
        die('Failed to connect to server: ' . mysql_error());
    }
    
    //Select database
    $db = mysql_select_db(DB_DATABASE);
    if(!$db) {
        die("Unable to select database");
    }
?>