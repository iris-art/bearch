<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$userID = $_POST['user_id'];
$bandName = $_POST['band_name'];
$bandDescription = $_POST['band_description'];
$bandLocation = $_POST['band_location'];
$bandGenre = $_POST['band_genre'];

if($bandName == '' || $bandDescription == ''){
	echo 'Band Name or Description can not be empty' + $bandName + $bandDescription;
}else{
	$query = "select * from users where name = '$bandName'";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query));
	if(isset($recordExists)){
		echo 'Band already exists';
	}else{
		$query = "INSERT INTO bands (name, description, members, location, genre) VALUES ('$bandName', '$bandDescription', '$userID', '$bandLocation', '$bandGenre')";
		if(mysqli_query($con, $query)){
			echo 'Band created successfully';
		}else{
			echo mysqli_error($con);
		}
		$query1 = "UPDATE users SET Band = '$bandName' WHERE email = '$userID'";
		if(mysqli_query($con, $query1)){
			echo 'Band created successfully';
		}else{
			echo mysqli_error();
		}
	}


	mysqli_close($con);
}



?>