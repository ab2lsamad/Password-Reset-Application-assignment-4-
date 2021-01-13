<?php
class Database {
	private $db;

	public function connectDB(){
		$this->db = null;
		try
		{
			$this->db = new mysqli('localhost','root','','applicationsdb');
			#echo "Connection Successful";
		}
		catch(Exception $e)
		{
			echo "Connection Failed".$e->getMessage();
		}

		return $this->db;
	}
}

?>