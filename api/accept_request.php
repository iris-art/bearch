<?php


define('HOST', 'remotemysql.com:3306');
define('USER', '79nPzcOK8a');
define('PASSWORD', 'WG5JPU9yD9');
define('DB', '79nPzcOK8a');


$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

$string = $_POST['requests'];
$string1 = $_POST['members'];
$bandName = $_POST['band_name'];
$userID = $_POST['user_id'];

$query = "UPDATE bands SET requests = '$string', members='$string1' WHERE name = '$bandName'";
$result = mysqli_query($con, $query);
$query = "UPDATE users set band = '$bandName' WHERE email = '$userID'";
$result = mysqli_query($con, $query);


// echo (json_encode($flag));

mysqli_close($con);
?>
