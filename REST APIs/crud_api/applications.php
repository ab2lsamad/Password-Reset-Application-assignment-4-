<?php

class Applications {
	private $db;
	private $table = "applications";

	public $id;
	public $app_uname;
	public $name;
	public $rollno;
	public $program;
	public $dept;
	public $email;
	public $subject;
	public $body;
	public $date;
	public $app_status;
	public $co_approval;
	public $co_comment;
	public $ad_approval;
	public $ad_comment;

	public function __construct($db)
	{
		$this->db = $db;
	}

	public function createApplication()
	{
		$this->name = htmlspecialchars(strip_tags($this->name));
		$this->rollno = htmlspecialchars(strip_tags($this->rollno));
		$this->program = htmlspecialchars(strip_tags($this->program));
		$this->dept = htmlspecialchars(strip_tags($this->dept));
		$this->email = htmlspecialchars(strip_tags($this->email));
		$this->subject = htmlspecialchars(strip_tags($this->subject));
		$this->body = htmlspecialchars(strip_tags($this->body));
		$this->date = htmlspecialchars(strip_tags($this->date));

		$query = "INSERT INTO ".$this->table." SET app_uname='".$this->app_uname."', name='".$this->name."', rollno='".$this->rollno."', program='".$this->program."', dept='".$this->dept."', email='".$this->email."', subject='".$this->subject."', body='".$this->body."', date='".$this->date."';";
		$this->db->query($query);
		if ($this->db->affected_rows > 0) {
			return true;
		}
		else {
			print("Error: ".$this->db->error."\n");
			return false;
		}
	}

	public function getSingleUser()
	{
		$query = "SELECT * FROM ".$this->table." WHERE app_uname= '".$this->app_uname."';";
		$record = $this->db->query($query);
		return $record;
	}

	public function getAllData()
	{
		$query = "SELECT * FROM ".$this->table;
		$record = $this->db->query($query);
		return $record;
	}

	public function getAdminData()
	{
		$query = "SELECT * FROM ".$this->table." WHERE co_approval= 'Approve';";
		$record = $this->db->query($query);
		return $record;
	}

	public function getLast()
	{
		$query = "SELECT * FROM ".$this->table." ORDER BY id DESC LIMIT 1;";
		$record = $this->db->query($query) or die($this->db->error);
		$row = $record->fetch_assoc();

		$this->id = $row['id'];
		$this->app_uname = $row['app_uname'];
		$this->name = $row['name'];
		$this->rollno = $row['rollno'];
		$this->program = $row['program'];
		$this->dept = $row['dept'];
		$this->email = $row['email'];
		$this->subject = $row['subject'];
		$this->body = $row['body'];
		$this->date = $row['date'];
		$this->app_status = $row['app_status'];
		$this->co_approval = $row['co_approval'];
		$this->co_comment = $row['co_comment'];
		$this->ad_approval = $row['ad_approval'];
		$this->ad_comment = $row['ad_comment'];
	}

	public function update()
	{
		$this->name = htmlspecialchars(strip_tags($this->name));
		$this->rollno = htmlspecialchars(strip_tags($this->rollno));
		$this->program = htmlspecialchars(strip_tags($this->program));
		$this->dept = htmlspecialchars(strip_tags($this->dept));
		$this->email = htmlspecialchars(strip_tags($this->email));
		$this->subject = htmlspecialchars(strip_tags($this->subject));
		$this->body = htmlspecialchars(strip_tags($this->body));
		$this->date = htmlspecialchars(strip_tags($this->date));
		$this->app_status = htmlspecialchars(strip_tags($this->app_status));
		$this->co_approval = htmlspecialchars(strip_tags($this->co_approval));
		$this->co_comment = htmlspecialchars(strip_tags($this->co_comment));
		$this->ad_approval = htmlspecialchars(strip_tags($this->ad_approval));
		$this->ad_comment = htmlspecialchars(strip_tags($this->ad_comment));

		$query = "UPDATE ".$this->table." SET app_uname= '".$this->app_uname."', name= '".$this->name."', rollno= '".$this->rollno."', program= '".$this->program."', dept= '".$this->dept."', email= '".$this->email."', subject= '".$this->subject."', body= '".$this->body."', date= '".$this->date."', app_status= '".$this->app_status."', co_approval= '".$this->co_approval."', co_comment= '".$this->co_comment."', ad_approval= '".$this->ad_approval."', ad_comment= '".$this->ad_comment."' WHERE id= ".$this->id.";";

		$this->db->query($query);
		if ($this->db->affected_rows > 0) {
			return true;
		}
		else {
			print("Error: ".$this->db->error."\n");
			return false;
		}
	}
}

?>