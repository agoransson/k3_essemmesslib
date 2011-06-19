<?php

/**
 * *******************************************************\
 * File: 	resgiter.php								*
 * Author: 	Andreas Göransson							*
 * Date: 	2011-03-16									*
 * Organization: Malmö University, K3					*
 *
 * Project: 	Programming for Interaction 3 - Mobile	*
 *
 * Description:	Attempts to register a new user.		*
 * \*******************************************************
 */

include_once("config.php");

/* Make sure arguments are set */
if (!isset($_POST["email"]) || !isset($_POST["username"]) || !isset($_POST["password"]) || !isset($_POST["avatar"])) {
    $response["error"] = "Arguments not set.";
    die(json_encode($response));
}

/* Read the arguments */
$email = $_POST["email"];
$username = $_POST["username"];
$password = $_POST["password"];
$avatar = $_POST["avatar"];

/* Make sure the email is valid */
if (!validEmail($email)) {
    $response["message"] = "E-mail was not valid, registration aborted!";
    $response["data"] = "false";
    die(json_encode($response));
}

/* See if the email or username are already taken */
$exists = "SELECT * FROM users WHERE (email='$email' OR username='$username')";
if (!($exists_result = mysql_query($exists, $link))) {
    /* Failed */
	$response["message"] = mysql_error();
	$response["data"] = "false";
    die(json_encode($response));
}

/* If it didn't exist, add the new user to the database */
// Start with adding the avatar!
// $imgsize = filesize($avatar);
// $img = addslashes(fread(fopen($avatar, "r"), $imgsize));
$newavatar = "INSERT INTO avatars (img_data) VALUES ('$avatar')";
if (!($avatar_result = mysql_query($newavatar, $link))) {
    /* Failed */
	$response["message"] = mysql_error();
	$response["data"] = "false";
    die(json_encode($response));
}
// If all went well with the avatar, add the user
$idavatar = mysql_insert_id();
$newuser = "INSERT INTO users (username, password, email, id_avatar) VALUES ('$username','" . md5($password) . "','$email','" . utf8_encode($idavatar) . "')";
if (!($user_result = mysql_query($newuser, $link))) {
    /* Failed */
	$response["message"] = mysql_error();
	$response["data"] = "false";
    die(json_encode($response));
} else {
    /* Success, assemble the response */
    $response["message"] = "Registration successful for user " . $_POST["username"];
    $response["data"] = "true";

    /* Send the mail */
    $subject = "Registration successful";
    $body = "Thank you for registering\n\nYour details are\n\nusername:" . $username . "\npassword:" . $password;
    mail($email, $subject, $body);
}

/* Send the response back to the requester, JSON encoded */
print(json_encode($response));

/* Close database */
mysql_close();

?>

