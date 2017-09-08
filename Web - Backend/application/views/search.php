<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="bootstrap-4.0.0-beta-dist/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="bootstrap-4.0.0-beta-dist/css/bootstrap-grid.min.css">
	<link rel="stylesheet" type="text/css" href="bootstrap-4.0.0-beta-dist/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
	<script type="text/javascript" src="bootstrap-4.0.0-beta-dist/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="bootstrap-4.0.0-beta-dist/js/bootstrap.js"></script>
	<title>Bank Portal - Search Users</title>
</head>
<body>
<div class="container">
<br />
<div class="row">
<div class="col-sm-6"><h1 style="color:blue;"><b><?php foreach ($banks as $bank) {
}
echo $bank->name; ?></b></h1></div>
<div class="col-sm-4">
  <?php echo form_open('Dashboard/changeThreshold'); ?>
  <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-user">Threshold Value</i></span>
        <?php echo form_input(['type' => 'number', 'class' => 'form-control', 'placeholder' => 'Threshold value for documents','value' => $bank->threshold, 'name' => 'threshold', 'required' => 'true']); ?>
	      <span class="input-group-btn">
        <?php echo form_submit(['class' => 'btn btn-primary', 'value' => 'Submit', 'name' => 'submit']); ?>
	      </span>
  </div>
  <?php echo form_close(); ?>
</div>
<div class="col-sm-2">
<?php echo form_open('Dashboard/logout'); ?>
  <input type="submit" class="btn btn-info" style="margin-left: 5em;" value="Logout" />
<?php echo form_close(); ?>
</div>
</div>
<br />
<div class="row">
	<div class="col-sm-12">
		<div class="input-group">
	      <input type="number" class="form-control" placeholder="Enter account number of user you want to search for">
	      <span class="input-group-btn">
	        <button class="btn btn-secondary" type="button">Go!</button>
	      </span>
	    </div>
	</div>
</div>
<br />
<div class="row">
<table class="table table-striped">
  <thead>
    <tr>
      <th>S. No.</th>
      <th>Name</th>
      <th>Phone</th>
      <th>Email</th>
      <th>Account Number</th>
      <th>Document</th>
      <th>Change Aadhar or Pan Status</th>
    </tr>
  </thead>
  <tbody>
  <?php foreach ($users as $u) { ?>
    <tr>
      <th scope="row" style="min-width: 5em;"><?php echo $u['sno']; ?></th>
      <td style="min-width: 9em;"><?php echo $u['name']; ?></td>
      <td><?php echo $u['phone']; ?></td>
      <td><?php echo $u['email']; ?></td>
      <td style="min-width: 10em;"><?php echo $u['accountnumber']; ?></td>
      <td>
      	<a href="<?php echo $u['image']; ?>" target="_blank"><img src="<?php echo $u['image']; ?>" style="width: 100%;"></a>
      </td>
      <td>
      <?php if($u['status']=='1') { ?>
      	<Button class="btn btn-success" >Change Status</Button>
      <?php } else { ?>
        <Button class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">Change Status</Button>
      <?php } ?>
      </td>
    </tr>
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Change Status</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <?php echo form_open('Dashboard/approveDocument'); ?>
        <input type="hidden" name="documentid" value="<?php echo $u['documentid']; ?>">
        <button type="submit" class="btn btn-primary">Approve Document</button>
        <?php echo form_close(); ?>
      </div>
    </div>
  </div>
</div>
    <?php } ?>
  </tbody>
</table>	
</div>	
</div>
</body>
</html>