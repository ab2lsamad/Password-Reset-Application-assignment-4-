<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once '../dbconfig.php';
include_once '../applications.php';

$database = new Database();
$db = $database->connectDB();
$application = new Applications($db);

$application->getLast();

if ($application->id != null) {
	$arr = array(
		'id' => $application->id,
		'app_uname' => $application->app_uname,
		'name' => $application->name,
		'rollno' => $application->rollno,
		'program' => $application->program,
		'dept' => $application->dept,
		'email' => $application->email,
		'subject' => $application->subject,
		'body' => $application->body,
		'date' => $application->date,
		'app_status' => $application->app_status,
		'co_approval' => $application->co_approval,
		'co_comment' => $application->co_comment,
		'ad_approval' => $application->ad_approval,
		'ad_comment' => $application->ad_comment
	);
	http_response_code(200);
	print_r(json_encode($arr));
}
else{
   http_response_code(404);
   print_r(json_encode(array("message" => "No record found.")));
}

?>