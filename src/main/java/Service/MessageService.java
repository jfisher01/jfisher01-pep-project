package Service;

import DAO.MessageDAO;
import java.util.*;
import Model.Message;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * 
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /*
     * 3: Our API should be able to process the creation of new messages.
     * User should be able to submit a new post on the endpoint POST
     * localhost:8080/messages
     * The request body will contain a JSON representation of a message - but will
     * not contain a message_id.
     * - The creation of the message will be successful if and only if the
     * message_text is not blank, is not over 255 characters, and posted_by refers
     * to a real, existing user
     * If successful, the response body should contain a JSON of the message,
     * including its message_id
     * The response status should be 200, which is the default
     * If the creation of the message is not successful, the response status should
     * be 400. (Client error)
     */
    /**
     * 
     * @param message
     * @return
     */
    public Message addMessage(Message message) {

        if ((message.getMessage_text() != "" )  && (message.getMessage_text().length() <= 225)
                && (messageDAO.getMessageById(message.posted_by) != null)) {

            return messageDAO.insertNewMessage(message);
        }
      
        return null;
    }

    /*
     * 4: Our API should be able to retrieve all messages.
     * User should be able to submit a GET request on the endpoint GET
     * localhost:8080/messages.
     * The response body should contain a JSON representation of a list containing
     * all messages retrieved from the database.
     * It is expected for the list to simply be empty if there are no messages
     * The response status should always be 200, which is the default.
     */
    /**
     * 
     * @return
     */
    public List<Message> getAllMessages() {

        if (messageDAO.getAllMessages().equals(null)) {
            return null;
        } else {

            return messageDAO.getAllMessages();
        }

    }

    /*
     * ## 5: Our API should be able to retrieve a message by its ID.
     * User should be able to submit a GET request on the endpoint GET
     * localhost:8080/messages/{message_id}.
     * The response body should contain a JSON representation of the message
     * identified by the message_id
     * It is expected for the response body to simply be empty if there is no such
     * message
     * The response status should always be 200, which is the default.
     */
    /**
     * 
     * @param id
     * @return
     */
    public Message getMessageById(int id) {
        if (messageDAO.getMessageById(id) == null) {

            return null;
        }
        return messageDAO.getMessageById(id);
    }

    /*
     * ## 6: Our API should be able to delete a message identified by a message ID.
     * Our API should be able to delete a message identified by a message ID.
     * User should be able to submit a DELETE request on the endpoint DELETE
     * localhost:8080/messages/{message_id}.
     * The deletion of an existing message should remove an existing message from
     * the database
     * If the message existed, the response body should contain the now-deleted
     * message
     * The response status should be 200, which is the default.
     * If the message did not exist, the response status should be 200, but the
     * response body should be empty
     * multiple calls to the DELETE endpoint should respond with the same type of
     * response.
     */

    /**
     * 
     * @param id
     */
    public Message deleteUserMessageById(int id) {
        if (messageDAO.getMessageById(id) != null) {
            Message message =  messageDAO.getMessageById(id);
            
            messageDAO.deleteMessageById(id);
            return message;
        }
        return null;
    }

    /*
     * # 7: Our API should be able to update a message text identified by a message
     * ID.
     * User should be able to submit a PATCH request on the endpoint PATCH
     * localhost:8080/messages/{message_id}
     * 
     * The request body should contain a new message_text values to replace the
     * message identified by message_id.
     * The request body can not be guaranteed to contain any other information.
     * - The update of a message should be successful if and only if the message id
     * already exists and the new message_text is not blank and is not over 255
     * characters.
     * If the update is successful, the response body should contain the full
     * updated message (including message_id, posted_by, message_text, and
     * time_posted_epoch)
     * the response status should be 200, which is the default
     * The message existing on the database should have the updated message_text.
     * If the update of the message is not successful for any reason, the response
     * status should be 400. (Client error)
     */

    /**
     * 
     * @param message
     * @param message_id
     * @param message_text
     * @return
     */
    public Message updatMessage(Message message, int message_id, String message_text) {

        if ((this.messageDAO.getMessageById(message_id) != null) && (message.getMessage_text() != "")
                && (message.getMessage_text().length() <= 225)) {
            this.messageDAO.updateMessage(message_id, message);

            return this.messageDAO.getMessageById(message_id);

        } else {
            return null;

        }
    }

    /*
     * 8: Our API should be able to retrieve all messages written by a particular
     * user.
     * 
     * As a user, I should be able to submit a GET request on the endpoint GET
     * localhost:8080/accounts/{account_id}/messages.
     * 
     * The response body should contain a JSON representation of a list containing
     * all messages posted by a particular user, which is retrieved from the
     * database. It is expected for the list to simply be empty if there are no
     * messages. The response status should always be 200, which is the default.
     * 
     */

    /**
     * 
     * @param posted_by
     * @return
     */
    public ArrayList<Message> getAllPostByOneUser(int posted_by) {

        if (messageDAO.getAllMessageByThisUser(posted_by).equals(null)) {
            return null;
        }

        else {
            return messageDAO.getAllMessageByThisUser(posted_by);
        }
    }

}
