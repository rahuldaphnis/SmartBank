<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends CI_Controller {

	function __construct() {
		
		parent::__construct();
		$this->load->model('bankmodel');	
		$this->isloggedin();	
	}

	public function approveDocument() {
		$documentid = $this->input->post('documentid');
		$this->db->where('documentid',$documentid);
		$data['status'] = '1';
		$query = $this->db->update('document',$data);
		redirect(base_url().'/Dashboard');
	}

	public function index()
	{
		$records = $this->bankmodel->getBankData();
		$records1 = $this->bankmodel->getUsersData();
		$data['banks'] = $records;
		$data['users'] = $records1;
		$this->load->view('search',$data);
	}

	public function changeThreshold() {
		$this->bankmodel->changeThreshold();
	}

	public function logout()
	{
		$this->session->unset_userdata('user');
		$this->session->sess_destroy();
		redirect(base_url());
	}

	function isloggedin()
	{
		$isloggedin = $this->session->userdata('isloggedin');
/*		$this->load->model('orgdata');
		$recordsp = $this->orgdata->getdata();
		foreach ($recordsp as $data) {
			# code...
		}
*/		if(!isset($isloggedin) || $isloggedin!=true)
		{
			redirect(base_url());
		}
/*		if(($data->verifyemail=="0")||($data->verifyphone=="0"))
		{
			redirect(base_url()."verify");
		}
*/	}

}
