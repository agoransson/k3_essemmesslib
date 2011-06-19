<?php

/********************************************************\
 * File: 	config.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *							*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *							*
 * Description:	Configuration script.			*
\********************************************************/

include_once("functions.php");

/* Database details */
$dbhost="MYSQL SERVER";
$dbuser="MYSQL USERNAME";
$dbpass="MYSQL PASSWORD";
$dbname="MYSQL DATABASE";

/* Connect to the database - not using persistant connection here */
($link = mysql_connect("$dbhost", "$dbuser", "$dbpass")) || die("Couldn't connect to MySQL");
mysql_select_db("$dbname", $link) || die("Couldn't open db: $dbname. Error if any was: ".mysql_error() );

/* Construct the response, and add default values */
$response = array();
$response["data"] = array();
$response["access_token"] = "";
$response["message"] = "";

?>
