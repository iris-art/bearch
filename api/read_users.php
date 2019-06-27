<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");


$query = "SELECT * FROM users";
$result = mysqli_query($con, $query);
while($row = mysqli_fetch_array($result)) {
	
	
	if(isset($row)){
		$response['email'] = $row['email'];
		$response['name'] = $row['name'];
		$response['location'] = $row['location'];
		$response['band'] = $row['Band'];
		$response['province'] = $row['province'];
		$response['instrument'] = $row['instrument'];
		$response['genre'] = $row['genre'];
	}else{
		echo mysqli_error($con);
	}
	
	echo json_encode($response), "~";
}



mysqli_close($con);
?>
