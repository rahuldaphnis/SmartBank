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

	/*  
		to get the user documents - Bank Id will be verified at frontend part so
		that same function can be used to give suggestion to user to use existing documents
		for verification in case of submitting documents to any other bank
	*/
	function getUserDocuments($userid) {

		$this->db->where('userid',$userid);
		$query = $this->db->get('document');
		$records = $query->result();
		return $records;
	}

	//Add a new entry in documents table for recently uploaded document
	function addUserDocument($document) {

		$query = $this->db->insert('document',$document);
		return $query;

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
}

?>