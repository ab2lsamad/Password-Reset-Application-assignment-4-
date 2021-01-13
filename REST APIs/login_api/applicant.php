<?php

class Applicant {
	private $db;
	private $table = "Applicants";

	public $app_uname;
	public $app_pass;

	public function __construct($db)
	{
		$this->db = $db;
	}

	public function read()
	{
		$query = "SELECT * FROM ".$this->table." WHERE app_uname= '".$this->app_uname."';";
		$record = $this->db->query($query);
		$row = $record->fetch_assoc();

		$this->app_uname = $row['app_uname'];
		$this->app_pass = $row['app_pass'];
	}

	public function create()
	{
		$this->app_uname=htmlspecialchars(strip_tags($this->app_uname));
        $this->app_pass=htmlspecialchars(strip_tags($this->app_pass));

        $query = "INSERT INTO ".$this->table." SET app_uname= '".$this->app_uname."', app_pass= '".$this->app_pass."';";
        $this->db->query($query);
        if($this->db->affected_rows > 0)
        	return true;
        else
        	return false;
	}

	public function update()
	{
		$this->app_uname=htmlspecialchars(strip_tags($this->app_uname));
        $this->app_pass=htmlspecialchars(strip_tags($this->app_pass));

        $query = "UPDATE ".$this->table." SET app_pass= '".$this->app_pass."' WHERE app_uname= '".$this->app_uname."';";
        $this->db->query($query);
        if($this->db->affected_rows > 0)
        	return true;
        else
        	return false;
	}
}
?>