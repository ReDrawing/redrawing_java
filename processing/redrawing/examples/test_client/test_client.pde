/*
Basic test of the library
Run with "python -m redrawing.test_client".

Receives the test bodyPose, and draw it in the screen.

*/

import br.campinas.redrawing.MessageManager;
import br.campinas.redrawing.data.BodyPose;

import hypermedia.net.*; 

//Creates the message manager, setting the message queue size of 5 (per data type) and true for deleting old messages 
MessageManager msgManager = new MessageManager(5, true);

//Uses the UDP Processing library to receive messages
UDP udp;

void setup()
{
  size(300,300);
  udp = new UDP(this, 6000); //Default ReDrawing port
  udp.listen(true);
}

void draw()
{
  clear();
  background(255,255,255);
  
  if(msgManager.hasMessage(BodyPose.class))
  {
    BodyPose bodypose = msgManager.getData(BodyPose.class);
    
    float[] neck = bodypose.keypoints.get("NECK");
    float[] nose = bodypose.keypoints.get("NOSE");
    float[] eye_r = bodypose.keypoints.get("EYE_R");
    float[] eye_l = bodypose.keypoints.get("EYE_L");
    float[] shoulder_r = bodypose.keypoints.get("SHOULDER_R");
    float[] shoulder_l = bodypose.keypoints.get("SHOULDER_L");
    
    fill(0,0,0);
    circle(neck[0], neck[1], 10);
    circle(nose[0], nose[1], 10); 
    circle(eye_r[0], eye_r[1], 10);
    circle(eye_l[0], eye_l[1], 10);
    circle(shoulder_r[0], shoulder_r[1], 10);
    circle(shoulder_l[0], shoulder_l[1], 10);
    
    line(neck[0], neck[1], nose[0], nose[1]);
    line(eye_r[0], eye_r[1], nose[0], nose[1]);
    line(eye_l[0], eye_l[1], nose[0], nose[1]);
    line(neck[0], neck[1], shoulder_r[0], shoulder_r[1]);
    line(neck[0], neck[1], shoulder_l[0], shoulder_l[1]);
    
    fill(0,0,255);
    text("NECK",neck[0], neck[1]);
    text("NOSE",nose[0], nose[1]);
    text("EYE_R",eye_r[0], eye_r[1]);
    text("EYE_L",eye_l[0], eye_l[1]);
    text("SHOULDER_R",shoulder_r[0], shoulder_r[1]);
    text("SHOULDER_L",shoulder_l[0], shoulder_l[1]);
  }
}

//Receive the message and give it to the manager
void receive(byte[] data, String ip, int port)
{
  data = subset(data, 0, data.length);
  String message = new String( data );
  
  
  msgManager.insertMessage(message);
}
