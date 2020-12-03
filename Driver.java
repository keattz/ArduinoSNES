import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

public class Driver implements SerialPortEventListener {
  SerialPort serialPort;
  /** The port we're normally going to use. */
  private static final String PORT_NAMES[] = {
    "/dev/tty.usbserial-A9007UX1", // Mac OS X
    "/dev/ttyUSB0", // Linux
    "COM3", // Windows
  };
  
  private Robot robot;
  
  /** Buffered input stream from the port */
  private InputStream input;
  /** The output stream to the port */
  private OutputStream output;
  /** Milliseconds to block while waiting for port open */
  private static final int TIME_OUT = 2000;
  /** Default bits per second for COM port. */
  private static final int DATA_RATE = 115200;
  
  /*
   * These booleans will ensure that our buttons are only pressed down once. Just like
   * in our Arduino sketch false = not pressed.
   */
  public Boolean a = false;
  public Boolean b = false;
  public Boolean x = false;
  public Boolean y = false;
  public Boolean l = false;
  public Boolean r = false;
  public Boolean up = false;
  public Boolean down = false;
  public Boolean left = false;
  public Boolean right = false;
  public Boolean start = false;
  public Boolean select = false;
  public Boolean a2 = false;
  public Boolean b2 = false;
  public Boolean x2 = false;
  public Boolean y2 = false;
  public Boolean l2 = false;
  public Boolean r2 = false;
  public Boolean up2 = false;
  public Boolean down2 = false;
  public Boolean left2 = false;
  public Boolean right2 = false;
  public Boolean start2 = false;
  public Boolean select2 = false;
  
  public void initialize() throws AWTException {
    
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
    robot = new Robot();
    
    // iterate through, looking for the port
    while (portEnum.hasMoreElements()) {
      CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
      for (String portName : PORT_NAMES) {
        if (currPortId.getName().equals(portName)) {
          portId = currPortId;
          break;
        }
      }
    }
    
    if (portId == null) {
      System.out.println("Could not find COM port.");
      return;
    }
    
    try {
      // open serial port, and use class name for the appName.
      serialPort = (SerialPort) portId.open(this.getClass().getName(),
                                            TIME_OUT);
      
      // set port parameters
      serialPort.setSerialPortParams(DATA_RATE,
                                     SerialPort.DATABITS_8,
                                     SerialPort.STOPBITS_1,
                                     SerialPort.PARITY_NONE);
      
      // open the streams
      input = serialPort.getInputStream();
      output = serialPort.getOutputStream();
      
      // add event listeners
      serialPort.addEventListener(this);
      serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
      System.err.println(e.toString());
    }
  }
  
  /**
   * This should be called when you stop using the port.
   * This will prevent port locking on platforms like Linux.
   */
  public synchronized void close() {
    if (serialPort != null) {
      serialPort.removeEventListener();
      serialPort.close();
    }
  }
  
  /**
   * Handle an event on the serial port. Read the data and print it.
   */
  public synchronized void serialEvent(SerialPortEvent oEvent) {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
      try {
        int available = input.available();
        byte chunk[] = new byte[available];
        input.read(chunk, 0, available);
        
        sendCommand(new String(chunk));
        
      } catch (Exception e) {
        System.err.println(e.toString());
      }
    }
  }
  
  public void sendCommand(String command){
    if(command.equals(""))
      return;
    if(command == null)
      return;
    
    System.out.println(command);
    
    //Switches for the A Button
    if(command.contains("A")){
      if(!a){
        robot.keyPress(KeyEvent.VK_A);
        a = true;
      }
    }
    if(command.contains("0")){
      a = false;
      robot.keyRelease(KeyEvent.VK_A);
    }
    
    //Switches for the B Button
    if(command.contains("B")){
      if(!b){
        robot.keyPress(KeyEvent.VK_B);
        b = true;
      }
    }
    if(command.contains("1")){
      b = false;
      robot.keyRelease(KeyEvent.VK_B);
    }
    
    //Switches for the X Button
    if(command.contains("X")){
      if(!x){
        robot.keyPress(KeyEvent.VK_X);
        x = true;
      }
    }
    if(command.contains("2")){
      x = false;
      robot.keyRelease(KeyEvent.VK_X);
    }
    
    //Switches for the Y Button
    if(command.contains("Y")){
      if(!y){
        robot.keyPress(KeyEvent.VK_Y);
        y = true;
      }
    }
    if(command.contains("3")){
      y = false;
      robot.keyRelease(KeyEvent.VK_Y);
    }
    
    //Switches for the L Button
    if(command.contains("L")){
      if(!l){
        robot.keyPress(KeyEvent.VK_L);
        l = true;
      }
    }
    if(command.contains("4")){
      l = false;
      robot.keyRelease(KeyEvent.VK_L);
    }
    
    //Switches for the R Button
    if(command.contains("R")){
      if(!r){
        robot.keyPress(KeyEvent.VK_R);
        r = true;
      }
    }
    if(command.contains("5")){
      r = false;
      robot.keyRelease(KeyEvent.VK_R);
    }
    
    //Switches for the Select button
    if(command.contains("E")){
      if(!select){
        robot.keyPress(KeyEvent.VK_S);
        select = true;
      }
    }
    if(command.contains("6")){
      select = false;
      robot.keyRelease(KeyEvent.VK_S);
    }
    
    //Switches for the Start button
    if(command.contains("S")){
      if(!start){
        robot.keyPress(KeyEvent.VK_ENTER);
        start = true;
      }
    }
    if(command.contains("7")){
      start = false;
      robot.keyRelease(KeyEvent.VK_ENTER);
    }
    
    // Switches for the UP button
    if(command.contains("U")){
      if(!up){
        up = true;
        robot.keyPress(KeyEvent.VK_UP);
      }
    }
    if(command.contains("8")){
      up = false;
      robot.keyRelease(KeyEvent.VK_UP);
    }
    
    //Switches for the Down button
    if(command.contains("D")){
      if(!down){
        robot.keyPress(KeyEvent.VK_DOWN);
        down = true;
      }
    }
    if(command.contains("9")){
      down = false;
      robot.keyRelease(KeyEvent.VK_DOWN);
    }
    
    //Switches for the Left button
    if(command.contains("F")){
      if(!left){
        robot.keyPress(KeyEvent.VK_LEFT);
        left = true;
      }
    }
    if(command.contains("-")){
      left = false;
      robot.keyRelease(KeyEvent.VK_LEFT);
    }
    
    //Switches for the Right button
    if(command.contains("I")){
      if(!right){
        robot.keyPress(KeyEvent.VK_RIGHT);
        right = true;
      }
    }
    if(command.contains("=")){
      right = false;
      robot.keyRelease(KeyEvent.VK_RIGHT);
    }
	
	//Switches for the A2 Button
    if(command.contains("C")){
      if(!a2){
        robot.keyPress(KeyEvent.VK_C);
        a2 = true;
      }
    }
    if(command.contains(";")){
      a2 = false;
      robot.keyRelease(KeyEvent.VK_C);
    }
    
    //Switches for the B2 Button
    if(command.contains("G")){
      if(!b2){
        robot.keyPress(KeyEvent.VK_G);
        b2 = true;
      }
    }
    if(command.contains("'")){
      b2 = false;
      robot.keyRelease(KeyEvent.VK_G);
    }
    
    //Switches for the X2 Button
    if(command.contains("H")){
      if(!x2){
        robot.keyPress(KeyEvent.VK_H);
        x2 = true;
      }
    }
    if(command.contains("[")){
      x2 = false;
      robot.keyRelease(KeyEvent.VK_H);
    }
    
    //Switches for the Y2 Button
    if(command.contains("J")){
      if(!y2){
        robot.keyPress(KeyEvent.VK_J);
        y2 = true;
      }
    }
    if(command.contains("]")){
      y2 = false;
      robot.keyRelease(KeyEvent.VK_J);
    }
    
    //Switches for the L2 Button
    if(command.contains("K")){
      if(!l2){
        robot.keyPress(KeyEvent.VK_K);
        l2 = true;
      }
    }
    if(command.contains(",")){
      l2 = false;
      robot.keyRelease(KeyEvent.VK_K);
    }
    
    //Switches for the R2 Button
    if(command.contains("M")){
      if(!r2){
        robot.keyPress(KeyEvent.VK_M);
        r2 = true;
      }
    }
    if(command.contains(".")){
      r2 = false;
      robot.keyRelease(KeyEvent.VK_M);
    }
    
    //Switches for the Select2 button
    if(command.contains("N")){
      if(!select2){
        robot.keyPress(KeyEvent.VK_N);
        select2 = true;
      }
    }
    if(command.contains("/")){
      select2 = false;
      robot.keyRelease(KeyEvent.VK_N);
    }
    
    //Switches for the Start2 button
    if(command.contains("O")){
      if(!start2){
        robot.keyPress(KeyEvent.VK_O);
        start2 = true;
      }
    }
    if(command.contains("|")){
      start2 = false;
      robot.keyRelease(KeyEvent.VK_O);
    }
    
    // Switches for the UP2 button
    if(command.contains("P")){
      if(!up2){
        up2 = true;
        robot.keyPress(KeyEvent.VK_P);
      }
    }
    if(command.contains("`")){
      up2 = false;
      robot.keyRelease(KeyEvent.VK_P);
    }
    
    //Switches for the Down2 button
    if(command.contains("Q")){
      if(!down2){
        robot.keyPress(KeyEvent.VK_Q);
        down2 = true;
      }
    }
    if(command.contains("!")){
      down2 = false;
      robot.keyRelease(KeyEvent.VK_Q);
    }
    
    //Switches for the Left2 button
    if(command.contains("T")){
      if(!left2){
        robot.keyPress(KeyEvent.VK_T);
        left2 = true;
      }
    }
    if(command.contains("@")){
      left2 = false;
      robot.keyRelease(KeyEvent.VK_T);
    }
    
    //Switches for the Right2 button
    if(command.contains("V")){
      if(!right2){
        robot.keyPress(KeyEvent.VK_V);
        right2 = true;
      }
    }
    if(command.contains("#")){
      right2 = false;
      robot.keyRelease(KeyEvent.VK_V);
    }
  }
  
  public static void main(String[] args) throws Exception {
    Driver main = new Driver();
    main.initialize();
    System.out.println("Started");
  }
}