<?php
$db_name="DemoData";
$mysql_username="root";
$mysql_password="";
$server_name="localhost";
$conn =mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name);

$sql  = "Select * from Users";
$result = mysqli_query($conn,$sql);

if($result)
{
$response['Districts'] = array();
while($row=mysqli_fetch_array($result)){
		array_push($response['Districts'], $row);
		}
		$response['message']="Sucessful";
		}	
		 else{
 	$response['message']="Failure";
 }
echo json_encode($response);

?>
