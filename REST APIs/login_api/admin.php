<?php

class Admin {
	private $db;
	private $table = "Admin";

	public $admin_uname;
	public $admin_pass;

	public function __construct($db)
	{
		$this->db = $db;
	}

	public function read()
	{
		$query = "SELECT * FROM ".$this->table." WHERE admin_uname= '".$this->admin_uname."';";
		$record = $this->db->query($query);
		$row = $record->fetch_assoc();

		$this->admin_uname = $row['admin_uname'];
		$this->admin_pass = $row['admin_pass'];
	}

	public function update()
	{
		$this->admin_uname=htmlspecialchars(strip_tags($this->admin_uname));
        $this->admin_pass=htmlspecialchars(strip_tags($this->admin_pass));

        $query = "UPDATE ".$this->table." SET admin_pass= '".$this->admin_pass."' WHERE admin_uname= '".$this->admin_uname."';";
        $this->db->query($query);
        if($this->db->affected_rows > 0)
        	return true;
        else
        	return false;
	}
}

?>