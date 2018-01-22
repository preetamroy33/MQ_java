import com.ibm.mq.MQMessage;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

public class main {

    private static String hostname,port,userID,password,channel,queuemanager,queue;


    public static void main(String[] args) throws Exception{


        try{

            Properties prop = new Properties();
            InputStream input = null;


            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            hostname = prop.getProperty("hostname");
            port = prop.getProperty("port");
            userID = prop.getProperty("userID");
            password = prop.getProperty("password");
            channel = prop.getProperty("channel");
            queuemanager=prop.getProperty("queuemanager");
            queue=prop.getProperty("queue");



            mq mq = new mq(hostname,port,userID,password,channel,queuemanager,queue);

            //PUT MESSAGE IN QUEUE
//            mq.put_message_data("3");

            //RETRIEVE & DELETE FIRST MESSAGE FROM QUEUE - IT WORKS AS QUEUE
            MQMessage msg = mq.get_message();

            //GET MESSAGE DATA
            int msg_length = msg.getMessageLength();
            String msg_data = msg.readStringOfByteLength(msg_length);

            //WRITE MESSAGE DATA INTO FILE
            String filename = "msg.xml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(msg_data);
            writer.close();




        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
