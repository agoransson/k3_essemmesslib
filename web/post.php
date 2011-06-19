<?php

/********************************************************\
 * File: 	post.php									*
 * Author: 	Andreas Göransson							*
 * Date: 	2011-03-16									*
 * Organization: Malmö University, K3					*
 *														*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *														*
 * Description:	Attempts to post a message to the SQL 	*
 *		database, success depends on the state 			*
 *		of the supplied access_token.					*
\********************************************************/

include_once("config.php");

/* Make sure arguments are set */
if( !isset($_POST["access_token"]) || !isset($_POST["message"]) || !isset($_POST["tag"]) ){
	$response["error"] = "Arguments not set.";
	die( json_encode($response) );
}

/* Read the arguments */
$access_token = $_POST["access_token"];
$message = $_POST["message"];
$tag = $_POST["tag"];

/* See if the access_token is stored */
$find_access_token = "SELECT * FROM access_tokens WHERE access_token='$access_token'";
if( !($find_result = mysql_query( $find_access_token, $link )) ){
	/* Failed */
	$response["message"] = mysql_error();
	$response["data"] = "false";
	die( json_encode( $response ) );
}

/* Make sure only one row existed */
if( mysql_num_rows($find_result) == 1 ){
	/* Fetch the token values */
	$token = mysql_fetch_array( $find_result );

	/* TODO make sure the token is valid (timestamp)... we'll just assume it's okay now though */

	//$response["message"] = $token[2]." found!";

	/* Get the tag id... */
	/* Test if written tag exists... */
	$find_tag = "SELECT * FROM tags WHERE tag='$tag'";
	if( !($find_tag_result = mysql_query( $find_tag, $link)) ){
		/* Failed */
		$response["message"] = mysql_error();
		$response["data"] = "false";
		die( json_encode( $response ) );
	}

	if( mysql_num_rows($find_tag_result) == 1 ){
		/* Locate the unique tag */
		$tag_row = mysql_fetch_array( $find_tag_result );
		$tag_id = $tag_row[0];
	}else{
		/* Add a new tag and get it's id */
		$add_tag = "INSERT INTO tags (tag) VALUES ('$tag')";
		if( !($add_tag_result = mysql_query( $add_tag, $link)) ){
			/* Failed */
			$response["message"] = mysql_error();
			$response["data"] = "false";
			die( json_encode( $response ) );
		}

		$tag_id = mysql_insert_id();
	}

	/* Post message */
	$post_message = "INSERT INTO messages (user_id, tag_id, message ) VALUES ('$token[1]','$tag_id','$message')";
	if( !( $post_message_result = mysql_query( $post_message, $link)) ){
		/* Failed */
		$response["message"] = mysql_error();
		$response["data"] = "false";
		die( json_encode( $response ) );
	}

	/* Assemble the response */
	$response["message"] = "User id ".$token[1]." added message ".$message;
	$response["data"] = "true";
}


/* Send the response back to the requester, JSON encoded */
print( json_encode( $response ) );

/* Close database */
mysql_close();

?>

