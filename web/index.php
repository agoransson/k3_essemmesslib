<?php

/********************************************************\
 * File: 	index.php									*
 * Author: 	Andreas Göransson							*
 * Date: 	2011-09-13									*
 * Organization: Malmö University, K3					*
 *														*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *														*
 * Description:	Lists messages on the system. 			*
\********************************************************/

include_once("config.php");

	/* We're not going to filter this place right now... just display everything */
?>


<!doctype html>
<html>

<head>
	<meta charset="utf-8">
	
	<title>Essemmess AB - (MAH Android course)</title>

	<meta name="description" content="Essemmess AB">
	<meta name="author" content="Andreas Göransson">
	
	<link rel="stylesheet" href="style.css">
</head>

<body>
	<section id="content">
		<?php
			// list all messages (there's no filtering implemented at this point)
			$query  = "SELECT tags.tag, messages.message, users.username, messages.time";
			$query .= " FROM messages";
			$query .= " JOIN tags ON (messages.tag_id = tags.idtags)";
			$query .= " JOIN users ON (messages.user_id = users.idusers)";
			
			$result = mysql_query( $query );
			if( !$result ){
				die( "Could not load messages " . sql_error() );
			}

			while( $row = mysql_fetch_row($result) ){
				print "<article><p class=\"tag\">".$row[0]."</p><p class=\"message\">".$row[1]."</p><p class=\"author\">written ".$row[3]." by ".$row[2]."</p></article>";
			}
		?>
		<article class="push"></article>
	</section>

	<section id="footer">
		<?php include("footer.php") ?>
	</section>
</body>

</html>

