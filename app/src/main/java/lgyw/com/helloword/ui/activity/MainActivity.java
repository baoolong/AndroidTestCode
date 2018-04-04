package lgyw.com.helloword.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lgyw.com.helloword.R;

public class MainActivity extends AppCompatActivity {

    ServerSocket serverSocket=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startSocket(){
        try {
            Socket socket=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
