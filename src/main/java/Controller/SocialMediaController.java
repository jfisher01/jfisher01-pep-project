package Controller;

import DAO.AccountDAO;
import Controller.SocialMediaController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.MessageDAO;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    Message message = new Message();
   private  MessageService messageService;
    private  AccountService accountService;

    public SocialMediaController (MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAccountHandler);
    
        app.start(8080);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
  
    private void postMessageHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }

}

   private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService .getAllMessages();
        ctx.json(messages);
    }

  private void getMessageByIdHandler(Context ctx){
        ctx.json(messageService.getAllPostByOneUser(ctx.pathParam("account_id")));
    }


  private void deleteMessageByIdHandler(Context ctx){
        ctx.json(messageService.deleteUserMessageById(ctx.pathParam(message_id)));
    }


private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updatedMessage(message_id,message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }


    public void getAllMessagesFromAccountHandler(Context ctx){
        List<Message> message = messageService.getAllMessages();    
        ctx.json(message);
    }


    public void getAllAccountsHandler(Context ctx){
        List<Account> account = accountService.getAllAccounts();
               ctx.json(account);
       }
       
       public void postAccountHandler(Context ctx)throws JsonProcessingException {
               ObjectMapper mapper = new ObjectMapper();
               Account account = mapper.readValue(ctx.body(), Account.class);
               Account addedAccount = accountService.addAccount(account);
               if(addedAccount!=null){
                   ctx.json(mapper.writeValueAsString(addedAccount));
               }else{
                   ctx.status(400);
               }
}
}