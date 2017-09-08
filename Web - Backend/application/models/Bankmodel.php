<?php

class Bankmodel extends CI_Model {

	// Fetch all banks information - Preferably Name
	function getAllBanks() {

		$this->db->select('bankid');
		$this->db->select('name');
		$query = $this->db->get('bank_profile');
		$records = $query->result();
		if($records!=NULL) {
			return $records;
		}
		else {
			return NULL;
		}
	}

	function getUserDetails($userid) {

		$this->db->where('userid',$userid);
		$query = $this->db->get('user_profile');
		$record = $query->result();
		foreach ($record as $r) {
			# code...
		}
		return $r;
	}

	function getBankId($phone) {
		$this->db->where('phone',$phone);
		$query = $this->db->get('bank_profile');
		$record = $query->result();
		foreach ($record as $r) {
			# code...
		}
		return $r->bankid;
	}

	//Get Users Data
	function getUsersData() {
		$phone = $this->session->userdata('phone');
		$bankid = $this->getBankId($phone);
		$this->db->where('bankid',$bankid);
		$query = $this->db->get('document');
		$record = $query->result();
		$finalresult = array();
		$i=0;
		foreach ($record as $r) {
			$i++;
			$final['sno'] = $i;
			$userrecord = $this->getUserDetails($r->userid);
			$final['name'] = $userrecord->name;
			$final['phone'] = $userrecord->phone;
			$final['email'] = $userrecord->email;
			$final['accountnumber'] = $r->accountnumber;
			$final['image'] = $r->image;
			$final['status'] = $r->status;
			$final['documentid'] = $r->documentid;
			array_push($finalresult, $final);
		}
		return $finalresult;
	}

	//Change Threshold Value of Bank
	function changeThreshold() {

		$phone = $this->session->userdata('phone');
		$threshold = $this->input->post('threshold');
		$data['threshold'] = $threshold;
		$this->db->where('phone',$phone);
		$query = $this->db->update('bank_profile',$data);
		header('Location: '.base_url().'dashboard');
	}

	// Fetch one bank name
	function getBank($bankid) {

		$this->db->select('name');
		$this->db->where('bankid',$bankid);
		$query = $this->db->get('bank_profile');
		$records = $query->result();
		if($records!=NULL) {
			return $records;
		}
		else {
			return NULL;
		}
	}

	// Get all accounts added by user of particular user id.
	function getUserAccounts($userid) {

		$status = 1;
		$this->db->where('userid',$userid);
		$this->db->where('status',$status);
		$query = $this->db->get('user_accounts');
		$records = $query->result();
		if($records!=NULL) {
			return $records;
		}
		else {
			return NULL;
		}
	}

	//Insert New User Account or Activate existing account
	function addUserAccount($account) {

		$this->db->where('userid',$account['userid']);
		$this->db->where('bankid',$account['bankid']);
		$query = $this->db->get('user_accounts');
		$records = $query->result();
		$result = array();
		if($records!=NULL) {
			foreach ($records as $r) {
			}
			$query1 = true;
			if($r->status==0) {
				$account['status'] = 1;
				$r->status=1;
				$this->db->where('userid',$account['userid']);
				$this->db->where('bankid',$account['bankid']);
				$query1 = $this->db->update('user_accounts',$account);
			}
			return $query1;
		}
		else {

			$query1 = $this->db->insert('user_accounts',$account);
			return $query1;
		}
	}

	function getUserDocuments($userid) {

		$this->db->where('userid',$userid);
		$query = $this->db->get('document');
		$records = $query->result();
		foreach ($records as $r) {
			$records1 = $this->getBank($r->bankid);
			foreach ($records1 as $r1) {
				# code...
			}
			$r->bankname = $r1->name;
		}
		return $records;
	}

	function getUserBankDocuments($userid,$bankid) {

		$this->db->where('userid',$userid);
		$this->db->where('bankid',$bankid);
		$query = $this->db->get('document');
		$records = $query->result();
		return $records;
	}

	//Add a new entry in documents table for recently uploaded document
	function addUserDocument($document) {

		$this->db->where('userid',$document['userid']);
		$this->db->where('bankid',$document['bankid']);
		$query = $this->db->get('document');
		$records = $query->result();
		$result = array();
		if($records!=NULL) {
			foreach ($records as $r) {
			}
			$query1 = true;
			if($r->status==0) {
				$this->db->where('userid',$document['userid']);
				$this->db->where('bankid',$document['bankid']);
				$query1 = $this->db->update('document',$document);
			}
			return $query1;
		}
		else {
			$query = $this->db->insert('document',$document);
			return $query;
		}

	}

	function getSingleDocument($documentid) {

		$this->db->where('documentid',$documentid);
		$query = $this->db->get('document');
		$records = $query->result();
		if($records!=NULL) {
			foreach ($records as $r) {
			}
			return $r;
		}
		else {
			return NULL;
		}
	}

	//Function to get threshold set by a particular bank
	function getBankThreshold($bankid) {
		$this->db->where('bankid',$bankid);
		$query = $this->db->get('bank_profile');
		$records = $query->result();
		if($records!=NULL) {
			foreach ($records as $r) {
			}
			return $r;			
		}
		else {
			return NULL;
		}
	}

	//Functions of Bank Portal Starting from Here................................

	function addBank()
	{
		$data = array('name' => $this->input->post('name'),
			'phone' => $this->input->post('phone'),
			'password' => $this->input->post('password'),
			'threshold' => $this->input->post('threshold'),
			'endpoint' => $this->input->post('endpoint'),
			);
		$insert = $this->db->insert('bank_profile', $data);
		return $insert;
	}

	function checkinfo()
	{
		$this->db->where('phone',$this->input->post('phone'));
		$this->db->where('password',$this->input->post('password'));
		$query = $this->db->get('bank_profile');
		if($query->num_rows()==1)
			return true;
	}

	function getBankData() {

		$this->db->where('phone',$this->session->userdata('phone'));
		$query = $this->db->get('bank_profile');
		$records = $query->result();
		return $records;
	}

}

?>