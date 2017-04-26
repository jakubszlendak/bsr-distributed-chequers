package bsr.project.checkers.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import bsr.project.checkers.logger.Logs;
import sun.plugin.dom.exception.InvalidStateException;

public class ClientConnectionThread extends Thread {
    
    private Socket clientSocket;
    private volatile boolean active = true;
    BufferedReader in = null;
    PrintWriter out = null;
    
    private ClientInfo clientInfo;
    
    public ClientConnectionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientInfo = new ClientInfo(this);
    }
    
    @Override
    public void run() {
        
        // TODO register for event closeAllConnections
        
        // TODO new connection event
        
        System.out.println("Accepted Client Address - " + clientSocket.getInetAddress().getHostName());
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            
            while (active) {
                String received = in.readLine();
                
                if (received == null) {
                    
                    //end of the stream has been reached
                    // TODO send event disconnection;
                    
                    close();
                    break;
                    
                } else {
                    
                    System.out.println("Client Says :" + received);
                    //TODO send event: received bytes from client, send to parser
                    
                }
                
            }
            
        } catch (Exception e) {
            Logs.error(e);
        }
        
        // TODO unregister from all events
    }
    
    public synchronized boolean isActive() {
        return active;
    }
    
    public synchronized void close() {
        if (active) {
            try {
                in.close();
                out.close();
                clientSocket.close();
                active = false;
                System.out.println("Connection closed");
            } catch (IOException e) {
                Logs.error(e);
            }
        }
    }
    
    public synchronized void sendLine(String line) {
        if (!active) throw new InvalidStateException("Client connection is not active");
        if (out == null) throw new InvalidStateException("No output stream");
        out.println(line);
        out.flush();
    }
    
}