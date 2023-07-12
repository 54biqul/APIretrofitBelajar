package id.zagentstudio.belajarretrofitapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.zagentstudio.belajarretrofitapi.R;
import id.zagentstudio.belajarretrofitapi.adapter.AdapterWisata;
import id.zagentstudio.belajarretrofitapi.api.APIRequestData;
import id.zagentstudio.belajarretrofitapi.api.Retroserver;
import id.zagentstudio.belajarretrofitapi.model.ModelRespon;
import id.zagentstudio.belajarretrofitapi.model.ModelWisata;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class HomeActivity extends AppCompatActivity {
    private RecyclerView rvWisata;
    private ProgressBar pbWisata;
    private FloatingActionButton fbTambah;
    private RecyclerView.Adapter adWisata;
    private RecyclerView.LayoutManager lmWisata;
    private List<ModelWisata> listWisata = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvWisata = findViewById(R.id.rv_data);
        pbWisata = findViewById(R.id.pb_proses);
        fbTambah = findViewById(R.id.fab_tambah);

        lmWisata = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWisata.setLayoutManager(lmWisata);

        fbTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TambahActivity.class));
            }
        });
    }

    public void retriveWisata(){
        pbWisata.setVisibility(View.VISIBLE);
        APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listWisata = response.body().getData();

                adWisata = new AdapterWisata(HomeActivity.this, listWisata);
                rvWisata.setAdapter(adWisata);
                adWisata.notifyDataSetChanged();

                pbWisata.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Eror : Gagal Terhubung dengan server", Toast.LENGTH_SHORT).show();
                pbWisata.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveWisata();
    }
}