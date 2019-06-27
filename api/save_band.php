<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$bandName = $_POST['band_name'];
$bandGenre = $_POST['band_genre'];
$bandLocation = $_POST['band_location'];
$bandDescription = $_POST['band_description'];
$bandName1 = $_POST['band_name1'];
$imageURI = $_POST['image_uri'];


if($bandName == ''){
	echo 'Band Name can not be empty';
}else{
	$query = "UPDATE bands SET name = '$bandName1', genre = '$bandGenre', location = '$bandLocation' , description = '$bandDescription', imageuri='$imageURI' WHERE name = '$bandName'";
	if(mysqli_query($con, $query)){
			echo 'Band updated successfully';
	}else{
		echo 'Oops, something went wrong!';
	}


	mysqli_close($con);
}



?>