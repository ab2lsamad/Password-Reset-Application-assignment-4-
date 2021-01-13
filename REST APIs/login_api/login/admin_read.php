<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once '../dbconfig.php';
include_once '../admin.php';
$database = new Database();
$db = $database->connectDB();
$admin = new Admin($db);
$admin->admin_uname = isset($_GET['username']) ? $_GET['username'] : die();
$admin->read();
if($admin->admin_uname != null){

// create array
$arr = array(
"username" => $admin->admin_uname,
"password" => $admin->admin_pass
);

http_response_code(200);
print_r(json_encode(['user' => $arr]));
}
else{
http_response_code(404);
echo json_encode("User not found.");
}
?>