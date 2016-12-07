import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Hao on 12/6/2016.
 */
public class ServerGUI extends JFrame
{

    private final JTextArea jtaServerLog = new JTextArea(); //log area
    private final int defaultServerPort = 1500; //The TCP/IP port for server
    private ServerManager server;
    private JButton jbStartStop;

    ServerGUI()
    {
        super("ClueLess Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        JScrollPane serverLog = new JScrollPane(jtaServerLog);

        JSplitPane splitMessages = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(splitMessages);
        splitMessages.setDividerLocation(300);
        splitMessages.setTopComponent(serverLog);


        /* Make buttons */
        jbStartStop = new JButton("Start");
        JLabel jlab = new JLabel("Port: ");
        JTextField jtfPort = new JTextField(5);
        jtfPort.setText(Integer.toString(defaultServerPort));
        /* Create new JPanel  */
        JPanel jp = new JPanel();
        jp.add(jbStartStop);
        jp.add(jlab);
        jp.add(jtfPort);

        add(jp, BorderLayout.NORTH);

        setVisible(true);
        setLocationRelativeTo(null);
        validate(); // to display JPanel

        jbStartStop.addActionListener((ActionEvent e) -> {

            if ("Stop".equals(jbStartStop.getText())) {
                jbStartStop.setText("Start");
                try {
                    server.stop();
                } catch (IOException ex) {
                    Logger.getLogger(SvrMgrGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                jbStartStop.setText("Stop");
                server = new ServerManager(Integer.parseInt(jtfPort.getText()), this);
                new RunServer().start();
                jbStartStop.setText("Stop");

            } //end if else start stop

        }); //actionlistener for StartStop Server

    } //end constructor

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SvrMgrGUI svrMgrGUI = new SvrMgrGUI();

    } //end method main

    public void writeLog(String str)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        jtaServerLog.append(strDate + "  " + str + "\n");

    } //end method writeLog


    /**
     * Run SvrMgr on a separate thread
     *
     * @author PD
     * @version 1.0
     */
    class RunServer extends Thread
    {
        @Override
        public void run()
        {
            server.start();
            jbStartStop.setText("Start");
            server = null;
            writeLog("Server Stopped");
        } //end method run
    } //end  class RunServer


}
