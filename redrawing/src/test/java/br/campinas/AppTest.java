package br.campinas;

import br.campinas.redrawing.MessageManager;
import br.campinas.redrawing.data.BodyPose;
import br.campinas.redrawing.data.DataDecoder;
import br.campinas.redrawing.data.IMU;

public class AppTest 
{
    public static void main(String args[])
    {
        DataDecoder decoder = new DataDecoder();

        String jsonString = "{\"time\": 0.52, \"accel\": [1.1,2.2,3.3]}";

        Object data = decoder.decode(jsonString, IMU.class);

        IMU imuData = (IMU)data;

        //System.out.println(imuData.accel[0]);

        jsonString = "{\"time\": 0.52, \"keypoints\": {\"NECK\":[1.1,2.2,3.3]}, data_type:\"BodyPose\"}";
        data = decoder.decode(jsonString);
        BodyPose bodyData = (BodyPose)data;
        //System.out.println(bodyData.time);
        //System.out.println(bodyData.keypoints.get("NECK")[0]);
        //System.out.println(bodyData.keypoints.getClass().getName());

        MessageManager MessageManager = new MessageManager(5,true);

        MessageManager.insertMessage(jsonString);
        
        BodyPose bodypose = MessageManager.getData(BodyPose.class);
        System.out.println(bodypose);
        System.out.println(bodypose.keypoints.get("NECK")[0]);
    }
}
