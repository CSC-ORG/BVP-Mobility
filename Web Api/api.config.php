<?php

/***  register autoloading function  ***/
spl_autoload_register('__class__autoloader__');

try
{
  $PDO = new PDO('mysql:host=mysql3.000webhost.com;dbname=a5814165_applian', 'username', 'password', array(PDO::ATTR_PERSISTENT => true));
  $PDO->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  $PDO->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
}
catch(PDOException $ecx)
{
  echo $ecx->getMessage();
}
catch( Exception $ecx )
{
  echo $ecx->getMessage();
}



/**************************************************************************************
 **
 **
 */

function __class__autoloader__($__classname__)
{
  $__classname__ = './classes/' . basename(strtolower($__classname__)) . '.class.php';
  if(file_exists($__classname__))
    include $__classname__;
}

function output_json($message, $__CONDITION__ = false)
{
 
  die(json_encode($message));
}


function ValidateIMEI($imei)
{
  if (!preg_match('/^[0-9]{15}$/', $imei)) return false;
  $sum = 0;
  for ($i = 0; $i < 14; $i++)
  {
    $num = $imei[$i];
    if (($i % 2) != 0)
    {
      $num = $imei[$i] * 2;
      if ($num > 9)
      {
        $num = (string) $num;
        $num = $num[0] + $num[1];
      }
    }
    $sum += $num;
  }
  if ((($sum + $imei[14]) % 10) != 0) return false;
  return true;
}

