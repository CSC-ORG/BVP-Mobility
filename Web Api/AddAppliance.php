<?php


/***  include site wide/global configuration file  ***/
require 'api.config.php';

if(empty($_GET['id']) || is_array($_GET['id']) || !preg_match('`^[0-9]{4,20}$`', $_GET['id']))
   output_json(array('response'=>false,'msg'=>'HomeID is Not Valid!'));


if(empty($_GET['name']) || is_array($_GET['name']) || !preg_match('`^[a-zA-Z0-9 ]+$`iU', $_GET['name']))
    output_json(array('response'=>false,'msg'=>'Appliance Name is Not Valid!'));





$PDOStmt = $PDO->prepare('INSERT INTO appliances (HomeID, AppName) VALUES(:HomeID,:AppName)');
$PDOStmt->bindParam(':HomeID', $_GET['id'], PDO::PARAM_STR);
$PDOStmt->bindParam(':AppName', $_GET['name'], PDO::PARAM_STR);

$PDOStmt->execute();

if($PDOStmt->rowCount())
   output_json(array('response'=>false,'msg'=>'Appliance Added Successfully!'));

else
   output_json(array('response'=>false,'msg'=>'Failed'));

