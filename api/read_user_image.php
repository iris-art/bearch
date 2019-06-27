<?php

define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');









$userID = $_GET['user_id'];

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

$query = "SELECT CONVERT(imageuri USING utf8) FROM users WHERE email = '$userID'";
	$result = mysqli_query($con, $query);
	$row = mysqli_fetch_array($result);
	if(isset($row)){
		echo $row['CONVERT(imageuri USING utf8)'];
	}else{
		echo mysqli_error($con);
	}

mysqli_close($con);





?>