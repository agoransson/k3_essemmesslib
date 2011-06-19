<?php

/********************************************************\
 * File: 	index.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *							*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *							*
 * Description:	Lists messages on the system. 		*
\********************************************************/

include_once("config.php");

	/* We're not going to filter this place right now... just display everything */
?>

<!-- THE HTML Part -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
	<title>Essemmess AB - (PFI3: Mobile)</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	
	<!--link rel="stylesheet" type="text/css" href="style.css"-->
	<!--script type="text/javascript" src="script.js"></script-->
</head>

<body>
	<!-- List all messages, do this in a table -->
	<table border="1" summary="This table shows all enteres messags in Essemmess database">
	<caption>Ess emm ess AB</caption>
	<tr> <th>Message ID</th> <th>User ID</th> <th>TAG</th> <th>Message</th> </tr>
		<?php
			$sqlresult = mysql_query("SELECT * FROM messages");
			
			if ( !$sqlresult ) {
				die( "Could not load messages " + sql_error() );
			}
			
			

			while( $row = mysql_fetch_row($sqlresult) ) {

				/* Get the tag as well */
				$tagresult = mysql_query( "SELECT * FROM tags WHERE idtags='$row[2]'");
				if( !$tagresult ){
					die( "Could not load tag " + sql_error() );
				}

				$tag_num = mysql_num_rows( $tagresult );
				if( $tag_num > 1 ){
					die( "Something went wrong, we're having more than one tag for a unique ID! ");
				}

				/* If we made it here, the queries are fine... */
				$tag = mysql_fetch_array( $tagresult );

				/* Add the message to the table */
				print "<tr> <td>".$row[0]."</td> <td>".$row[1]."</td> <td>".$tag[1]."</td> <td>".$row[3]."</td> </tr>";
			}
		?>
	</table>
</body>

</html>

