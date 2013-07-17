Merge iOS6 SMS Databases with Java!
================

It is somehow a continuation of a project based here:
http://smsmerge.homedns.org/

How to use, my way:
To obtain the proper sms db files I suggest using:
http://www.icopybot.com/itunes-backup-manager.htm

run the application with two parameters:
(path-to-first-db-file) (path-to-second-db-file)

Import the new db file using the iBackup.
For me, regular copy of the file didn't work, and some messages didn't want to show up.
With import of the iBackup it worked flawlessly :)


What it does and what it does not
------------

### Merging

The code joins the second database to the first one. 
* It checks for existing recepients and properly changes the database so there are no double infomation.
* It copies the attachments
* Currently group chats ARE NOT TESTED!!!
* Works for both iMessage and texts

### Sorting
Because iPhone reads the texts from bottom to top by ID not DATE.
I sort the ID by DATE field and properly rewrite the joined tables.

### PROBLEMS

* I did not test group messages - I deleted them from my phone.

### FUTURE PLANS (or not... I'm lazy)

There is a bug in iOS SMS Database:

old iMessages based on email address are not copied or moved 
to the iMessages based on mobile number (from iOS6) that means you just loose those messages :/

It can be manually fixed with some SQL and I fixed mine that way... Its about 4 commands :P

