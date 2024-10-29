Thank you for the very nice discussions previously. 
We are excited to continue the interview process with you.

This is the design and coding challenge step we discussed.

Imagine the following scenario: 
we have a producer which writes an event every 100ms to a RabbitMQ queue. This event is comprised of the item, quantity and whether the 
request is to add inventory or to substract inventory (add/remove). The consumer application reads the event from the queue and updates the
inventory in the database according to the "command" which is contained within the event. The inventory is stored in a PostgreSQL database.

Please implement a solution that handles the event processing and inventory updates. You are free to use any framework of your choice.

To start, we have already written some code for random generation of the events.
Your Tasks:
1) Implement the RabbitMQ writer functionality in the Producer.java to write the event to a Topic
2) Read the event from the topic and execute the inventory update in the Consumer.java
3) Ensure the overall solution is resilient with exception handling, test cases etc

Your code will be evaluated on the following criteria:
1) Design Patterns
2) Clean Coding principles
3) SOLID Principles
4) Flexibility and Scalability

Please zip the solution send it back to the recruiter.

