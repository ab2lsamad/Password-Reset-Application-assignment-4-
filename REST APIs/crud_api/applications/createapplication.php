<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../dbconfig.php';
include_once '../applications.php';

$database = new Database();
$db = $database->connectDB();
$application = new Applications($db);

$data = json_decode(file_get_contents("php://input"));

$application->app_uname = $data->app_uname;
$application->name = $data->name;
$application->rollno = $data->rollno;
$application->program = $data->program;
$application->dept = $data->dept;
$application->email = $data->email;
$application->subject = $data->subject;
$application->body = $data->body;
$application->date = $data->date;


if ($application->createApplication()) {
	http_response_code(200);
	print_r(json_encode(array('message' => 'Application Created')));
}
else {
	http_response_code(404);
	print_r(json_encode(array('message' => 'Application Not Created')));
}

?>