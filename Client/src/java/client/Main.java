/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ejb.MySessionRemote;
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
        String message = "A;Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        
        channel.close();
        connection.close();
    }
    
}
