package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    List<NetworkInterface> networkInterfaces = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("XplicitVariable's IP Address Utility");
        FileInputStream inputStream = new FileInputStream("C:/Users/Aleka/Pictures/IP.png");
        Image applicationIcon = new Image(inputStream);
        primaryStage.getIcons().add(applicationIcon);

        GridPane gp = new GridPane();

        Label localAddress = new Label("Local Address");
        GridPane.setConstraints(localAddress, 0,0);

        TextField LocalAddress = new TextField();
        GridPane.setConstraints(LocalAddress, 1,0);

        Label publicAddress = new Label("Public Address");
        GridPane.setConstraints(publicAddress, 0, 1);

        TextField PublicAddress = new TextField();
        GridPane.setConstraints(PublicAddress, 1,1);

        ComboBox local




        int netintCount = 0;
        try {
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();


            while(ifaces.hasMoreElements()){

                NetworkInterface iface = ifaces.nextElement();

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                String name = iface.getName();

                while(addresses.hasMoreElements()){
                    InetAddress addr = addresses.nextElement();
                    if(addr instanceof Inet4Address && !addr.isLoopbackAddress()){
                        networkInterfaces.add(iface);
                        netintCount++;
                        System.out.println(name + addr);
                    }
                }
            }
        }catch(SocketException e){
            e.printStackTrace();
        }

        ObservableList<NetworkInterface> networkInterfacesObservable = FXCollections.observableList(networkInterfaces);
        ComboBox InterfaceBox = new ComboBox(networkInterfacesObservable);
        GridPane.setConstraints(InterfaceBox, 2,0);

        Button getAddresses = new Button("Get Addresses");
        GridPane.setConstraints(getAddresses, 2,2);
        getAddresses.setOnAction(event -> {

            //gets external/public ip address and places it within PublicAddress textfield for user to view
            try {
                URL url = new URL("http://bot.whatismyipaddress.com");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        url.openStream()));
                PublicAddress.setText(in.readLine());
            }catch(Exception e){
                PublicAddress.setText("Error. Not Connected.");
            }

            //gets internal address
            String num =  InterfaceBox.getValue().toString();
            String inter = networkInterfaces.get(0).toString();
            String displayName = networkInterfaces.get(0).getDisplayName();
            NetworkInterface Second;
            System.out.println(num +"\n" + inter + "\n" + displayName);


            int index = InterfaceBox.getSelectionModel().getSelectedIndex();
            NetworkInterface e = networkInterfaces.get(index);



                        Enumeration<InetAddress> addresses = e.getInetAddresses();

                        if(addresses.hasMoreElements()){
                            InetAddress addr = addresses.nextElement();
                            if(addr instanceof Inet4Address && !addr.isLoopbackAddress()){

                                StringBuilder sb = new StringBuilder(addr.toString());

                                sb.deleteCharAt(0);

                                String address = sb.toString();
                                LocalAddress.setText(address);
                            }
                        }

           // Enumeration<InetAddress> inetAddress =  .getInetAddresses();
            //System.out.println(getByName(InterfaceBox.getValue().toString()));


            for(int i = 0; i <= networkInterfaces.size()-1; i++){
                //if(networkInterfaces.get(i).ge)
                if(networkInterfaces.get(i).toString() == num){
                    Second = networkInterfaces.get(i);
                    System.out.println(Second.getName());
                }
            }

            //Enumeration<InetAddress> Address =;
           // LocalAddress.setText(Address.nextElement().toString());

        });

        gp.getChildren().addAll(InterfaceBox, PublicAddress, publicAddress, localAddress, LocalAddress, getAddresses);

        primaryStage.setScene(new Scene(gp, 650, 250));
        primaryStage.show();
    }
    //todo
    //getting null when trying to return NetworkInterface object with same name from arraylist

    public static void main(String[] args) {
        launch(args);
    }
}
