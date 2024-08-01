package Service;
import DAO.MessageDAO;
import java.util.*;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;


    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
     
        return messageDAO.getAllMessages();
    }
    

    public Message addMessage(Message message) {
       if(messageDAO.getMessageById(message.getMessage_id()) == null ){
        return messageDAO.insertMessage(message);
       }
       
              return null;
    }

    public List<Message> getAllAvailableMessages() {
        return messageDAO.getAllMessages();
    }

public ArrayList<Message> getAllPostByOneUser(int id){
    
   
    return   //messageDAO.getAllpostByUser(messages);
}




  public void deleteUserMessageById(int id){
    messageDAO.deleteMessageById(id);
}
}



