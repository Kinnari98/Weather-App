package com.example.androidharkkatyo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class SecondPage extends AppCompatActivity {



    Context context;
    private TextView name, surname;
    EditText et;
    TextView tvWindSpeed;
    TextView tvWindDirection;
    TextView tvHumidity;
    TextView tvcountry;
    TextView tvcity;
    TextView tvTemp;
    TextView tvFeels_Like;
    TextView tvMinTemp;
    TextView tvMaxTemp;
    TextView tvWeatherDesc;
    ImageView searchIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);


        name = findViewById(R.id.text_name);
        surname = findViewById(R.id.text_surname);
        et = findViewById(R.id.et);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvWindDirection = findViewById(R.id.tvWindDirection);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvcity = findViewById(R.id.tvCity);
        tvcountry = findViewById(R.id.tvCountry);
        tvTemp = findViewById(R.id.tvTemp);
        tvFeels_Like = findViewById(R.id.tvFeels_Like);
        tvMinTemp = findViewById(R.id.tvMinTemp);
        tvMaxTemp = findViewById(R.id.tvMaxTemp);
        tvWeatherDesc = findViewById(R.id.tvWeatherDesc);
        searchIcon = findViewById(R.id.searchIcon);

        String username = getIntent().getStringExtra("keyname");
        String usersurname = getIntent().getStringExtra("keysurname");
        name.setText(username);
        surname.setText(usersurname);



            // Jos internet yhteyttä ei ole, seuraa siitä varoitusviesti
        if (!isConnected()){
            Toast.makeText(SecondPage.this,"No internet Connection!", Toast.LENGTH_LONG).show();
        }
        else {
            // Jos yhteys löytyy
            Toast.makeText(SecondPage.this,"Internet connection established", Toast.LENGTH_LONG).show();
        }





        // Haetaan JSON-data openweathermap-apista ja samalla parsitaan se
        searchIcon.setOnClickListener(view -> {
            String apiKey="40bb658a709ebb7b1c210655e7f5cfe6";
            String city=et.getText().toString();
            String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=40bb658a709ebb7b1c210655e7f5cfe6&units=metric";
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject object = response.getJSONObject("sys");
                        String country = object.getString("country");
                        tvcountry.setText(country);

                        JSONObject object1= response.getJSONObject("main");
                        String temperature=object1.getString("temp");
                        tvTemp.setText("Temperature: " + temperature + " C");

                        JSONObject object2 = response.getJSONObject("main");
                        String feelsLike = object2.getString("feels_like");
                        tvFeels_Like.setText("Feels like: " + feelsLike + " C");

                        JSONObject object3 = response.getJSONObject("main");
                        String maxtemp = object3.getString("temp_max");
                        tvMaxTemp.setText("Max temp: " + maxtemp + " C");

                        JSONObject object4 = response.getJSONObject("main");
                        String mintemp = object4.getString("temp_min");
                        tvMinTemp.setText("Min temp: " + mintemp + " C");

                        JSONObject object5 = response.getJSONObject("wind");
                        String windspeed = object5.getString("speed");
                        tvWindSpeed.setText("Wind speed: " + windspeed + " m/s");

                        JSONObject object6 = response.getJSONObject("wind");
                        String winddeg = object6.getString("deg");
                        tvWindDirection.setText("Wind degree: " + winddeg + "");

                        JSONObject object7 = response.getJSONObject("main");
                        String humidity = object7.getString("humidity");
                        tvHumidity.setText("Humidity: " + humidity + "%");

                        String weatherDescription = response.getJSONArray("weather").getJSONObject(0).getString("description");
                        tvWeatherDesc.setText(weatherDescription);

                        String cityname = response.getString("name");
                        tvcity.setText(cityname);



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Tarkistaa käyttäjäsyötteen virheet
                    Toast.makeText(SecondPage.this, "Check your input", Toast.LENGTH_SHORT).show();

                }
            });
            queue.add(request);
        });
    }




    // Tarkistaa internet-yhteyden toimivuuden
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }






}