<?php
header('Content-Type: application/json');
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);

//Dummy data
/*
$id = 123;
$uinfo = "codegod";
$charge = 10;
$discharge = 6;
$reserve = 10500;
$capacity = 25000;
*/

//SQL connector
$sqlconnector_script = 'sql_connector.php';

/**
 Parameter extraction
*/ //----------------------------------------------------------------------

$index = 1;
foreach ($_GET as $name => $value) {
    $param[$index] = $value;
    $index+=1;
}

/**
 Calculate redirected_pwr
*/ //----------------------------------------------------------------------

$redirected_pwr = 0;
$netcharge = $param[3] - $param[4];

if($netcharge <= 0){
  //System is not self-sustained
  $redirected_pwr *= -1;
  $redirected_pwr = (1-($param[5]/$param[6])) * 250;
  if($redirected_pwr < 80)
    $redirected_pwr = 0;
}

/**
 Push to sensor
*/ //----------------------------------------------------------------------
require_once($sqlconnector_script);

$del = "DELETE FROM sensor WHERE id='{$param[1]}';";
$dbc->query($del);

$ins = "INSERT INTO sensor values('{$param[1]}',
  NULLIF('{$param[2]}',''),
  NULLIF('{$param[3]}',''),
  NULLIF('{$param[4]}',''),
  NULLIF('{$param[5]}',''),
  NULLIF('{$param[6]}',''),
  NULLIF('{$redirected_pwr}','')
)";

$dbc->query($ins) or die($dbc->errno);

/**
 JSONify data
*/ //----------------------------------------------------------------------

class Template{
  public $id;
  public $uinfo;
  public $redirected;
  function __construct($param, $redirected_pwr){
    $this->id = $param[1];
    $this->uinfo = $param[2];
    $this->redirected = $redirected_pwr;
  }
}
$customobj = new Template($param, $redirected_pwr);

//Display the custom response
echo json_encode($customobj);

//----------------------------------------------------------------------

$dbc->close();

?>
