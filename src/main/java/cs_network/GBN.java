package cs_network;/*
 *    This Go Back N Applet was designed to be used in conjunction with
 *    "Computer Networking: A Top Down Approach"
 *    by James Kurose & Keith Ross.
 *    Terminology and specifications are based upon their description of the
 *    Go Back N protocol in chapter 3, section 4.
 *
 *    Original Applet coded by Shamiul Azom as project assigned by 
 *    Prof. Martin Reisslein, Arizona State University
 *    Course No. EEE-459/591. Spring 2001
 *
 *    Applet significantly revised by Matthew Shatley and Chris Hoffman
 *    for Professor Paul D. Amer (amer@udel.edu)
 *    University of Delaware (2008)
 *    
 *    Applet updated Feb. 2012 by Chris Hoffman to fix GoBackN text output
 *    to reflect the single timer implementation of Go Back N, as outlined
 *    on pg 234 in the 5th edition of the aforementioned text.
 *
 *    A note on magic numbers: Magic numbers are horrible to have in your code in general.
 *    However, the graphics components of this applet provided no good way to remove the
 *    magic numbers from the code as locations for objects are specified in pixel coordinates. 
 *    We apologize in advance for any confusion this may cause in reading the code. 
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GBN extends Applet implements ActionListener, Runnable {
   
   private static final int ADVANCE_PACKET = 5;
   // Default values of parameters for animation
   // sender_window_len_def the sender can have a maximum of 5 outstanding
   // un-acknowledged packets
   final int sender_window_len_def = 5;
   // how many packets the receiver can hold in memory without delivering data
   // in the case of GBN we can hold 1(or the current packet) in memory
   // if another packet arrives the one in memory is discarded
   final int receiver_window_len = 1;
   // GUI components to describe how the Simulation should be drawn
   final int pack_width_def = 10;
   final int pack_height_def = 30;
   final int h_offset_def = 100;
   final int v_offset_def = 50;
   final int v_clearance_def = 300;

   //used for timeout values, thread.sleep() is specified in milliseconds
   //so we convert to seconds for timeout processing.(1000 milliseconds = 1 second)
   final int TIMEOUT_MULTIPLIER = 1000;

   final int MIN_FPS = 3;
   final int FPS_STEP = 2;
   final int DESELECTED = -1;
   final int DEFAULT_FPS = 5;
   //default to 20 packets if no value is supplied
   final int total_packet_def = 20;
   // 25 sec default timeout for retransmissions
   final int time_out_sec_def = 25;
   
   // Default colors of different packets
   // these have been matched as closely to the the text as possible
   // Order of color values Red, Green, Blue 
   final Color unack_color = new Color(204, 230, 247);
   final Color ack_color = Color.yellow;
   final Color sel_color = Color.green;
   final Color roam_pack_color = new Color(204, 230, 247);
   final Color roam_ack_color = Color.yellow;
   final Color dest_color = Color.red;
   final Color received_ack = new Color(37, 135, 234);
   
   // base - our sending base - the next expected packet to be received
   // nextseq - the next sequence number that will be given to a newly created
   // packet
   // selected - the index of the currently selected packet in transmission
   // lastKnownSucPacket - LAST KNOWN SUCcessful PACKET received by receiving
   // node
   int base, receiver_base, nextseq, fps, selected = DESELECTED, timeout,
      timeoutPacket, lastKnownSucPacket;
   boolean timerFlag, timerSleep;
   
   // define our buttons for actions available to be taken by the user
   Button send, stop, fast, slow, kill, reset;
   /*
    * 2 threads run for the applet gbnTread - runs to create our animation and
    * process packets timerThread - created and sleeps for a specified period
    * of time. On wake up performs timeout processing A timeout causes all of
    * the outstanding packets to be re-transmitted. NOTE: The text(Computer
    * Networking: A Top Down Approach) specified a per packet timer, however
    * this is rarely implemented as there is a significant overhead in using
    * that many timers. Logically, the only packet that would ever timeout is
    * the left most edge of the sending window as this has been in transmission
    * the longest. Since a per packet timer system is not implemented in
    * practice we have simulated per packet timers per the books description
    * while using only a single timer.
    */
   Thread gbnThread, timerThread;
   
   TextArea output; // output variable used to write information in the text
   // box
   Dimension offDimension;
   Image offImage; // implements double buffering to proved a smoother
   // animation
   Graphics offGraphics; // graphics component used for drawing
   GoBackNPacket sender[]; // sender array - holds the packets being sent
   
   // Declaring properties of our window
   int window_len, pack_width, pack_height, h_offset, v_offset, v_clearance,
      total_packet, time_out_sec;
   
   
   
   /**************************************************************************
    *                            Method init                                 *
    * ************************************************************************
    * Purpose: init method to set up applet for running - first method called
    *    on loading the code. Attempts to load parameters passed from HTML code
    *    contained in the website. If there is an error or no parameters are
    *    provided then the default values(declared above) are used.
    * Global variables used: sender - array holding the packets and the
    *    corresponding acks for the packets sent in the applet 
    * output - console window for applet activities & messages
    **************************************************************************/
   public void init() {
      
      // prevents layout manager from adjusting components in the applet
      // The buttons made it easier to deal with pixel coordinates
      // than recode for layout manager
      setLayout(null);
      output = new TextArea(150, 150); // setup output box
      // create text area for console output box
      output.setBounds(0, 400, 650, 250); // set bounds for output box
      output.setEditable(false); // prevent user from editing output written
      // to console
      add(output); // tell applet to draw our output box
      
      setupSimulationParams();
      
      base = 0; // Defining our base to be 0 the first packet number
      // expected
      receiver_base = 0; // Set the receiver base number to 0, which is the
      // first index in the receiver array
      nextseq = 0; // Defining next sequence number for next packet sent.
      fps = DEFAULT_FPS; // Defining default Frame per Second for our animation

      
      // create a shared array, used for both the sender and the receiver
      // nodes.
      // all packets will be created and processed from this array
      sender = new GoBackNPacket[total_packet];
      
      // Defining the buttons - creates the button and text to go on the
      // button
      send = new Button("Send New");
      // set the command to be performed when button is pressed this command
      // is used
      // to determine which button was pressed in the actionPerformed method
      send.setActionCommand("rdt");
      // on button pushed the actionPerformed method of this class is called
      // and appropriate action is taken depending on the button pressed
      send.addActionListener(this);
      // set the size and location of this button (of form (x, y, width,
      // length)
      // this is specified in pixel coordinates
      send.setBounds(0, 0, 90, 20);
      
      // same structure as above
      stop = new Button("Pause");
      stop.setActionCommand("stopanim");
      stop.addActionListener(this);
      stop.setBounds(90, 0, 90, 20);
      
      fast = new Button("Faster");
      fast.setActionCommand("fast");
      fast.addActionListener(this);
      fast.setBounds(180, 0, 90, 20);
   
      slow = new Button("Slower");
      slow.setActionCommand("slow");
      slow.addActionListener(this);
      slow.setBounds(270, 0, 90, 20);
      
      kill = new Button("Kill Packet/Ack");
      kill.setActionCommand("kl");
      kill.addActionListener(this);
      kill.setEnabled(false);
      kill.setBounds(360, 0, 90, 20);
      
      reset = new Button("Reset");
      reset.setActionCommand("rst");
      reset.addActionListener(this);
      reset.setBounds(450, 0, 90, 20);
      
      // Adding the buttons to our applet window so they can be rendered and
      // used
      add(send);
      add(stop);
      add(fast);
      add(slow);
      add(kill);
      add(reset);
      
      // print out message about the new authors of the code
      output.append("- GoBackN Applet written by Matt Shatley & Chris Hoffman\n");
      output.append("- Advised by Professor Paul D. Amer (amer@udel.edu), U of Delaware, 2008\n");
      output.append("- Updated by Chris Hoffman and Professor Amer, 2012\n\n");
      
      // tell user we are ready to begin demonstrating Go Back N
      output.append("-Ready to run. Press 'Send New' button to start.\n");
      
   }// End init() method
   
   /***************************************************************************
    *                             Method Start                                *
    * *************************************************************************
    * Purpose: Start method required for implementing multi-threading. Start is
    *   the first method called by a thread after creation. 
    * Procedures Calling: run    
    * Procedures Called: run     
    * Global Variables Used: gbnThread - creates new thread for first 
    *   execution and starts thread(calling run method of thread)
    ***************************************************************************/
   public void start() {
      // Creating GBNThread and starting execution. After start method is run
      // the run method of this class is called
      if (gbnThread == null)
	 gbnThread = new Thread(this);
      gbnThread.start();
   }// End start() method
   
   /***************************************************************************
    *                              Method run                                 * 
    * *************************************************************************
    * Purpose: Run method required for runnable interface. Determines which
    * thread is calling and begins animation processing accordingly. gbnThread
    * produces the animation for the applet. The timerThread sleeps until
    * timeout processing is needed to retransmit the sending window.
    * Procedures/Functions Called: check_upto_n, paint/update(indirectly)
    * Procedures/Functions Calling: main, start
    * Local variables: currentThread - holds the identifier for the currently
    * executing thread i - temporary variable used for loop control
    * Global variables used: sender - array holding the packets and the
    *   corresponding acks for the packets sent in the applet output - console
    *   window to display information about the applet activities
    *   lastKnownSucPacket - holds the number of the last successful packet to
    *   arrive gbnThread - thread to advance animation
    **************************************************************************/
   
   public void run() {
      // test if our simulation has completed. Prevents animation from looping
      // over
      // no inputs. Terminates threads and exits method.
      if (sender[total_packet - 1] != null)
	 if (sender[total_packet - 1].acknowledged) {
	    sender[total_packet - 1].packet_pos += ADVANCE_PACKET;
	    gbnThread = null;
	    output.append("Data Transferred - Simulation completed\n");
	    return;
	 }
      /*
       * determine which thread called the run method since both the GBN
       * simulation thread and the timer thread call the same run method.
       */
      System.gc();
     Thread currenthread = Thread.currentThread();
      
      // while the gbnThread (Go Back N simulation thread) is calling
      // animation advance the animation accordingly.
      while (currenthread == gbnThread) {
	 /*
	  * Explanation of the following if structure:
	  * if any of the packets in the sender array are in
	  * transit between nodes then the location variable is incremented by
	  * ADVANCE_PACKET(5 pixels by default) for that packet. Once the packet 
	  * has reached the receiving node the variable reached_dest is set to true. 
	  * Processing and sending of ACKs will then takes place.
	  */
	 if (onTheWay(sender)) {
	    for (int i = 0; i < total_packet; i++)
	       if (sender[i] != null) // if a packet has been created at this index in array
		  if (sender[i].on_way) // If this packet is currently being transmitted
			    // Test if the packet has reached the destination node
			    // if the packet has not reached the destination then
			    // advance the packet's location by ADVANCE_PACKET
		     if (sender[i].packet_pos < (v_clearance - pack_height))
			sender[i].packet_pos += ADVANCE_PACKET; // and

	    
	    // If a packet is moving towards the destination
		     else if (sender[i].packet_ack) {
			deliverPackets(i);
		     } else if (!sender[i].packet_ack) {
			// check if the packet this acknowledges is our
			// next expected
			// sequence number
			if (sender[i].ackFor != i) {
			   // extract what packet this ack is for
			   int location = sender[i].ackFor;
			   // check to see if we have processed the ack
			   // as the original ack for this packet may
			   // have been lost
			   if (!sender[location].acknowledged) {
			      // print out confirmation message,
			      // process packet, and perform GBN
			      // processing
			      output.append("(S) - Cumulative Ack received for Packet(s) up to and including Packet " + location + ". Timer for Packet(s) up to and including " + location + " stopped\n");
			      sender[location].received = true;
			      sender[location].on_way = false;
			      sender[location].acknowledged = true;
			      sender[i].packet_ack = false;
			      sender[i].on_way = false;
			      
			      // do go back n processing for every
			      // packet up to the location of this
			      // packet
			      simGoBackN(location);
			      
			   } else {
			      /*
			       * else this is a duplicate ack. Go Back
			       * N performs no action on duplicate
			       * acks received. process packet and
			       * ignore
			       */
			      output.append("(S) - Cumulative Ack for Packet(s) up to and including " + sender[i].ackFor + " received again (DUPACK)\n");
			      sender[i].packet_pos = pack_height + ADVANCE_PACKET;
			      sender[i].packet_ack = false;
			      sender[i].on_way = false;
			   }
			} else {
			   // else this is an ack for our next expected
			   // sequence number
			   output.append("(S) - Cumulative Ack received for Packet(s) up to and including  " + i  + "\n");
			   output.append("(S) - Stop timer\n");
			   
			   // check to see if there is another packet in transit
			   // in case we need to restart the timer
			   if(i < total_packet_def && sender[i + 1] != null)
			   {
				   output.append("(S) - Start timer (for Packet " + (i+1) + ")\n");
			   }
			   
			   sender[i].received = true;
			   sender[i].on_way = false;
			   
			   // perform go back n processing for all
			   // packets up to index i in sender array
			   simGoBackN(i);
			}
		     }
	    // update our display
	    repaint();
	    // sleep to display a reasonable fps for the animaition.
	    // without this the animation would process faster than the user
	    // can see.
	    
	    try {
	       Thread.sleep(TIMEOUT_MULTIPLIER / fps);
	       timeout = (TIMEOUT_MULTIPLIER / fps);
	       // if we get interupted something outside of our control has
	       // gone wrong.
	       // this should only be interrupted by a browser error
	    } catch (InterruptedException e) {
	       output.append("-Help\n");
	    }
	 }// end if
	 else
	    gbnThread = null;
      }
      
      while (currenthread == timerThread) {
	 // if timerSleep is true we have told the timerthread to wait a
	 // specified period of time(time_out_sec*TIMEOUT_MULTIPLIER)
	 // before checking to see if any packets are still outstanding.
	 if (timerSleep) {
	    timerSleep = false;
	    try {
	       Thread.sleep(time_out_sec * TIMEOUT_MULTIPLIER);
	       timeout = (time_out_sec * TIMEOUT_MULTIPLIER);
	    } catch (InterruptedException e) {
	       output.append("-Timer interrupted.\n");
	       return;
	    }
	 } else
	    retransmitOutstandingPackets();
      }// end while
   }// End run() method
   
   /*********************************************************************
    *                  Method retransmitOutstandingPacket               *
    * *******************************************************************
    * Purpose: handles transmission of packets when a timeout occurs
    * Procedure calling: run(called by timerThread)
    * Procedures called: none
    * Global variables used: sender[] - to set up params for retransmission 
    *   timerSleep - to reset timer value
    *   GBNThread - set animation thread for retransmissio
    *   output - output messages to user about retransmission
    *   base - number of left-most packet in the sending window
    * Local variables: n - used as loop control variable
    *********************************************************************/
   private void retransmitOutstandingPackets() {
      // after the timerThread wakes up process the packets in sender
      // array from the base of our window (the leftmost edge)
      
      // up to the last packet send
      for (int n = (base == 0) ? 0 : base - 1; n < base + window_len; n++) {
	 if (sender[n] != null)
	    if (!sender[n].acknowledged) {
	       sender[n].on_way = true;
	       sender[n].packet_ack = true;
	       sender[n].packet_pos = pack_height + ADVANCE_PACKET;
	       sender[n].ackFor = n;
	    }
	 timerSleep = true;
	 
	 if (gbnThread == null) {
         gbnThread = new Thread(this);
         gbnThread.start();
	 }
	 
      }// end for
      //test for border case -- needs cleanup
      
      for(int i = base; i < total_packet; i++)
    	  if(sender[i].acknowledged == false)
    	  {
      		  output.append("(S) - Timeout occurred (for Packet " + (i) + ") \n");
    		  break;
    	  }
      output.append("(S) - All outstanding Packet(s) from " + base + " to " + (nextseq - 1) + " are retransmitted. Start timer (for Packet "+ base +")\n");
   }
   
   /***************************************************************************
    *                        Method simGoBackN                                *
    * *************************************************************************
    * Purpose: Simulates the Go Back N protocol. The "heart" of the Go Back N
    * processing. Method handles the acknowledging up to the ack we just
    * received.
    * Procedures/Functions Calling: run
    * Local variables: i - the index in the array to check up to - comes from the
    * packet we just received 
    * Global variables used: sender - array holding the packets and the 
    *   corresponding acks for the packets sent in the applet
    *   output - console window to display information about the applet
    *   activities
    *   lastKnownSucPacket - holds the number of the last successful packet to
    *   arrive gbnThread - thread to advance animation
    ***************************************************************************/
   public void simGoBackN(int i) {
      // set all packets in the sender array up to index i (our ack that just
      // arrived) to acknowledged per go back n specs.
      for (int n = 0; n <= i; n++) {
	 sender[n].acknowledged = true;
      }
      // if our packet was selected clear the selection bit.
      if (i == selected) {
	 selected = DESELECTED;
	 kill.setEnabled(false);
      }
      
      timerThread = null; // resetting timer thread
      // increment our base value to reflect the new ack we just received
      if (i + window_len < total_packet)
	 base = i + 1;
      
      // if we have room in our window allow the user to send a new packet
      if (nextseq < base + window_len)
	 send.setEnabled(true);
      
      if (base != nextseq) {
	 timerThread = new Thread(this);
	 timerSleep = true;
	 timerThread.start();
      } else
	 // set out of order to false in order to control the last known
	 // packet received
	 sender[i].out_of_order = false;
   }
   
   /********************************************************************
    *                       Method deliverPackets                      *
    * ******************************************************************
    * Purpose: process incomming acknowledgments. handle incrementing the
    *   window and informing user of ack arrival
    * Procedures Calling: run
    * Procedures Called: none
    * Local Variables: none
    * Global Variables: packetNumber -index into sender of arriving packet
    *   sender[] - array of packets updated to reflect a new ack has arrived
    *   output - display information to user about ack that just arrived
    *   base - number of left most packet in sending window
    **************************************************************************/
   private void deliverPackets(int packetNumber) {
      sender[packetNumber].reached_dest = true;
      
      // if this packet is an acknowledgment for any
      // other packet than itself, it is a duplicate
      // ack. print out a message telling the user the
      // packet is a duplicate
      if (sender[packetNumber].ackFor != packetNumber) {
	 output.append("(S) Cumulative Ack for Packets up to and including " + sender[packetNumber].ackFor + " received again (DUPACK)\n");
	 sender[packetNumber].packet_pos = pack_height + ADVANCE_PACKET;
	 sender[packetNumber].packet_ack = false;
	 
	 // if all packets have been received in
	 // order, print confirmation message of
	 // packet arrival and process ack normally
	 
	 // check for in-order delivery
      } else if (check_upto_n(packetNumber) && packetNumber >= receiver_base) {
	 sender[packetNumber].packet_pos = pack_height + ADVANCE_PACKET;
	 sender[packetNumber].packet_ack = false;
	 lastKnownSucPacket = packetNumber;
	 output.append("(R) - Packet " + packetNumber + " received. Cumulative Ack for Packets up to and including " + packetNumber  + " sent. Packet " + packetNumber + " delivered to application\n");
	 // advance the receivers next expected
	 // packet window while
	 // the base less than our max packets
	 if (receiver_base + 1 < total_packet && receiver_base <= lastKnownSucPacket)
	    receiver_base = receiver_base + 1;
      }
      
      // Handle the out of order packets by
      // creating a duplicate ack for the last
      // in order packet received
      else {
	 // handles the special case where the first packet is lost - otherwise any packet
	 // that arrives will acknowledge packet 0 even though packet 0 has been lost
	 if (base == 0 && sender[0].packet_ack && receiver_base ==0) {
	    output.append("(R) - Packet " + packetNumber + " received out of order - no Packets acknowledged. Special case -  No Ack sent\n");
	    sender[packetNumber].packet_pos = pack_height + ADVANCE_PACKET;
	    sender[packetNumber].on_way = false;
	    sender[packetNumber].reached_dest = false;

	 }
	 //for duplicate acks
	 else if(packetNumber < receiver_base){
	    sender[packetNumber].packet_pos = pack_height + ADVANCE_PACKET;
	    sender[packetNumber].packet_ack = false;
	    
	    // base is stored as the next expected packet number so our last in packet
	    // received is 1 less than the current value of base when base is 0 we have not
	    // received any packets but we can't signify this without having an array out 
	    // of bounds error
	    output.append("(R) - Packet " + packetNumber + " received out of order Dropping Packet " + packetNumber + ". Cumulative Ack for Packets up to and including " + (lastKnownSucPacket) + " sent\n");
	 }
	 // all other packet arrivals handled below
	 else {
	    sender[packetNumber].packet_pos = pack_height + ADVANCE_PACKET;
	    sender[packetNumber].packet_ack = false;
	    
	    // base is stored as the next expected packet number so our last in packet
	    // received is 1 less than the current value of base when base is 0 we have not
	    // received any packets but we can't signify this without having an array out 
	    // of bounds error
	    sender[packetNumber].ackFor = lastKnownSucPacket;
	    sender[packetNumber].reached_dest = false;
	    sender[packetNumber].out_of_order = true;
	    
	    output.append("(R) - Packet " + packetNumber + " received out of order. Dropping Packet " + packetNumber + ". Cumulative Ack for Packets up to and including " + (lastKnownSucPacket) + " sent\n");
	    if (packetNumber == selected) {
	       selected = DESELECTED;
	       kill.setEnabled(false);
	    }
	 }
      }
   }
   
   /*********************************************************************
    *                        Method setupSimulationParams               *
    * *******************************************************************
    * Purpose: Extract simulation parameters from the HTML page the applet
    * is being executed from. If the parameter is supplied convert to value
    * to integer and check for greater than 0(less than 0 will throw exceptions)
    * if the value supplied is in range, assign that value to the simulation
    * parameter
    * Global variables used: window_len,pack_widt, pack_height, h_offset, v_offset,
    *   v_clearance, total_packet, time_out_sec
    **************************************************************************/
   private void setupSimulationParams() {
      
      String strWinLen, strPackWd, strPackHt, strHrOff, strVtOff, strVtClr, strTotPack, strTimeout;
      
      // Start collecting parameters from HTML the applet is called from
      strWinLen = getParameter("window_length");
      strPackWd = getParameter("packet_width");
      strPackHt = getParameter("packet_height");
      strHrOff = getParameter("horizontal_offset");
      strVtOff = getParameter("vertical_offset");
      strVtClr = getParameter("vertical_clearance");
      strTotPack = getParameter("total_packets");
      strTimeout = getParameter("timer_time_out");
      
      // try to retrieve the expected parameters we read in from above
      try {
	 //check if current param was supplied in HTML page
	 if (strWinLen != null) {
	    //if param was supplied convert value to integer value
	    window_len = Integer.parseInt(strWinLen);
	    //check if value supplied is greater than 0 (negative or 0 will cause simulation errors)
	    //conditional assignment - if window_leng is greater than 0, window_len keeps its current value otherwise the default value(sender_window_len_def) is uesd
	    window_len = (window_len > 0) ? window_len : sender_window_len_def;
	 } else
	    //if param was not supplied use default value
	    window_len = sender_window_len_def;

	 //same structure as above
	 if (strPackWd != null) {
	    pack_width = Integer.parseInt(strPackWd);
	    pack_width = (pack_width > 0) ? pack_width : pack_width_def;
	 } else
	    pack_width = pack_width_def;

	 if (strPackHt != null) {
	    pack_height = Integer.parseInt(strPackHt);
	    pack_height = (pack_height > 0) ? pack_height : pack_height_def;
	 } else
	    pack_height = pack_height_def;

	 if (strHrOff != null) {
	    h_offset = Integer.parseInt(strHrOff);
	    h_offset = (h_offset > 0) ? h_offset : h_offset_def;
	 } else
	    h_offset = h_offset_def;

	 if (strVtOff != null) {
	    v_offset = Integer.parseInt(strVtOff);
	    v_offset = (v_offset > 0) ? v_offset : v_offset_def;
	 } else
	    v_offset = v_offset_def;

	 if (strVtClr != null) {
	    v_clearance = Integer.parseInt(strVtClr);
	    v_clearance = (v_clearance > 0) ? v_clearance : v_clearance_def;
	 } else
	    v_clearance = v_clearance_def;

	 if (strTotPack != null) {
	    total_packet = Integer.parseInt(strTotPack);
	    total_packet = (total_packet > 0) ? total_packet
	       : total_packet_def;
	 } else
	    total_packet = total_packet_def;

	 if (strTimeout != null) {
	    time_out_sec = Integer.parseInt(strTimeout);
	    time_out_sec = (time_out_sec > 0) ? time_out_sec
	       : time_out_sec_def;
	 } else
	    time_out_sec = (time_out_sec > 0) ? time_out_sec
	       : time_out_sec_def;
	 
	 //exception converting to integer - if a non integer value is supplied conversion to an integer value will throw an exception
	 //if an exception is thrown, keep supplied values(already checked) and use default values for rest of params. 
      } catch (Exception e) {
	 // if above fails use what values we have and defaults for the rest
	 // should recover more gracefully than previous code
	 window_len = (window_len > 0) ? window_len : sender_window_len_def;
	 pack_width = (pack_width > 0) ? pack_width : pack_width_def;
	 pack_height = (pack_height > 0) ? pack_height : pack_height_def;
	 h_offset = (h_offset > 0) ? h_offset : h_offset_def;
	 v_offset = (v_offset > 0) ? v_offset : v_offset_def;
	 v_clearance = (v_clearance > 0) ? v_clearance : v_clearance_def;
	 total_packet = (total_packet > 0) ? total_packet : total_packet_def;
	 time_out_sec = (time_out_sec > 0) ? time_out_sec : time_out_sec_def;
      }
      
   }
   
   /********************************************************************
    *                 Method actionPerformed                           *
    * ****************************************************************** 
    * Purpose: actionPerformed method required to be an action listener class.
    * Determines which button in the animation is pressed (ie send new, stop
    * animation, kill packet/ack, ...)  
    * Procedures/Functions Called: paint/update i - temporary variable used for 
    *   loop control
    * Global variables used: sender - array holding the packets and the
    * corresponding acks for the packets sent in the applet nextSeq - the next
    * unused sequence number for a packet
    **************************************************************************/
   
   public void actionPerformed(ActionEvent e) {
      
      // get what button called the method and perform appropriate action
      String cmd = e.getActionCommand();
      
      // user pressed the send new button check if we can send a new packet
      if (cmd == "rdt" && nextseq < base + window_len) {
	 // create our new packet in the sender array
	 sender[nextseq] = new GoBackNPacket(true, pack_height + ADVANCE_PACKET, nextseq);
	 // tell user the packet was successfully created and sent
	 output.append("(S) - Packet " + nextseq + " sent\n");
	 if(timerThread == null)
		 output.append("(S) - Start timer (for Packet " + nextseq + ")\n");
	 else
		 output.append("(S) - Timer already running\n");
	 if (base == nextseq) // i.e. the window is empty and new data is
	    // comming in
	    {
	       // start the timer thread for timeout processing
	       if (timerThread == null)
		  timerThread = new Thread(this);
	       timerSleep = true;
	       timerThread.start();
	    }
	 
	 repaint();
	 nextseq++;
	 if (nextseq == base + window_len)
	    send.setEnabled(false);
	 start();
      }
      
      // user wants to increase speed of animation
      else if (cmd == "fast") // Faster button pressed
	 {
	    fps += FPS_STEP;
	    output.append("-Simulation speed increased\n");
	 }
      
      // user wants to decrease speed of animation
      else if (cmd == "slow" && fps > MIN_FPS) {
	 fps -= FPS_STEP;
	 output.append("-Simulation speed decreased\n");
      }
      // pause animation
      
      // stop the animation from running to allow user to read status messages
      // and examine packets in transmission
      else if (cmd == "stopanim") {
	 output.append("- Simulation paused\n");
	 gbnThread = null;
	 
	 if (timerThread != null) {
	    timerFlag = true;
	    timerThread = null; // added later
	 }
	 // change our stop button to allow the user to resume the simulation
	 stop.setLabel("Resume");
	 stop.setActionCommand("startanim");
	 
	 // disableing all the buttons we dont allow user to perform actions
	 // during paused sim
	 send.setEnabled(false);
	 slow.setEnabled(false);
	 fast.setEnabled(false);
	 kill.setEnabled(false);
	 
	 repaint();
      }
      
      // resumes animation after it was paused.
      else if (cmd == "startanim") {
	 output.append("-Simulation resumed.\n");
	 stop.setLabel("Pause");
	 stop.setActionCommand("stopanim");
	 
	 if (timerFlag) {
	    timerThread = new Thread(this);
	    timerSleep = true;
	    timerThread.start();
	 }
	 
	 // enabling the buttons
	 send.setEnabled(true);
	 slow.setEnabled(true);
	 fast.setEnabled(true);
	 kill.setEnabled(true);

	 //repaint to show updated simulation
	 repaint(); 
	 start();
	 
      }
      
      // lose selected packet in transmisson
      else if (cmd == "kl") {
	 if (sender[selected].packet_ack) {
	    output.append("-Packet " + selected + " lost\n");
	 } else
	    output.append("-Cumulative Ack of Packet " + selected + " lost.\n");
	 
	 sender[selected].on_way = false;
	 kill.setEnabled(false);
	 selected = DESELECTED;
	 repaint();
      }
      
      // reset animation to initial view
      else if (cmd == "rst")
	 reset_app();
   }
   
   /***************************************************************************
    *                         Method mouseDown                                *
    * *************************************************************************
    * Purpose: Determines when the mouse is pressed down and what
    * object(packet) is currently under the mouse. mouseDown is used to select
    * a packet in transmission to be killed(possibly) 
    * Global variables used: sender - array holding the packets and the
    *    corresponding acks for the packets sent in the applet
    * output - console window to display information about the applet
    *    activities
    **************************************************************************/
    public boolean mouseDown(Event e, int x, int y) {
	int location, xpos, ypos;
	location = (x - h_offset) / (pack_width + 7);
	//for clicking off of currently selected packet - also prevents index out of bounds exceptions
	if (location >= total_packet || location < 0){
	    selected = DESELECTED;
	    return false;
	}
	if (sender[location] != null) {
	    xpos = h_offset + (pack_width + 7) * location;
	    ypos = sender[location].packet_pos;
	    
	    if (x >= xpos && x <= xpos + pack_width && sender[location].on_way) {
	    	if ((sender[location].packet_ack && y >= v_offset + ypos && y <= v_offset + ypos + pack_height) || 
		       ((!sender[location].packet_ack) && y >= v_offset + v_clearance - ypos && y <= v_offset + v_clearance - ypos + pack_height)) {
		    if (sender[location].packet_ack)
			output.append("-Packet " + location + " selected.\n");
		    else
			output.append("-Cumulative Ack " + location + " selected.\n");
		
		    sender[location].selected = true;
		    selected = location;
		    kill.setEnabled(true);
		    repaint();
		    
		} else {
		    output.append("-Click on a moving Packet to select.\n");
		    selected = DESELECTED;
		}
	    } else {
		output.append("-Click on a moving Packet to select.\n");
		selected = DESELECTED;
	    }
      }
      
      return true;
   }
    
    /********************************************************************
    *                   Method paint                                   *
    * ****************************************************************** 
    * Purpose: Allows a graphics context to be established for drawing 
    * Procedures/Functions Called: update 
    * Procedures/Functions Calling: main, start, run 
    * Local variables: g - Graphics object for drawing functionality
    **************************************************************************/
   public void paint(Graphics g) // To eliminate flushing, update is
   // overriden
   {
      update(g);
   }
   
   /********************************************************************
    *                         Method Update                            *
    * ******************************************************************
    * Purpose: Handles the actual drawing for the applet. Draws the packets,
    * message boxes, ... 
    * Procedures/Functions Called:check_upto_n, paint/update(indirectly) 
    * Procedures/Functions Calling: paint 
    * Local variables: i - temporary variable used for loop control
    * Global variables used: sender - array holding the packets and the
    *    corresponding acks for the packets sent in the applet
    * offGraphics - used to create a secondary buffer to draw the necessary
    * components before putting the completed drawing to screen. This prevents
    * "flashing" when viewing the applet on higher frame rates
    **************************************************************************/
   public void update(Graphics g) {
      Dimension d = size();
      
      // Create the offscreen graphics context, if no good one exists.
      if ((offGraphics == null) || (d.width != offDimension.width)
	  || (d.height != offDimension.height)) {
	 offDimension = d;
	 offImage = createImage(d.width, d.height);
	 offGraphics = offImage.getGraphics();
      }
      
      // Erase the previous image.
      offGraphics.setColor(Color.white);
      offGraphics.fillRect(0, 0, d.width, d.height);
      
      // drawing window
      offGraphics.setColor(Color.black);
      // Sender window defining the top left, and bottom right coordinates of
      // the rectangle.
      
      offGraphics.draw3DRect(h_offset + base * (pack_width + 7) - 4, v_offset - 3, (window_len) * (pack_width + 7) + 1, pack_height + 6, true);
      // Receiver window. Note: the 222 is used to relocate the box based on
      // the v_offset variable, which is located in the senders box
      offGraphics.draw3DRect(h_offset + receiver_base * (pack_width + 7) - 4,v_offset + 222, ((receiver_window_len) * (pack_width + 7) + 1),pack_height + 6, true);
      
      // walk through our sender array and gather information about how to
      // draw packets
      for (int i = 0; i < total_packet; i++) {
	 // print out numbers over our packets for easy reference
	 offGraphics.setColor(Color.black);
	 offGraphics.drawString("" + i, h_offset + (pack_width + 7) * i, v_offset - 4);
	 offGraphics.drawString("" + i, h_offset + (pack_width + 7) * i, v_offset + v_clearance + 30);
	 
	 // if no packet has been created at our current index draw the
	 // packet as a black rectangle
	 if (sender[i] == null) {
	    offGraphics.setColor(Color.black);
	    offGraphics.draw3DRect(h_offset + (pack_width + 7) * i,v_offset, pack_width, pack_height, true);
	    offGraphics.draw3DRect(h_offset + (pack_width + 7) * i,v_offset + v_clearance, pack_width, pack_height, true);
	 } else {
	    // packet exists at our current index - determine what color to
	    // draw the packet in the animation
	    if (sender[i].acknowledged)
	       offGraphics.setColor(received_ack);
	    else
	       offGraphics.setColor(unack_color);
	    
	    offGraphics.fill3DRect(h_offset + (pack_width + 7) * i,v_offset, pack_width, pack_height, true);
	    if (sender[i].buffered)
	       offGraphics.setColor(Color.GRAY);
	    else
	       // drawing the destination packets
	       offGraphics.setColor(dest_color);
	    // if the packet has reached the destination than draw a filled
	    // rectangle in destination row
	    
	    // else draw a "clear" rectangle in destination row
	    if (sender[i].reached_dest)
	       offGraphics.fill3DRect(h_offset + (pack_width + 7) * i,v_offset + v_clearance, pack_width, pack_height, true);
	    
	    else {
	       offGraphics.setColor(Color.black);
	       offGraphics.draw3DRect(h_offset + (pack_width + 7) * i, v_offset + v_clearance, pack_width, pack_height, true);
	    }
	    // drawing the moving packets
	    if (sender[i].on_way) {
	       if (i == selected)
		  offGraphics.setColor(sel_color);
	       
	       else if (sender[i].packet_ack)
		  offGraphics.setColor(roam_pack_color);
	       else if (sender[i].received)
		  offGraphics.setColor(received_ack);
	       else
		  offGraphics.setColor(roam_ack_color);
	       
	       if (sender[i].packet_ack) {
		  offGraphics.fill3DRect(h_offset + (pack_width + 7) * i,v_offset + sender[i].packet_pos, pack_width,pack_height, true);
		  offGraphics.setColor(Color.black);
		  offGraphics.drawString("" + i, h_offset+ (pack_width + 7) * i, v_offset+ sender[i].packet_pos);
	       } else {
		  
		  offGraphics.fill3DRect(h_offset + (pack_width + 7) * i,v_offset + v_clearance - sender[i].packet_pos,pack_width, pack_height, true);
		  if (sender[i].out_of_order) {
		     offGraphics.setColor(Color.black);
		     offGraphics.drawString("" + sender[i].ackFor,h_offset + (pack_width + 7) * i, v_offset+ v_clearance - sender[i].packet_pos);
		  } else {
		     offGraphics.setColor(Color.black);
		     offGraphics.drawString("" + i, h_offset + (pack_width + 7) * i, v_offset + v_clearance - sender[i].packet_pos);
		  }
	       }
	    } // end if sender on way
	 } // end else
      } // for loop ends
      
      // drawing message boxes
      offGraphics.setColor(Color.black);
      int newvOffset = v_offset + v_clearance + pack_height;
      int newHOffset = h_offset;
      
      // draw values of variables on frame
      // offGraphics.drawString(newHOffset,newvOffset+25);
      offGraphics.drawString("(S) - Action at Sender                  (R) - Action at Receiver",newHOffset + 60, newvOffset + 90);
      
      // offGraphics.drawString(strCurrentValues,newHOffset,newvOffset+40);
      offGraphics.drawString("Packet", newHOffset + 15, newvOffset + 60);
      offGraphics.drawString("Ack Received", newHOffset + 225,newvOffset + 60);
      offGraphics.drawString("Ack", newHOffset + 170, newvOffset + 60);
      offGraphics.drawString("Received", newHOffset + 85, newvOffset + 60);
      offGraphics.drawString("Selected", newHOffset + 335, newvOffset + 60);
      
      offGraphics.drawString("base = " + base, h_offset + (pack_width + 7)* total_packet + 10, v_offset + 33);
      offGraphics.drawString("nextseqnum = " + nextseq, h_offset + (pack_width + 7) * total_packet + 10, v_offset + 50);
      
      offGraphics.setColor(Color.blue);
      offGraphics.drawString("Sender (Send Window Size = " + window_len + ")", h_offset + (pack_width + 7) * total_packet + 10, v_offset + 12);
      offGraphics.drawString("Receiver (Receiver Window Size = " +  receiver_window_len + ")", h_offset + (pack_width + 7) * total_packet + 10, v_offset + v_clearance + 12);
      offGraphics.setColor(Color.gray);
      offGraphics.draw3DRect(newHOffset - 10, newvOffset + 42, 400, 25, true);
      offGraphics.setColor(roam_pack_color);
      offGraphics.fill3DRect(newHOffset, newvOffset + 50, 10, 10, true);
      offGraphics.setColor(roam_ack_color);
      offGraphics.fill3DRect(newHOffset + 155, newvOffset + 50, 10, 10, true);
      offGraphics.setColor(received_ack);
      offGraphics.fill3DRect(newHOffset + 210, newvOffset + 50, 10, 10, true);
      offGraphics.setColor(dest_color);
      offGraphics.fill3DRect(newHOffset + 70, newvOffset + 50, 10, 10, true);
      offGraphics.setColor(sel_color);
      offGraphics.fill3DRect(newHOffset + 320, newvOffset + 50, 10, 10, true);
      
      g.drawImage(offImage, 0, 0, this);
   } // method paint ends
   
   /********************************************************************
    *                           Method onTheWay                        *
    * ****************************************************************** 
    * Purpose: checks to see if all of the packets in an array(in our case
    * the sender array) have been created and are being processed
    * Procedures/Functions Calling: run 
    * Local variables: i - temporary variable used for loop control
    **************************************************************************/
   public boolean onTheWay(GoBackNPacket pac[]) {
      
      for (int i = 0; i < pac.length; i++)
	 if (pac[i] == null)
	    return false;
	 else if (pac[i].on_way)
	    return true;
      
      return false;
   }
   
   /********************************************************************
    *                      Method check_upto_n                         *
    * ******************************************************************
    * Purpose: checks the sender array to see if all of the pacekts up to index
    * packno have reached thier destination 
    * Procedures/Functions Calling: run
    * Local variables: i - temporary variable used for loop control 
    * Global variables used: sender - array holding the packets and the 
    *    corresponding acks for the packets sent in the applet
    **************************************************************************/
   public boolean check_upto_n(int packno) {
      for (int i = 0; i < packno; i++)
	 if (!sender[i].reached_dest)
	    return false;
      return true;
   }
   
   /********************************************************************
    *                      Method reset_app                            *
    * ****************************************************************** 
    * Purpose: resets the applet to its initial state to allow for a second run
    * without reloading the webpage     
    * Local variables: i - temporary variable used for loop control
    * Global variables used: sender - array holding the packets and the 
    *    corresponding acks for the packets sent in the applet
    * base - what number our sending window is set to nextseq - the next
    *    sequence number that can be used for a packet selected - the packet
    *    currently selected 
    * fps - how fast shoud the animation run timerFlag -
    * gbnThread - used to process and display the animation timerThread - used
    *    to handle timeouts and retransmit the sending window
    **************************************************************************/
   
   public void reset_app() {
      
      for (int i = 0; i < total_packet; i++)
	 if (sender[i] != null)
	    sender[i] = null;
      
      base = 0;
      receiver_base = 0;
      nextseq = 0;
      selected = DESELECTED;
      fps = DEFAULT_FPS;
      timerFlag = false;
      timerSleep = false;
      gbnThread = null;
      timerThread = null;
      
      if (stop.getActionCommand() == "startanim") // in case of pause mode,
	 // enable all buttons
	 {
	    slow.setEnabled(true);
	    fast.setEnabled(true);
	 }
      
      send.setEnabled(true);
      kill.setEnabled(false);
      stop.setLabel("Pause");
      stop.setActionCommand("stopanim");
      output.append("---------------------------------------------------\n\n");
      output.append("-Simulation restarted. Press 'Send New' to start.\n");
      repaint();
   }
   
} // end class GBN

class GoBackNPacket {
   
   boolean on_way; // is packet in transit
   boolean reached_dest; // true if packet reached the destination
   boolean acknowledged; // used by drawing function -false will use packet
   // color -true will use ack color
   boolean packet_ack; // is this packet an ack? if false packet is assumed to
   // be a message
   boolean selected; // true if packet was selected by user false otherwise
   boolean received; // true if packet was received
   boolean out_of_order; // packet arrived out of order and an ack from the
   // base needs to be sent
   int packet_pos; // location of packet in diagram
   int ackFor; // carries the number of the packet the ack is for
   boolean buffered;
   
   GoBackNPacket() {
      on_way = false;
      selected = false;
      reached_dest = false;
      acknowledged = false;
      packet_ack = true;
      received = false;
      out_of_order = false;
      packet_pos = 0;
      ackFor = 0;
      buffered = false;
   }
   
   GoBackNPacket(boolean onway, int packetpos, int nextseq) {
      on_way = onway;
      selected = false;
      reached_dest = false;
      acknowledged = false;
      packet_ack = true;
      received = false;
      out_of_order = false;
      packet_pos = packetpos;
      ackFor = nextseq;
      buffered = false;
      
   }
}

