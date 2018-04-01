<?php
//Its servicing in JSON format
header('Content-Type: application/json');

ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);

$sqlconnector_script = 'sql_connector.php';

//INclude the database handler
require_once($sqlconnector_script);

$selall = "SELECT * FROM assignid;";
$tablev = $dbc->query($selall);
$data = $tablev->fetch_all();

$variable = $data[0][0];
$variable += 1;

$dbc->query("DELETE FROM assignid");

$ins = "INSERT INTO assignid VALUES('{$variable}');";
$dbc->query($ins);

class uhhh{
    public $variable;
    function __construct($variable){
        $this->variable = $variable;
    }
}
$jsonvar = new uhhh($variable);

echo json_encode($jsonvar);

$dbc->close();

?>