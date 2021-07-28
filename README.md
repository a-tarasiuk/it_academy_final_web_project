# CT - Cargo Trading.

## Project description:
The project represents cargo trading.

### Types of account:
- Individual;
- Company.

### Account roles:
- Administrator;
- Guest (unregistered user);
- Individual;
- Company.

### Account statuses:
- Active;
- Banned;
- Removed;
- Under consideration (Your reques is under consideration by the site administrator. According to the results of the consideration of the application, you will be sent by letter on the mail specified during registration).

### Order statuses:
- Open;
- Closed.

### Account confirmation upon registration
To confirm the mail specified during registration, a token is generated using `Java Uuid Generator (JUG)`. JUG is a set of Java classes for working with UUIDs: generating UUIDs using any of standard methods, outputting efficiently, sorting and so on. It generates UUIDs according to the UUID specification (RFC-4122) (also see Wikipedia UUID page for more explanation).

### ERR diagram