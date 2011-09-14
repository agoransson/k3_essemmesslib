<?php

/**
 * *******************************************************\
 * File: 	read.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *
 * Project: 	Programming for Interaction 3 - Mobile	*
 *
 * Description:	Attempts to read messages posted to the	*
 * 		SQL server. Needs a valid access_token. *
 * \*******************************************************
 */

include_once("config.php");

/* Make sure arguments are set */
if (!isset($_POST["access_token"]) || (strlen($_POST["access_token"]) < 32)) {
    $response["message"] = "User not logged in";
    $response["data"] = "false";
    die(json_encode($response));
}

if (!isset($_POST["filter_tag"])) {
    $response["message"] = "Missing arguments for search!";
    $response["data"] = "false";
    die(json_encode($response));
}

/* Read the arguments */
$access_token = $_POST["access_token"];
$filter_tag = $_POST["filter_tag"];

/* See if the access_token is stored */
$find_access_token = "SELECT * FROM access_tokens WHERE access_token='$access_token'";
if (!($find_result = mysql_query($find_access_token, $link))) {
    /* Failed */
    $response["message"] = "SQL error? -> " . mysql_error() . ")";
    $response["data"] = "false";
    die(json_encode($response));
}
/* Make sure only one row existed */
if (mysql_num_rows($find_result) == 1) {
    /* Fetch the token values */
    $token = mysql_fetch_array($find_result);
    /* Find tag_id */
    if (strlen($filter_tag) > 0) {
        /* This means we have entered something in the filter_field... */
		$query  = "SELECT tags.tag, messages.message, users.username, messages.time, avatars.img_data";
		$query .= " FROM messages";
		$query .= " JOIN tags ON (messages.tag_id = tags.idtags)";
		$query .= " JOIN users ON (messages.user_id = users.idusers)";
		$query .= " JOIN avatars ON (users.id_avatar = avatars.idavatars)";
		$query .= " WHERE (tags.tag = '$filter_tag')";
		
		if (!($result = mysql_query($query, $link))) {
            /* Failed */
            $response["message"] = mysql_error();
            $response["data"] = "false";
            die(json_encode($response));
        }

		while( $row = mysql_fetch_row($result) ){
			// tag = 0, message = 1, authorname = 2, email = 3, avatar = 4
            /* Make the user obj */
            $user = array("username" => $row[2], "email" => $row[3], "avatar" => $row[4]);
            /* user_id, tag_id, message */
            $response["data"][] = array("user" => $user, "tag" => $row[0], "message" => $row[1]);
		}
    } else {
		/* This means we left the filter field empty, get all messages! */
		$query  = "SELECT tags.tag, messages.message, users.username, users.email, avatars.img_data, messages.time";
		$query .= " FROM messages";
		$query .= " JOIN tags ON (messages.tag_id = tags.idtags)";
		$query .= " JOIN users ON (messages.user_id = users.idusers)";
		$query .= " JOIN avatars ON (users.id_avatar = avatars.idavatars)";
		
		if (!($result = mysql_query($query, $link))) {
            /* Failed */
            $response["message"] = mysql_error();
            $response["data"] = "false";
            die(json_encode($response));
        }

		while( $row = mysql_fetch_row($result) ){
			// tag = 0, message = 1, authorname = 2, email = 3, avatar = 4, time = 5
            /* Make the user obj */
            $user = array("username" => $row[2], "email" => $row[3], "avatar" => $row[4]);
            /* user_id, tag_id, message */
            $response["data"][] = array("user" => $user, "tag" => $row[0], "message" => $row[1], "time" => $row[5]);
		}
    }
} else {
    /* This means there was no access token in the database that matched the token supplied */
    $response["message"] = "User not logged in!";
    $response["data"] = "false";
    die(json_encode($response));
}

/* Send the response back to the requester, JSON encoded */
print(json_encode($response));
/* Close database */
mysql_close();

?>

