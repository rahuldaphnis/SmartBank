<?php

class BankDryApi extends CI_Controller {

	public function getData() {

		$accountnumber = $this->input->post('accountnumber');
		$ifsccode = $this->input->post('ifsccode');

		$passbook = array();

		if(($accountnumber=='1234567890')&&($ifsccode=='ABCD1234')) {

			$data['ownername'] = 'Rahul Malhotra';
			$data['accountstatus'] = 'active';
			$data['phone'] = '9555705671';
			$data['accountnumber'] = '1234567890';
			$data['balance'] = '20000';
			$data['currency'] = 'IND';
			$data['accounttype'] = 'SB';
			$data['createddate'] = '20/07/2016';

			//Passbook Entry 1
			$passbookdata1['date'] = '20/07/2016';
			$passbookdata1['particulars'] = 'Cash Added By Self';
			$passbookdata1['debit'] = '1000';
			$passbookdata1['credit'] = '0';
			$passbookdata1['balance'] = '1000';
			
			array_push($passbook, $passbookdata1);

			//Passbook Entry 2
			$passbookdata2['date'] = '25/08/2016';
			$passbookdata2['particulars'] = 'Cash Withdrawl By Self';
			$passbookdata2['debit'] = '0';
			$passbookdata2['credit'] = '500';
			$passbookdata2['balance'] = '500';
			
			array_push($passbook, $passbookdata2);

			//Passbook Entry 3
			$passbookdata3['date'] = '29/11/2016';
			$passbookdata3['particulars'] = 'Cash Added By Self';
			$passbookdata3['debit'] = '15000';
			$passbookdata3['credit'] = '0';
			$passbookdata3['balance'] = '15500';
			
			array_push($passbook, $passbookdata3);

			//Passbook Entry 4
			$passbookdata4['date'] = '04/04/2017';
			$passbookdata4['particulars'] = 'Cash Withdrawl By Self';
			$passbookdata4['debit'] = '0';
			$passbookdata4['credit'] = '500';
			$passbookdata4['balance'] = '15000';
			
			array_push($passbook, $passbookdata4);

			//Passbook Entry 5
			$passbookdata5['date'] = '20/07/207';
			$passbookdata5['particulars'] = 'Cash Added By Self';
			$passbookdata5['debit'] = '5000';
			$passbookdata5['credit'] = '0';
			$passbookdata5['balance'] = '20000';
			
			array_push($passbook, $passbookdata5);

			$data['success'] = '1';
			$data['passbook'] = $passbook;
		}

		echo json_encode($data);
	}
}

?>