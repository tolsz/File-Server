# File-Server
A simple file server. 

Project from hyperskill.org. \
Here's the link to the project: https://hyperskill.org/projects/52

## Usage
First turn on the server side then the client side. If everything is correct, the server should print "Server started!" message in the console.
If you want to stop the server, send "exit". If you don't want to specify a file name, just press Enter without typing anything, the server should generate a unique name for this file and send back the id.

The following examples show the client side, the greater-than symbol followed by a space (> ) represents the user input.

Example 1:
```console
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 2
Enter name of the file: > my_cat.jpg
Enter name of the file to be saved on server: > 
The request was sent.
Response says that file is saved! ID = 23
```

Example 2:
```console
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 1
Do you want to get the file by name or by id (1 - name, 2 - id): > 2
Enter id: > 23
The request was sent.
The file was downloaded! Specify a name for it: > cat.jpg
File saved on the hard drive!
```

Example 3:
```console
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > 3
Do you want to delete the file by name or by id (1 - name, 2 - id): > 2
Enter id: > 23
The request was sent.
The response says that this file was deleted successfully!
```

Example 4:
```console
Enter action (1 - get a file, 2 - save a file, 3 - delete a file): > exit
The request was sent.
```
