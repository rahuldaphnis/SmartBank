<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Welcome extends CI_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see https://codeigniter.com/user_guide/general/urls.html
	 */

	function __construct() {
		
		parent::__construct();
		$this->isloggedin();
		$this->load->model('bankmodel');		
	}

	public function index()
	{
		$this->load->view('home');
	}

	public function signup()
	{
		$query = $this->bankmodel->addBank();
		if($query)
		{
			$data = array('phone' => $this->input->post('phone'),
				'isloggedin' =>true
			 );
			$this->session->set_userdata($data);
			redirect(base_url().'dashboard');			
//			redirect(base_url().'verify');			
		}
		else
		{
			$this->index();
		}
	}

	public function login()
	{
		$query = $this->bankmodel->checkinfo();

		if($query)
		{
			$data = array('phone' => $this->input->post('phone'),
				'isloggedin' =>true
			 );
			$this->session->set_userdata($data);
			redirect(base_url().'dashboard');
		}
		else
		{
			redirect(base_url());
			//$this->index();
		}
	}

	function isloggedin()
	{
		$isloggedin = $this->session->userdata('isloggedin');
		if(isset($isloggedin) && $isloggedin==true)
		{
			redirect(base_url().'dashboard');
		}

	}

	public function logout()
	{
		echo "yes";
		$this->session->unset_userdata('user');
		$this->session->sess_destroy();
		redirect(base_url());
	}

}
