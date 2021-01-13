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

$application->id = $data->id;
$application->app_uname = $data->app_uname;
$application->name = $data->name;
$application->rollno = $data->rollno;
$application->program = $data->program;
$application->dept = $data->dept;
$application->email = $data->email;
$application->subject = $data->subject;
$application->body = $data->body;
$application->date = $data->date;
$application->app_status = $data->app_status;
$application->co_approval = $data->co_approval;
$application->co_comment = $data->co_comment;
$application->ad_approval = $data->ad_approval;
$application->ad_comment = $data->ad_comment;

if ($application->update()) {
	print_r(json_encode(array('message' => 'Application Updated')));
}
else {
	print_r(json_encode(array('message' => 'Application Not Updated')));
}

?>