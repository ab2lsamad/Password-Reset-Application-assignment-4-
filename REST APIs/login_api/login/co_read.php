<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../dbconfig.php';
include_once '../coordinator.php';
$database = new Database();
$db = $database->connectDB();
$coordinator = new Coordinator($db);
$coordinator->co_uname = isset($_GET['username']) ? $_GET['username'] : die();
$coordinator->read();
if($coordinator->co_uname != null){

// create array
$arr = array(
"username" => $coordinator->co_uname,
"password" => $coordinator->co_pass
);

http_response_code(200);
print_r(json_encode(['user' => $arr]));
}
else{
http_response_code(404);
echo json_encode("User not found.");
}
?>