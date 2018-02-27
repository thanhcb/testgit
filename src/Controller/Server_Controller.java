package Controller;

import Model.Datamodel;
import Model.SinhVien;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server_Controller {
    private String host = "localhost";
    private int port = 4444;
    private Socket client_socket;
    private ServerSocket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ObjectOutputStream writeFile;
    private ObjectInputStream readFile;
    private ArrayList<SinhVien> listsv = null;

    public ArrayList<SinhVien> loadFile(){
            File file = new File("Datamodel.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    return new ArrayList<>();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    try {
                        readFile = new ObjectInputStream(new FileInputStream(file));
                        return (ArrayList<SinhVien>) readFile.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (EOFException e){
                        System.out.println("file dang rong!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new ArrayList<>();
    }
    public void saveFile(ArrayList<SinhVien> sv){
        File file = new File("Datamodel.txt");
        try {
            writeFile = new ObjectOutputStream(new FileOutputStream(file));
            writeFile.writeObject(sv);
            writeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Deo thay file dau het!!!");
        }
    }
    public Server_Controller() {
        try {
            listsv = loadFile();
            ServerSocket server = new ServerSocket(port);
            System.out.println("listening.....");
            client_socket = server.accept();
            System.out.println("ket noi thanh cong!");
            input = new ObjectInputStream(client_socket.getInputStream());
            output = new ObjectOutputStream(client_socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("loi server!");
            e.printStackTrace();
        }
        while(true){
            Datamodel getClient = new Datamodel();
            try {
                getClient = (Datamodel) input.readObject();
                System.out.println(getClient.getStatus());
                switch (getClient.getStatus()){
                    case "Them":{
                        if(listsv.size()==0){
                            getClient.getSv().setMsv(1);
                        }
                        else {
                            getClient.getSv().setMsv(listsv.get(listsv.size()-1).getMsv() + 1);
                        }
                        listsv.add(getClient.getSv());
                        for(SinhVien x: listsv){
                            System.out.println(x.getTenSV());
                        }
                        saveFile(listsv);
                        // gui du lieu cho client
                        Datamodel datasend = new Datamodel();
                        datasend.setStatus("Da them thanh cong!");
                        output.writeObject(datasend);
                        output.flush();
                        break;
                    }
                    case"HienThi":{
                        output.writeObject(listsv);
                        output.reset();
                        break;
                    }
                    case"Xoa":{
                        SinhVien sv = getClient.getSv();
                        Iterator<SinhVien> iter = listsv.iterator();
                        while(iter.hasNext()){
                            SinhVien x = iter.next();
                            if(x.getMsv()==sv.getMsv()){
                                iter.remove();
                            }
                        }
                        saveFile(listsv);
                        //gui ve client
                        Datamodel send = new Datamodel();
                        send.setStatus("Xoa thanh cong!");
                        output.writeObject(send);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    client_socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
