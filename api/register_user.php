<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$userName = $_POST['user_name'];
$userID = $_POST['user_id'];
$userPassword = $_POST['user_password'];
$userLocation = $_POST['user_location'];
$userGenre = $_POST['user_genre'];
$userInstrument = $_POST['user_instrument'];
$userProvince = $_POST['user_province'];

if($userName == '' || $userID == '' || $userPassword == ''){
	echo 'User Name, ID or Password can not be empty';
}else{
	$query = "select * from users where email = '$userID'";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query));
	if(isset($recordExists)){
		echo 'User already exists';
	}else{
		$query = "INSERT INTO users (name, email, password, location, instrument, genre, province) VALUES ('$userName', '$userID', '$userPassword','$userLocation','$userInstrument', '$userGenre', '$userProvince')";
		if(mysqli_query($con, $query)){
			echo 'User registered successfully';
		}else{
			echo mysqli_error();
		}
	}


	mysqli_close($con);
}



?>