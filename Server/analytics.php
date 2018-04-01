<form action="analytics.php" method="post">
<input type=number name="userid" />
<input type=text name="password" />
<input type=submit name="submit" value="Submit"/>
</form>
<?php
/**
 The form given above was supposed to be an authentication system
 so that any user, with their password could login to the website
 as an alternative means of veiwing user analytics in case no
 Android app
 The superuser / system admin account may be able to view all data
 */

ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);

$sqlconnector_script = 'sql_connector.php';
$page = $_SERVER['PHP_SELF'];
$sec = "2";

require_once($sqlconnector_script);

$selall = "SELECT * FROM sensor";
//Personalize data output maybe
//USing WHERE clause
$selall .= ";";

$tableall = $dbc->query($selall);

$data = $tableall->fetch_all();

echo '<table>';
echo '<tr>';
    echo '<th>' . "id" . '</th>';
    echo '<th>' . "uinfo" . '</th>';
    echo '<th>' . "charge" . '</th>';
    echo '<th>' . "discharge" . '</th>';
    echo '<th>' . "reserve" . '</th>';
    echo '<th>' . "capacity" . '</th>';
    echo '<th>' . "redirected" . '</th>';
echo '</tr>';
foreach($data as &$row){
    echo '<tr>';
    foreach ($row as &$attr) {
        echo '<td>' . $attr . '</td>';
    }
    echo '</tr>';
}
echo '</table>';
$dbc->close();
?>
<html>
    <head>
    <meta http-equiv="refresh" content="<?php echo $sec?>;URL='<?php echo $page?>'">
    </head>
</html>