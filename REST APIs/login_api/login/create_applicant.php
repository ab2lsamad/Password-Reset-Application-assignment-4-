<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../dbconfig.php';
include_once '../applicant.php';
$database = new Database();
$db = $database->connectDB();
$applicant = new Applicant($db);

$data = json_decode(file_get_contents("php://input"));
$applicant->app_uname = $data->app_uname;
$applicant->app_pass = $data->app_pass;

if($applicant->create()){
echo 'Applicant Created';
} else{
echo 'Applicant Not Created';
}
?>