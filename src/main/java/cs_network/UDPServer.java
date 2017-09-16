package cs_network;/* UDPServer.java from Kurose-Ross
   with minor revisions by Amer 8/2002 (cis.udel.edu)
   - updated 10/2002 to fix bug - in the original Kurose-Ross code,
   if the UDPClient is executed a second time with a shorter
   string(without restarting UDPServer), the output will contain
   the shorter string and chars from the first execution's string */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

class UDPServer
{
  public static void main(String args[]) throws Exception
  {
    DatagramSocket serverSocket = new
	  DatagramSocket(9876);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];
    while(true)
      {
      DatagramPacket receivePacket =
	   new DatagramPacket(receiveData, 
				receiveData.length);
      serverSocket.receive(receivePacket);

	 /* String sentence = new String(
      receivePacket.getData());       updated Amer 10/2002 */
      String sentence = new String(
				    receivePacket.getData(), 0, receivePacket.getLength());
          System.out.println("from client:"+sentence);
      InetAddress IPAddress =
	   receivePacket.getAddress();
      int port = receivePacket.getPort();
      String capitalizedSentence = new Scanner(System.in).nextLine();

      sendData = capitalizedSentence.getBytes();
      DatagramPacket sendPacket =
	   new DatagramPacket(sendData, sendData.length, 
				IPAddress, port);
      serverSocket.send(sendPacket);
      }
  }
}
