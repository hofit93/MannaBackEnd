# BackEnd Manna task

This project was generated with java.
You can run the program and see the output print online with this URL:
https://ide.geeksforgeeks.org/oIlsrNt9Ff

## Summary of this task

You are working in a big online marketing company. Your manager assigns you a
new task as follows:
The company needs a solution to deal with its enormous CRM calls logging
requirements. The new product you should develop need to handle tens of
thousands of CRM calls entries per minute. The system should log (write) the
entries to a file. Each entry has its own priority (assume the priority is indicated as
an integer). The system should log the items to the file as closed as possible to the
items priority (form high to low). The system should be used by all the company
products it should function in a multi-threading and multi process environment.

You can develop the logging component either in .NET (C#), Node.js (JavaScript) or
Java.

## Summary of the solution

The app using multi-threading and for each CRM calls create a new thread.
each of those threads put the CRM data on BlockingQueue and done.
In addition, I create one main thread, 
this thread is responsibility is to write all the CRM data to the list according to the CRM priority.

I set MAX_CRM parm to simulate a lot of CRM at the same time.
I also set for each CRM a random number between 0 to 4 for the priority of the CRM calls.




