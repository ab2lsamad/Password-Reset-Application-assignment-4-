<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../dbconfig.php';
include_once '../applications.php';

$database = new Database();
$db = $database->connectDB();
$application = new Applications($db);

$data = $application->getAdminData();

$itemCount = $data->num_rows;

if($itemCount > 0){
$arr = array();
$arr["data"] = array();
while ($row = $data->fetch_assoc())
{
array_push($arr["data"], $row);
}
print_r(json_encode($arr));
}
else{
http_response_code(404);
print_r(json_encode(array("message" => "No record found.")));
}


?>