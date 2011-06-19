<?php

/********************************************************\
 * File: 	functions.php				*
 * Author: 	Andreas Göransson			*
 * Date: 	2011-03-16				*
 * Organization: Malmö University, K3			*
 *							*
 * Project: 	Programming for Interaction 3 - Mobile	*
 *							*
 * Description:	Helper functions for "webserver" used 	*
 *		in course Programming for Interaction 3 *
\********************************************************/


/* Returns a random String */
function getRandomString($length = 6) {
	$validCharacters = "abcdefghijklmnopqrstuxyvwz0123456789";

	$validCharNumber = strlen($validCharacters);

	$result = "";

	for ($i = 0; $i < $length; $i++) {
		$index = mt_rand(0, $validCharNumber - 1);
		$result .= $validCharacters[$index];
	}

	return $result;
}

/**
 Validate an email address.
 Provide email address (raw input)
 Returns true if the email address has the email
 address format and the domain exists.
 */
function validEmail($email)
{
	$isValid = true;
	$atIndex = strrpos($email, "@");
	if (is_bool($atIndex) && !$atIndex)
	{
		$isValid = false;
	}
	else
	{
		$domain = substr($email, $atIndex+1);
		$local = substr($email, 0, $atIndex);
		$localLen = strlen($local);
		$domainLen = strlen($domain);
		if ($localLen < 1 || $localLen > 64)
		{
			// local part length exceeded
			$isValid = false;
		}
		else if ($domainLen < 1 || $domainLen > 255)
		{
			// domain part length exceeded
			$isValid = false;
		}
		else if ($local[0] == '.' || $local[$localLen-1] == '.')
		{
			// local part starts or ends with '.'
			$isValid = false;
		}
		else if (preg_match('/\\.\\./', $local))
		{
			// local part has two consecutive dots
			$isValid = false;
		}
		else if (!preg_match('/^[A-Za-z0-9\\-\\.]+$/', $domain))
		{
			// character not valid in domain part
			$isValid = false;
		}
		else if (preg_match('/\\.\\./', $domain))
		{
			// domain part has two consecutive dots
			$isValid = false;
		}
		else if
			(!preg_match('/^(\\\\.|[A-Za-z0-9!#%&`_=\\/$\'*+?^{}|~.-])+$/',
			str_replace("\\\\","",$local)))
		{
			// character not valid in local part unless
			// local part is quoted
			if (!preg_match('/^"(\\\\"|[^"])+"$/',
				str_replace("\\\\","",$local)))
			{
				$isValid = false;
			}
		}
		if ($isValid && !(checkdnsrr($domain,"MX") ||
			↪checkdnsrr($domain,"A")))
		{
			// domain not found in DNS
			$isValid = false;
		}
	}
	return $isValid;
}
?>
