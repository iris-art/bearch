<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

$bandName = $_GET['band_name'];

	
if ($bandName == "None"){
    $query = "SELECT * FROM bands";
    $result = mysqli_query($con, $query);
    while($row = mysqli_fetch_array($result)) {
 
	echo  "~",$row["name"] , "~", $row["description"], "~", $row["location"], "~", $row["genre"], "~", $row["requests"], "~", $row['members'] , "%";
}
}
else{
	$query = "SELECT CONVERT(imageuri USING utf8) FROM bands WHERE name = '$bandName'";
	$result = mysqli_query($con, $query);
	$row = mysqli_fetch_array($result);
	$imageURI = $row['CONVERT(imageuri USING utf8)'];
	if(isset($row)){
		echo $row['CONVERT(imageuri USING utf8)'];
	
	}else{
		echo mysqli_error($con);
	}
    $query = "SELECT * FROM bands WHERE name = '$bandName'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_array($result);
    echo "~",$row["name"] , "~", $row["description"], "~", $row["location"], "~", $row["genre"] , "~", $row["requests"], "~", $row['members'], "~"; 
}

// echo (json_encode($flag));

mysqli_close($con);
?>
