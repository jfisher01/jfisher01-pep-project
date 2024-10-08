package Controller;

import Controller.SocialMediaController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {

    Message message = new Message();
    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController() {

        messageService = new MessageService();
        accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLogIntoAccountHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);
      

        return app;
    }

    /**
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        System.out.println(addedAccount);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.status(200).json(mapper.writeValueAsString(addedAccount));
        }
    }

    /**
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postLogIntoAccountHandler(Context ctx) throws JsonProcessingException {
       
        ObjectMapper mapper = new ObjectMapper();
        Account myAccount = mapper.readValue(ctx.body(), Account.class);
       Account logInAccount = accountService.logIntoAccount(myAccount, "username", "passowrd");
      
        if (logInAccount == null) {
            
            ctx.status(401).status(HttpStatus.UNAUTHORIZED);
        } 
        else {

            ctx.status(200).json(mapper.writeValueAsString(logInAccount));
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */

    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
         ctx.status(200).json(mapper.writeValueAsString(addedMessage));
        } 
        else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {

        List<Message> messages = messageService.getAllMessages();
        
        if(message.equals(null)){
          ctx.status(200);

        }
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) {
         
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
      
        Message return_message = messageService.getMessageById(message_id);
     
        if(return_message != null){
            ctx.status(200).json(return_message);
        }
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

       Message deletedMessage = messageService.deleteUserMessageById(message_id);

        System.out.println("Message deleted!");
        if (deletedMessage == null) {
            ctx.status(200);
        } else {
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }


    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updatMessage(message, message_id, "message_text");
        System.out.println(updatedMessage);
        if (updatedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }

    public void getAllMessagesFromUserHandler(Context ctx) {

      int message_by = Integer.parseInt(ctx.pathParam("account_id"));

      ctx.status(200).json(messageService.getAllPostByOneUser(message_by));
     
      
    }
}