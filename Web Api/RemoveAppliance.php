<?php


/***  include site wide/global configuration file  ***/
require 'api.config.php';
/* Empty elements check */
if(empty($_GET['id']) || is_array($_GET['id']) || !preg_match('`^[0-9]{4,20}$`', $_GET['id']))
   output_json(array('response'=>false,'msg'=>'HomeID is Not Valid!'));


if(empty($_GET['appId']) || is_array($_GET['appId']) || !preg_match('`^[0-9]{1,20}$`', $_GET['appId']))
    output_json(array('response'=>false,'msg'=>'Appliance Id is Not Valid!'));





$PDOStmt = $PDO->prepare('DELETE FROM appliances WHERE HomeID=:HomeID AND AppId=:AppId');
$PDOStmt->bindParam(':HomeID', $_GET['id'], PDO::PARAM_STR);
$PDOStmt->bindParam(':AppId', $_GET['appId'], PDO::PARAM_STR);

$PDOStmt->execute();
/* Query executed successfully */
if($PDOStmt->rowCount())
   output_json(array('response'=>false,'msg'=>'Appliance Added Successfully!'));

else
   output_json(array('response'=>false,'msg'=>'Failed'));

