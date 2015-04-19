<?php

/***  include site wide/global configuration file  ***/
require 'api.config.php';

$PDOStmt = $PDO->prepare('SELECT * FROM appliances WHERE HomeID=:id');
$PDOStmt->bindParam(':id', $_GET['id'], PDO::PARAM_INT);
$PDOStmt->execute();
/* Query executed successfully */
if($PDOStmt->rowCount())
  output_json(array('response'=>true,'app_list'=>$PDOStmt->fetchAll(PDO::FETCH_ASSOC)));
else
   output_json(array('response'=>false,'msg'=>'Nothing To Display!'));

