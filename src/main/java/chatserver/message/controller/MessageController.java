package chatserver.message.controller;

import chatserver.message.enums.MessageStatus;
import chatserver.message.model.Message;
import chatserver.message.model.SimpleMessage;
import chatserver.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.POST)
    public Message sendMessage(@RequestBody SimpleMessage simpleMessage) {
        return messageService.sendMessage(simpleMessage);
    }

    @RequestMapping(path = "/{id}/{status}", method = RequestMethod.PUT)
    public HttpStatus setMessageStatus(@PathVariable Long id, @PathVariable MessageStatus status) {
        messageService.setMessageStatus(id, status);
        return HttpStatus.OK;
    }

    @RequestMapping(path = "/previous_messages", method = RequestMethod.GET)
    public List<Message> getPreviousMessages(@RequestParam("from") String from,
                                             @RequestParam("to") String to) {
        return messageService.getPreviousMessages(from, to);
    }

    @RequestMapping(path = "/unread_messages", method = RequestMethod.GET)
    public List<Message> getUnreadMessages(@RequestParam("from") String from,
                                           @RequestParam("to") String to) {
        return messageService.getUnreadMessages(from, to);
    }

}
