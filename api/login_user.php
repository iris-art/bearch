<?php

define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$userID = $_POST['user_id'];
	$userPassword = $_POST['user_password'];


	if($userID == '' || $userPassword == ''){
		echo "fail";
		exit;
	}

	
	
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT CONVERT(imageuri USING utf8) FROM users WHERE email = '$userID'";
	$result = mysqli_query($con, $query);
	$row = mysqli_fetch_array($result);
	$imageURI = $row['CONVERT(imageuri USING utf8)'];
	if(isset($row)){
		echo $row['CONVERT(imageuri USING utf8)'];
	
	}else{
		echo mysqli_error($con);
	}
	$query = "SELECT * FROM users WHERE email = '$userID' AND password = '$userPassword'";
	$result = mysqli_query($con, $query);
    $data = mysqli_fetch_array($result);
    
	if(isset($data)){
		echo "~" ,$data["email"] , "~", $data["name"] ,"~", $data["genre"] ,"~", $data["instrument"] ,"~", $data["Band"], "~", $data["location"], "~", $data["province"], "~";
	
	}else{
		echo "fail";
	}

	mysqli_close($con);
}



?>