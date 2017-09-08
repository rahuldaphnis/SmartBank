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
	<title>Bank Portal</title>
</head>
<body>
	<div class="container-fluid" style="border: 1px solid black; background: url('Images/bankbackground.jpg') no-repeat center; height: 40em; background-size: cover;">
	<br /><br />
	<div class="row">
	<div class="col-sm-12">
		<center><h3 style="color:black; text-shadow: 1px 1px white; font-weight: bold;">Smart Bank Portal</h3></center></div>		
	</div>
	<br />
	<div class="row">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<nav class="nav nav-tabs" id="myTab" role="tablist">
		  <a class="nav-item nav-link active" style="color:black; width: 50%; text-align: center;" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab">Login</a>
		  <a class="nav-item nav-link" style="color:black; width: 50%; text-align: center;" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab">Sign Up</a>
		</nav>
  		<br />
		<div class="tab-content" id="nav-tabContent">
		  <div class="tab-pane fade show active" id="nav-home" role="tabpanel">
            <?php echo form_open('Welcome/login'); ?>
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-user">Phone</i></span>
				<?php echo form_input(['type' => 'number', 'class' => 'form-control', 'placeholder' => 'Phone', 'name' => 'phone', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-lock">#</i></span>
				<?php echo form_password(['class' => 'form-control', 'placeholder' => 'Password', 'name' => 'password', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <?php echo form_submit(['class' => 'btn btn-success', 'style' => 'width: 100%; cursor: pointer; font-size: 1.1em; font-weight: bold;', 'value' => 'Login']); ?>
			<?php echo form_close();?>
		  </div>
		  <div class="tab-pane fade" id="nav-profile" role="tabpanel">  
		  <?php echo form_open('/Welcome/signup'); ?>
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-user">Name</i></span>
				<?php echo form_input(['type' => 'text', 'class' => 'form-control', 'placeholder' => 'Name of Bank', 'name' => 'name', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-user">Phone</i></span>
				<?php echo form_input(['type' => 'number', 'class' => 'form-control', 'placeholder' => 'Phone Number of Bank', 'name' => 'phone', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-lock">#</i></span>
				<?php echo form_password(['class' => 'form-control', 'placeholder' => 'Password', 'name' => 'password', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-user">%</i></span>
				<?php echo form_input(['type' => 'number', 'class' => 'form-control', 'placeholder' => 'Threshold percentage to auto verify documents', 'name' => 'threshold', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <div class="input-group">
			    <span class="input-group-addon"><i class="glyphicon glyphicon-user">@</i></span>
				<?php echo form_input(['type' => 'text', 'class' => 'form-control', 'placeholder' => 'API Endpoint of Bank', 'name' => 'endpoint', 'required' => 'true']); ?>
			  </div>
			  <br />
			  <?php echo form_submit(['class' => 'btn btn-success', 'style' => 'width: 100%; cursor: pointer; font-size: 1.1em; font-weight: bold;', 'value' => 'Sign Up']); ?>
		  <?php echo form_close();?>
		  </div>
		</div>
		<br />
	</div>
	<div class="col-sm-4"></div>
	</div>		
	</div>
</body>
</html>