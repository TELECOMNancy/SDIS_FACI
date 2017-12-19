/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package worker;

import com.rabbitmq.client.*;
import ejb.MySessionRemote;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author root
 */
public class Main {
    
    @EJB
    private static MySessionRemote mySession;
    
    private final static String QUEUE_NAME = "SDIS_Project";
    private final static String MY_URI = "amqp://vceybkad:vSm4bOoyp"
            + "_J8RSQXXcSBvH5zLgot6Bij@gopher.rmq.cloudamqp.com/vceybkad";
    
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(MY_URI);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                    Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException 
            {
                String message = new String(body, "UTF-8");
                String[] messageSplited = message.split(";");
                String key = messageSplited[0];
                String value = messageSplited[1];
                
                try
                {
                    mySession.post(key, value);
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
    
}
