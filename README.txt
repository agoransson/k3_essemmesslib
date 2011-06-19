EssemmessLib

Author: Andreas Göransson, andreas.goransson@mah.se


Android library which lets your android app communicate with the development service available at
Malmö University. This is a very basic library used in the programming courses at the faculty
Arts and Communication, K3.

The system is built on a simple PHP service which interacts with a MySQL database, the database
contains a set of messages written by any of the registered users. Each message contains a
message body, a message tag and the authors name.

Your Android app can easily interact with this system by instantiating a server object, this object
will allow your application to communicate TO the server. To react to the response of the server, your
application should implement the EssemmessListener interface.


Supported functionality:
* Login to the system (this renders a new 32bit access token for further communication with the system)
* Write new message (this requires a successful login)
* Read messages, based on tag (requires a successful login)
* Read messages, all (requires a successful login)


TODO:
* Register new user
* Remove the listener interface, replace with Handler


Example app, functionality:
To get the android example app up and running click "File" -> "Import" -> "General" -> "Existing Projects into Workspace".
* Login
* Write
