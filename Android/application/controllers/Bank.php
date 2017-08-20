<?php

class Bank extends CI_Controller {

	public function __construct() {
		parent::__construct();
		$this->load->model('bankmodel');
	}

	//Get details of all banks registered with the application
	public function getBanks() {

		$result = array();
		$records = $this->bankmodel->getAllBanks();

		if($records!=NULL) {
			$result['success'] = '1';
			$result['data'] = $records;
		}
		else {
			$result['success'] = '0';
			$result['data'] = NULL;
		}

		echo json_encode($result);

	}

	// Get details of bank accounts of a particular user by user id
	public function getSavedBanks() {

		$userid = $this->input->post('userid');
		$records = $this->bankmodel->getUserAccounts($userid);
		if($records!=NULL) {
			$result['success'] = '1';
			$result['data'] = $records;
		}
		else {
			$result['success'] = '0';
			$result['data'] = NULL;
		}

		echo json_encode($result);
	}

	// Get details of passbook and bank account of a user by account id and ifsc code
	public function addAccount() {

		$phone = $this->input->post('phone');
		$userid = $this->input->post('userid');
		$bankid = $this->input->post('bankid');
		$accountnumber = $this->input->post('accountnumber');
		$ifsccode = $this->input->post('ifsccode');

		// Call Bank Dry API - Curl [ Send account number and ifsc code ]
		$url = 'http://localhost/MyBank/index.php/BankDryApi/getData';
		$curl = curl_init($url);
		$curl_post_data = array(
				'accountnumber' => $accountnumber,
				'ifsccode' => $ifsccode
			);
		curl_setopt($curl,CURLOPT_RETURNTRANSFER,true);
		curl_setopt($curl, CURLOPT_POST, true);
		curl_setopt($curl, CURLOPT_POSTFIELDS, $curl_post_data);
		$curl_response = curl_exec($curl);

		if($curl_response==false) {
			$info = curl_getinfo($curl);
			curl_close($curl);
			die('Error occured during curl execution. Additional info - '.var_export($info));
		}
		curl_close($curl);

		$account = json_decode($curl_response);

		$type = $account->accounttype;

		$result = array();
		if($phone==$account->phone) {
			$result['success'] = '1';
			$result['data'] = $account;
		}
		else {
			$result['success'] = '0';
			$result['data'] = NULL;
		}

		$accountdata['number'] = $accountnumber;
		$accountdata['type'] = $type;
		$accountdata['ifsccode'] = $ifsccode;
		$accountdata['status'] = 1;
		$accountdata['userid'] = $userid;
		$accountdata['bankid'] = $bankid;

		$query = $this->bankmodel->addUserAccount($accountdata);

		if($query) {
			$result['accountsaved'] = '1';
		}
		else {
			$result['accountsaved'] = '0';
		}

		echo json_encode($result);
	}

	//Get details of documents of user's documents
	public function getUserDocuments() {

		$userid = $this->input->post('userid');
		$records = $this->bankmodel->getUserDocuments($userid);
		$result = array();
		if($records!=NULL) {
			$result['success'] = '1';
			$result['data'] = $records;
		}
		else {
			$result['success'] = '0';
			$result['data'] = NULL;
		}

		echo json_encode($result);
	}

	//Add existing user document for a different bank account
	public function addExistingDocument() {

		$userid = $this->input->post('userid');
		$bankid = $this->input->post('bankid');
		$type = $this->input->post('type');
		$accountnumber = $this->input->post('accountnumber');
		$documentid = $this->input->post('documentid');
		$r = $this->bankmodel->getSingleDocument($documentid);
		$imgurl = $r->image;
		if(($r!=NULL)&&($r->status==1)&&($r->bankid!=$bankid)) {

			$document['userid'] = $userid;
			$document['bankid'] = $bankid;
			$document['type'] = $type;
			$document['accountnumber'] = $accountnumber;
			$document['image'] = $imgurl;

			$r1 = $this->bankmodel->getBankThreshold($bankid);

			//Call IBM Watson Api and check Threshold
			$url = 'https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=5ecfc6103bc1992a17ab5492dc12caf7d18c515e&version=2016-05-20&url='.$imgurl.'&owners=IBM,Me&classifier_ids=IdAuthentication_658689001&threshold=0.0';
			$curl = curl_init($url);
			curl_setopt($curl,CURLOPT_RETURNTRANSFER,true);
			curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
			$curl_response = curl_exec($curl);

			if($curl_response==false) {
				$info = curl_error($curl);
				curl_close($curl);
				die('Error occured during curl execution. Additional info - '.var_export($info));
			}
			curl_close($curl);

			$ibmresponse = json_decode($curl_response);
			$data = array();
			$data = $ibmresponse->images;
			$datap = $data['0'];
			$datak =$datap->classifiers;
			$datao = $datak['0'];
			$datat = $datao->classes;
			$fakeaadhar = $datat['0'];
			$fakepan = $datat['1'];
			$originalaadhar = $datat['2'];
			$originalpan = $datat['3'];
			$fakeaadharscore = $fakeaadhar->score;
			$fakepanscore = $fakepan->score;
			$originalaadharscore = $originalaadhar->score;
			$originalpanscore = $originalpan->score;
			$threshold = ($r1->threshold)/100;
			//echo 'p'.$originalaadharscore.'k'.$threshold;
			$query = NULL;
			if($type==1) {
				if($originalaadharscore>$threshold) {
					//echo 'blabla';
					$document['status'] = 1;
					$query = $this->bankmodel->addUserDocument($document);
				}
			}
			else if($type==2) {
				if($originalpanscore>$threshold) {
					$document['status'] = 1;
					$query = $this->bankmodel->addUserDocument($document);
				}				
			}
			if($query!=NULL) {
				$result['success'] = '1';
			}
			else {
				$result['success'] = '0';
			}
		}
		else {
				$result['success'] = '0';
		}

		echo json_encode($result);
	}

	//Add a new document of a particular user
	public function addNewDocument() {

		$userid = $this->input->post('userid');
		$bankid = $this->input->post('bankid');
		$type = $this->input->post('type');
		$accountnumber = $this->input->post('accountnumber');

		$image = $this->input->post('image');
		$decodedimage = base64_decode($image);
		if($image!=NULL) {
			if($document['type']==1) {
				$p = file_put_contents('uploads/aadharcards/bankid'.($bankid).'userid'.($userid).'jpg', $decodedimage);
				$imgurl = base_url().'uploads/aadharcards/bankid'.($bankid).'userid'.($userid).'jpg';
			}
			else if($document['type']==2) {
				$p = file_put_contents('uploads/pancards/bankid'.($bankid).'userid'.($userid).'jpg', $decodedimage);
				$imgurl = base_url().'uploads/pancards/bankid'.($bankid).'userid'.($userid).'jpg';
			}
			else {
				$p=NULL;
			}
		}
		else {
			$p=NULL;
		}

		$document['userid'] = $userid;
		$document['bankid'] = $bankid;
		$document['type'] = $type;
		$document['accountnumber'] = $accountnumber;
		$document['image'] = $imgurl;

		$r = $this->bankmodel->getBankThreshold($bankid);

		//		if($r->threshold>)

		//Call IBM Watson Api and check Threshold



		//$document['status'] = Set  according to threshold of bank

		if($p!=NULL) {
			$query = $this->bankmodel->addUserDocument($document);
		}
		else {
			$query = NULL;
		}

		if($query!=NULL) {
			$result['success'] = '1';
		}
		else {
			$result['success'] = '0';
		}

		echo json_encode($result);

	}

}

?>