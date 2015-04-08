<?php

/***  include site wide/global configuration file  ***/
require 'api.config.php';

if(empty($_GET['id']) || is_array($_GET['id']) || !preg_match('`^[0-9]{4,20}$`', $_GET['id']))
    output_json(array('response'=>false,'msg'=>'HomeID is Not Valid!'));



if(empty($_GET['password']) || is_array($_GET['password']) || !preg_match('`^[a-zA-Z ]+$`iU', $_GET['password']))
    output_json(array('response'=>false,'msg'=>'Password Is Not Valid!'));


global $PDOStmt;
try{

$PDOStmt = $PDO->prepare('INSERT INTO houses(HomeID,HomePassword) VALUES(:HomeID,:HomePassword)');
$PDOStmt->bindParam(':HomeID', $_GET['id'], PDO::PARAM_STR);
$PDOStmt->bindParam(':HomePassword', md5($_GET['password']), PDO::PARAM_STR);

$PDOStmt->execute();

if($PDOStmt->rowCount())
    output_json(array('response'=>true,'msg'=>'Home Added Successfully'));
else
    output_json(array('response'=>false,'msg'=>'Home ID Allready Taken'));
}catch(Exception $ex)
{
 output_json(array('response'=>false,'msg'=>'Home ID Allready Taken'));
}


