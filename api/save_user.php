<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$userName = $_POST['user_name'];
$userID1 = $_POST['user_id1'];
$userID = $_POST['user_id'];
$userLocation = $_POST['user_location'];
$userGenre = $_POST['user_genre'];
$userInstrument = $_POST['user_instrument'];
$imageURI = $_POST['image_uri'];
$userProvince = $_POST['user_province'];

if($userName == '' || $userID == '' ){
	echo 'User Name, ID or Password can not be empty';
}else{
	$query = "Update users SET name = '$userName', email = '$userID', location = '$userLocation', genre = '$userGenre', instrument = '$userInstrument', imageuri = '$imageURI', province = '$userProvince' WHERE email = '$userID1'";
	if(mysqli_query($con, $query)){
			echo 'User updated succesfully';
	}else{
		echo mysqli_error($con);
	}


	mysqli_close($con);
}



?>