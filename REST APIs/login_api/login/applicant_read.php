<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers");
include_once '../dbconfig.php';
include_once '../applicant.php';
$database = new Database();
$db = $database->connectDB();
$applicant = new Applicant($db);
$applicant->app_uname = isset($_GET['username']) ? $_GET['username'] : die();
$applicant->read();
if($applicant->app_uname != null){

// create array
$arr = array(
"username" => $applicant->app_uname,
"password" => $applicant->app_pass
);

http_response_code(200);
echo json_encode(array('user' => $arr));
}
else{
http_response_code(404);
echo json_encode("User not found.");
}
?>