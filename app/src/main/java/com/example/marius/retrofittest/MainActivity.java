package com.example.marius.retrofittest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.marius.retrofittest.model.RetroPhoto;
import com.example.marius.retrofittest.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPhotos;
    private PhotosAdapter adapter;
    private ProgressDialog progressDialog;
    private String global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        global = "dsadasda";
        String locala = "";

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        service.getAllPhotos().enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDialog.dismiss();
                adapter.setData(response.body());

            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "a fost o eroare", Toast.LENGTH_SHORT).show();


            }
        });
    }


    private void setupViews() {
        rvPhotos = findViewById(R.id.rvPhotos);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvPhotos.setLayoutManager(manager);
        adapter = new PhotosAdapter();
        rvPhotos.setAdapter(adapter);

    }
}
