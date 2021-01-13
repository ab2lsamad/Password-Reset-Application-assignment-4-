<?php

class Coordinator {
	private $db;
	private $table = "Coordinator";

	public $co_uname;
	public $co_pass;

	public function __construct($db)
	{
		$this->db = $db;
	}

	public function read()
	{
		$query = "SELECT * FROM ".$this->table." WHERE co_uname= '".$this->co_uname."';";
		$record = $this->db->query($query);
		$row = $record->fetch_assoc();

		$this->co_uname = $row['co_uname'];
		$this->co_pass = $row['co_pass'];
	}

	public function update()
	{
		$this->co_uname=htmlspecialchars(strip_tags($this->co_uname));
        $this->co_pass=htmlspecialchars(strip_tags($this->co_pass));

        $query = "UPDATE ".$this->table." SET co_pass= '".$this->co_pass."' WHERE co_uname= '".$this->co_uname."';";
        $this->db->query($query);
        if($this->db->affected_rows > 0)
        	return true;
        else
        	return false;
	}
}

?>