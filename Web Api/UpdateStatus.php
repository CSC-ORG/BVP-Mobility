<?php


/***  include site wide/global configuration file  ***/
require 'api.config.php';

if(empty($_GET['id']) || is_array($_GET['id']) || !preg_match('`^[0-9]{4,20}$`', $_GET['id']))
   output_json(array('response'=>false,'msg'=>'HomeID is Not Valid!'));


if(empty($_GET['appid']) || is_array($_GET['appid']) || !preg_match('`^[0-9]$`', $_GET['appid']))
    output_json(array('response'=>false,'msg'=>'Appliance ID is Not Valid!'));

if(empty($_GET['status']) || is_array($_GET['status']))
    output_json(array('response'=>false,'msg'=>'Appliance Status is Not Valid!'));




try{

$PDOStmt = $PDO->prepare('UPDATE appliances SET AppStatus=:AppStatus WHERE HomeID=:HomeID and AppId=:AppId');
$PDOStmt->bindParam(':HomeID', $_GET['id'], PDO::PARAM_STR);
$PDOStmt->bindParam(':AppId', $_GET['appid'], PDO::PARAM_STR);

$PDOStmt->bindParam(':AppStatus', $_GET['status'], PDO::PARAM_STR);

$PDOStmt->execute();

if($PDOStmt->rowCount())
   output_json(array('response'=>true,'msg'=>'Status Changed Successfully!'));

else
   output_json(array('response'=>false,'msg'=>'Failed'));

}catch(Exception $ex)
{
 output_json(array('response'=>false,'msg'=>'Invalid Status'));
}
