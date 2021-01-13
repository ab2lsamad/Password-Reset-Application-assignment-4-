<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../dbconfig.php';
include_once '../admin.php';
$database = new Database();
$db = $database->connectDB();
$admin = new Admin($db);

$data = json_decode(file_get_contents("php://input"));
$admin->admin_uname = $data->admin_uname;
$admin->admin_pass = $data->admin_pass;

if($admin->update()){
echo 'Admin Updated';
} else{
echo 'Admin Not Updated';
}
?>