package cba533_inc.cours3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView homeImage;
    String bingUrl = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeImage =  findViewById(R.id.imageView_imageDay);
        //downloadBingImageOfTheDay();
        downloadHomeImage("https://www.bing.com/th?id=OHR.TofinoCoast_FR-CA7305904283_1920x1080.jpg&rf=NorthMale_1920x1080.jpg&pid=hp");
        setListener();
    }

    public void setListener(){

        findViewById(R.id.btn_connexion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToConnectActivity();
            }
        });

      findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              downloadBingImageOfTheDay();
          }
      });
    }

    private void downloadBingImageOfTheDay(){

        BingImageLinkDownlader BingImageLinkDownloader = new BingImageLinkDownlader();
        try {
            BingImageLinkDownloader.execute(bingUrl);
        }catch(Exception e){

        }
    }

    public void downloadHomeImage(String imageUrl){
        ImageDownloader imageDownloader = new ImageDownloader();
        try {
            imageDownloader.execute(imageUrl);
        }catch(Exception e){

        }
    }

    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls){
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                return imageBitmap;
            }catch(MalformedURLException e){

            }catch(IOException e){

            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            homeImage.setImageBitmap(bitmap);
        }
    }

    public class BingImageLinkDownlader extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls){
            BufferedReader reader = null;
            HttpURLConnection connection = null;
            try{
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }
                if(buffer.length() == 0){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line=reader.readLine())!=null){
                    buffer.append(line + "\n");
                }
            }catch(MalformedURLException e){
                return null;
            }catch(IOException e){
                return null;
            }finally{
                if(connection != null){
                    connection.disconnect();
                }
                if(reader!=null){
                    try{
                        reader.close();
                    }catch(Exception e){

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response){
            if(response != null) {
                try{
                    JSONObject jsonRoot = null;
                    JSONArray jsonArrayImage = jsonRoot.getJSONArray("images");
                    String urlImage = jsonArrayImage.getJSONObject(0).getString("url");
                    downloadHomeImage("http://www.bing.com" + urlImage);
                }catch(Exception e){

                }

            }
        }

    }


    private void moveToConnectActivity(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
