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

        $find_tag_id = "SELECT idtags FROM tags WHERE tag='$filter_tag'";
        if (!($find_tag_id_result = mysql_query($find_tag_id, $link))) {
            /* Failed */
            $response["message"] = mysql_error();
            $response["data"] = "false";
            die(json_encode($response));
        }

        if (mysql_num_rows($find_tag_id_result) == 1) {
            /* Locate the unique tag */
            $tag_row = mysql_fetch_array($find_tag_id_result);
            $tag_id = $tag_row[0];
        } else {
            /* No tags... kill it? */
            $response["message"] = "Found no matching tags.";
            $response["data"] = "false";
            die(json_encode($response));
        }

        /* Find messages with tag_id */
        $find_messages = "SELECT * FROM messages WHERE tag_id='$tag_id'";
        if (!($find_messages_result = mysql_query($find_messages, $link))) {
            /* Failed */
            $response["message"] = mysql_error();
            $response["data"] = "false";
            die(json_encode($response));
        }

        /* Read the selected rows into an array */
        while ($row = mysql_fetch_assoc($find_messages_result)) {
            /* Get the username */
            $user_id = $row["user_id"];
            $get_user = "SELECT username, email, id_avatar FROM users WHERE idusers='$user_id'";
            if (!($get_user_result = mysql_query($get_user, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $user_row = mysql_fetch_assoc($get_user_result);
            /* Get the tagname */
            $tag_id = $row["tag_id"];
            $get_tag = "SELECT tag FROM tags WHERE idtags='$tag_id'";
            if (!($get_tag_result = mysql_query($get_tag, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $tag_row = mysql_fetch_assoc($get_tag_result);
            /* Get the avatar */
            $id_avatar = $user_row["id_avatar"];
            $get_avatar = "SELECT img_data FROM avatars WHERE idavatars='$id_avatar'";
            if (!($get_avatar_result = mysql_query($get_avatar, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $avatar = mysql_result($get_avatar_result, 0);
            /* Make the user obj */
            $user = array("username" => $user_row["username"], "email" => $user_row["email"], "avatar" => $avatar);
            /* user_id, tag_id, message */
            $response["data"][] = array("user" => $user, "tag" => $tag_row["tag"], "message" => $row["message"]);
        }
    } else {
        /* Or if we didn't enter anything, select all messages */

        /* Find messages with tag_id */
        $find_messages = "SELECT * FROM messages";
        if (!($find_messages_result = mysql_query($find_messages, $link))) {
            /* Failed */
            $response["message"] = mysql_error();
            $response["data"] = "false";
            die(json_encode($response));
        }

        /* Read the selected rows into an array */
        while ($row = mysql_fetch_assoc($find_messages_result)) {
            /* Get the username */
            $user_id = $row["user_id"];
            $get_username = "SELECT username, email, id_avatar FROM users WHERE idusers='$user_id'";
            if (!($get_username_result = mysql_query($get_username, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $user_row = mysql_fetch_assoc($get_username_result);
            /* Get the tagname */
            $tag_id = $row["tag_id"];
            $get_tag = "SELECT tag FROM tags WHERE idtags='$tag_id'";
            if (!($get_tag_result = mysql_query($get_tag, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $tag_row = mysql_fetch_assoc($get_tag_result);
            /* Get the avatar */
            $id_avatar = $user_row["id_avatar"];
            $get_avatar = "SELECT img_data FROM avatars WHERE idavatars='$id_avatar'";
            if (!($get_avatar_result = mysql_query($get_avatar, $link))) {
                /* Error */
                $response["message"] = mysql_error();
                $response["data"] = "false";
                die(json_encode($response));
            }
            $avatar = mysql_result($get_avatar_result, 0);
            /* Make the user obj */
            $user = array("username" => $user_row["username"], "email" => $user_row["email"], "avatar" => $avatar);
            /* Add the post to the response */
            // $response["posts"][] = array( "user" => $user_row["username"], "tag" => $tag_row["tag"], "message" => $row["message"] );
            $response["message"] = "User " . $user_row["username"] . " successfully authenticated and read messages by tag " . $tag_row["tag"];
            $response["data"][] = array("user" => $user, "tag" => $tag_row["tag"], "message" => $row["message"]);
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

