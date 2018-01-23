import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import org.jetbrains.annotations.Nullable;

public class mq {

    private String queuemanager,queue;

    public mq(String hostname, String port, String userID, String password, String channel,String queuemanager,String queue) {

        //SET MQ ENVIRONMENT VARIABLE TO CONNECT TO QUEUE MANAGER
        MQEnvironment.hostname = hostname;
        MQEnvironment.port = Integer.parseInt(port);
        MQEnvironment.userID = userID;
        MQEnvironment.password = password;
        MQEnvironment.channel = channel;

        this.queuemanager = queuemanager;
        this.queue = queue;


    }


    @Nullable
    public MQMessage get_message(){

        try{

            MQQueueManager qm = new MQQueueManager(queuemanager);

            //QUEUEMANAGER OPEN OPTION FOR INPUT(GET MESSAGE) & INQUIRE(TO CHECK DEPTH)
            int openOptionArg = CMQC.MQOO_INPUT_AS_Q_DEF|CMQC.MQOO_INQUIRE; //INQUIRE for checking depth
            MQQueue q = qm.accessQueue(queue,openOptionArg);

            int depth = q.getCurrentDepth();

            if (depth > 0){

                MQMessage msg = new MQMessage();
                MQGetMessageOptions gmo = new MQGetMessageOptions();

                //MSG IS UPDATED WITH THE MSG RETRIEVED FROM QUEUE
                q.get(msg,gmo);

                return msg;
            }
            else{
                System.out.println("queue is empty");
                System.exit(0);
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void put_message_data(String data){

        try{

            MQQueueManager qm = new MQQueueManager(queuemanager);
            int openOptionArg = CMQC.MQOO_BROWSE|CMQC.MQOO_INPUT_AS_Q_DEF|CMQC.MQOO_OUTPUT|CMQC.MQOO_INQUIRE; //THESE MANY OPEN OPTION IS PROBABLY NOT NEEDE
            MQQueue q = qm.accessQueue(queue,openOptionArg);

            MQMessage msg = new MQMessage();
            msg.writeString(data); //ADD CONTENT TO MSG-DATA
            q.put(msg); //PUT MSG IN QUEUE



        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public int check_depth(){

        try{

            MQQueueManager qm = new MQQueueManager(queuemanager);

            //QUEUEMANAGER OPEN OPTION FOR INPUT(GET MESSAGE) & INQUIRE(TO CHECK DEPTH)
            int openOptionArg = CMQC.MQOO_INPUT_AS_Q_DEF|CMQC.MQOO_INQUIRE; //INQUIRE for checking depth
            MQQueue q = qm.accessQueue(queue,openOptionArg);

            int depth = q.getCurrentDepth();

            return depth;

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

}
