<?php

/********************************************************\
 * File: 	logout.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *							*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *							*
 * Description:	Attempts to delete an access_token.	*
\********************************************************/

include_once("config.php");

/* Make sure arguments are set */
if( !isset($_POST["access_token"]) ){
	$response["error"] = "Arguments not set.";
	die( json_encode($response) );
}

/* Read the arguments */
$access_token = $_POST["access_token"];



/* Build and execute the login query */
$logout = "DELETE FROM access_tokens WHERE access_token='$access_token'";

if( !($logout_result = mysql_query( $logout, $link )) ){
	/* Failed */
	$response["error"] = mysql_error();
	die( json_encode( $response ) );
}

$response["message"] = "User logged out.";

/* Send the access_token back to the requester, JSON encoded */
print( json_encode( $response ) );

/* Close database */
mysql_close();

?>

