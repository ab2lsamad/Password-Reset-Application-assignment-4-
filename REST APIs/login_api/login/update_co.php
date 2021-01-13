<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../dbconfig.php';
include_once '../coordinator.php';
$database = new Database();
$db = $database->connectDB();
$coordinator = new Coordinator($db);

$data = json_decode(file_get_contents("php://input"));
$coordinator->co_uname = $data->co_uname;
$coordinator->co_pass = $data->co_pass;

if($coordinator->update()){
echo 'Coordinator Updated';
} else{
echo 'Coordinator Not Updated';
}
?>