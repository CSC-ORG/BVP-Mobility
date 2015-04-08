<?php

/***  include site wide/global configuration file  ***/
require 'api.config.php';


if(empty($_GET['id']))
    output_json('HomeID Is Empty!',true);



if(empty($_GET['password'])) 
    output_json('Password Is Empty!',true);



global $PDOStmt;


$PDOStmt = $PDO->prepare('SELECT HomeID,HomePassword FROM houses WHERE HomeID=:HomeID and HomePassword=:HomePassword');
$PDOStmt->bindParam(':HomeID', $_GET['id'], PDO::PARAM_STR);
$PDOStmt->bindParam(':HomePassword', md5($_GET['password']), PDO::PARAM_STR);

$PDOStmt->execute();

if($PDOStmt->rowCount())
    output_json(array('response'=>true,'msg'=>'Login Successfully'));
else
    output_json(array('response'=>false,'msg'=>'Login Failed'));



