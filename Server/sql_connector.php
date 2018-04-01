<?php
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);

DEFINE('DB_USER','poweruser');
DEFINE('DB_PASSWORD','powerpass');
DEFINE('DB_HOST','localhost');
DEFINE('DB_NAME','powerData');

//Depending on style, index.php must also be changed
//Default = OOP
//Procedural
/*
$dbc = @mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME)
	OR die('Could not connect to MySQL <br />' . mysqli_connect_error());

*/
//OOP
$dbc = new mysqli("localhost", "poweruser", "powerpass", "powerData");

if($dbc->connect_errno) {
	die('Could not connect to MySQL <br />' . $dbc->connect_error);
}
?>
