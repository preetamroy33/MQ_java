import com.ibm.mq.MQMessage;

import java.io.*;
import java.util.Properties;

public class main {

    private static String hostname,port,userID,password,channel,queuemanager,queue;
    private static String propertyfilename, operation, put_data;


    public static void main(String[] args) throws Exception{

        if(args.length == 0){
            System.out.println("Please input propertyfilename & operation as commandline input");
            System.out.println("operation can be \"put_data\" or \"check_depth\" or \"get_first\"");
            System.out.println("example usage : java main conf.ini check_depth");
            System.out.println("example usage : java main conf.ini get_first");
            System.out.println("example usage : java main conf.ini put_data \"<input message>\"");

            System.exit(1);
        }
        if(args.length == 1){
            System.out.println("You entered property file name only, Please input propertyfilename & operation as commandline input");
            System.out.println("operation can be \"put_data\" or \"check_depth\" or \"get_first\"");
            System.out.println("example usage : java main conf.ini check_depth");
            System.out.println("example usage : java main conf.ini get_first");
            System.out.println("example usage : java main conf.ini put_data \"<input message>\"");

            java.io.File file = new java.io.File(args[0]);
            if (!file.exists()){
                System.out.println(" input config file doesnot exist");

            }

            System.exit(1);
        }

        propertyfilename = args[0];
        operation = args[1];

        try{

            Properties prop = new Properties();
            InputStream input = null;

            input = new FileInputStream(propertyfilename);

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

            if(operation.equals("check_depth")){

                int depth = mq.check_depth();
                //write("queue_depth",Integer.toString(depth));
                System.out.println(depth);
            }


            if(operation.equals("put_data")){

                if(args.length != 3){
                    System.out.println("you have selected put_data...but have not given the input data");

                    System.out.println("example usage : java main conf.ini put_data \"<input message>\"");

                    System.exit(1);
                }
                put_data=args[2];


                //PUT MESSAGE IN QUEUE
                mq.put_message_data(put_data);

            }


            if(operation.equals("get_first")){

//                RETRIEVE & DELETE FIRST MESSAGE FROM QUEUE - IT WORKS AS QUEUE
            MQMessage msg = mq.get_message();

            //GET MESSAGE DATA
            int msg_length = msg.getMessageLength();
            String msg_data = msg.readStringOfByteLength(msg_length);

            //write("msg.xml",msg_data);
                System.out.println(msg_data);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void write(String filename,String data){


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
