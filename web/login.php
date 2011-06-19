<?php

/**
 * *******************************************************\
 * File: 	login.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *
 * Project: 	Programming for Interaction 3 - Mobile	*
 *
 * Description:	Attempts to login to the webserver, and *
 * 		if successful retrieves an access token *
 * 		that allows for posting and reading 	*
 * 		messages from the server.		*
 * \*******************************************************
 */

include_once("config.php");

/* Make sure arguments are set */
if (!isset($_POST["username"]) || !isset($_POST["password"])) {
    $response["error"] = "Arguments not set.";
    die(json_encode($response));
}

/* Read the arguments */
$username = $_POST["username"];
$password = $_POST["password"];

/* Build and execute the login query */
$login = "SELECT * FROM users WHERE username='$username' AND password=md5('$password')";
if (!($login_result = mysql_query($login, $link))) {
    /* Failed */
    $response["error"] = mysql_error();
    die(json_encode($response));
}

/* Make sure only one row existed */
if (mysql_num_rows($login_result) == 1) {
    /* Fetch the user values */
    $user = mysql_fetch_array($login_result);

    /* Generate random access_token */
    $access_token = md5(getRandomString());
    $response["access_token"] = $access_token;

    /* Search for user_id in access_tokens to see if user has an access_token already */
    $searchtoken = "SELECT user_id FROM access_tokens WHERE user_id='$user[0]'";
    if (!($searchtoken_result = mysql_query($searchtoken, $link))) {
    	/* Failed */
    	$response["data"] = "false";
        $response["message"] = mysql_error();
        die(json_encode($response));
    }

    if (mysql_num_rows($searchtoken_result) == 1) {
        /* User has already retrieved access_token, UPDATE */
        $updatetoken = "UPDATE access_tokens SET access_token='$access_token' WHERE user_id='$user[0]'";
        if (!($updatetoken_result = mysql_query($updatetoken, $link))) {
        	/* Failed */
        	$response["data"] = "false";
            $response["message"] = mysql_error();
            die(json_encode($response));
        }
    } else {
        /* User has no access_token linked, INSERT */
        $inserttoken = "INSERT INTO access_tokens (user_id,access_token) VALUES ('$user[0]','$access_token')";
        if (!($inserttoken_result = mysql_query($inserttoken, $link))) {
        	/* Failed */
        	$response["data"] = "false";
            $response["message"] = mysql_error();
            die(json_encode($response));
        }
    }

    /* Successful login */
    $response["data"] = "true";
} else {
    /* Wrong username or password */
    $response["data"] = "false";
    $response["message"] = "Wrong username or password";
    die(json_encode($response));
}

/* Send the access_token back to the requester, JSON encoded */
print(json_encode($response));

/* Close database */
mysql_close();

?>

