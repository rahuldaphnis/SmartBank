<?php

class Usermodel extends CI_Model {
	
	//Give user details by his/her phone number
	function getUser($phone) {
		$this->db->where('phone',$phone);
		$query = $this->db->get('user_profile');
		$records = $query->result();
		return $records;
	}

	//Function to Create new User Entry during signup
	function addUser($data) {
		$query = $this->db->insert('user_profile',$data);
		return $query;
	}

	// Function to udpate profile of user
	function updateProfile($id,$data) {
		$this->db->where('userid',$id);
		$query = $this->db->update('user_profile',$data);
		return $query;
	}
}

?>