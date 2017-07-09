# Chatserver API
Basic chat service implementation with REST-api

**Requirements:**
- Ability to send text chat messages from one user to another user
- Ability to persist messages, so that receiver could receive it after sender goes offline
- Ability for receiver to receive new chat messages
- Ability to notify sender when receiver read particular message
- No client authentication/authorization needed

## User

### Create new user
* Method & URL

  ```POST /api/user```
  
* Payload
  
  sender: string - required
  
  ```sender```
  
* Success response

  * **Code**: 200
  * **Content**: 
  ```
  {
      "id": 1,
      "name": "sender"
  }
  ```

* Error response

  * **CODE**: 400
  * **Content**:
  ```
  {
      "timestamp": 1499625878987,
      "status": 400,
      "error": "Bad Request",
      "exception": "chatserver.user.exception.UserAlreadyExistsException",
      "message": "User with such name already exists",
      "path": "/api/user"
  }
  ```

## Message

### Send new message
* Method & URL

  ```POST /api/message```
  
* Payload

  from: string - required
  to: string - required
  data: string - required

  ```
  {
    "from": "sender",
    "to": "receiver",
    "data": "Your message"
  }
  ```
  
* Success response
  * **CODE**: 200
  * *Content*:
  ```
  {
    "id": 1,
    "from": {
        "id": 1,
        "name": "sender"
    },
    "to": {
        "id": 2,
        "name": "receiver"
    },
    "data": "Your message",
    "timestamp": 1499629156618,
    "messageStatus": "SENT"
  }
  ```
  
* Error response
  * **CODE**: 400
  * *Content*:
  ```
  {
      "timestamp": 1499638742392,
      "status": 400,
      "error": "Bad Request",
      "exception": "chatserver.user.exception.UserNotFoundException",
      "message": "User not found",
      "path": "/api/message"
  }
  ```
  
### Set message status
* Method & URL

  ```PUT /api/message/{id}/{status}```
  
* Request parameters

  id: long - required (Message ID)
  status: MessageStatus - required (Message Status)
  * RECEIVED - Receiver's client has recieved the message
  * SEEN - Receiver has seen the message
  
* Success response
  * **CODE**: 200
  * *Content*: ```ACCEPTED```
  
* Error response
  * **CODE**: 400
  * *Content*:
  ```
  {
      "timestamp": 1499639058269,
      "status": 400,
      "error": "Bad Request",
      "exception": "chatserver.message.exception.StatusChangeNotPermittedException",
      "message": "Message status change not permitted",
      "path": "/api/message/1/RECEIVED"
  }
  ```
  
### Get previous messages
* Method & URL

  ```GET /api/message/previous_messages```
  
* Request parameters

  from: string - required
  to: string - required
  
* Success response
  * **CODE**: 200
  * *Content*: 
  ```
  [
      {
          "id": 1,
          "from": {
              "id": 1,
              "name": "sender"
          },
          "to": {
              "id": 2,
              "name": "receiver"
          },
          "data": "Your message",
          "timestamp": 1499638990843,
          "messageStatus": "SEEN"
      }
  ]
  ```
  
* Error response
  * **CODE**: 400
  * *Content*:
  ```
  {
      "timestamp": 1499639335044,
      "status": 400,
      "error": "Bad Request",
      "exception": "chatserver.user.exception.UserNotFoundException",
      "message": "User not found",
      "path": "/api/message/previous_messages"
  }
  ```
  
### Get unread messages
* Method & URL

  ```GET /api/message/unread_messages```
  
* Request parameters

  from: string - required
  to: string - required
  
* Success response
  * **CODE**: 200
  * *Content*: 
  ```
  [
      {
          "id": 1,
          "from": {
              "id": 1,
              "name": "sender"
          },
          "to": {
              "id": 2,
              "name": "receiver"
          },
          "data": "Your message",
          "timestamp": 1499638990843,
          "messageStatus": "SENT"
      },
       {
          "id": 2,
          "from": {
              "id": 1,
              "name": "sender"
          },
          "to": {
              "id": 2,
              "name": "receiver"
          },
          "data": "Your message",
          "timestamp": 1499638990844,
          "messageStatus": "RECEIVED"
      }
  ]
  ```
  
* Error response
  * **CODE**: 400
  * *Content*:
  ```
  {
      "timestamp": 1499639335044,
      "status": 400,
      "error": "Bad Request",
      "exception": "chatserver.user.exception.UserNotFoundException",
      "message": "User not found",
      "path": "/api/message/previous_messages"
  }
  ```
