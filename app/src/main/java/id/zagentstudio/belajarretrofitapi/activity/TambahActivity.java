package id.zagentstudio.belajarretrofitapi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.zagentstudio.belajarretrofitapi.R;
import id.zagentstudio.belajarretrofitapi.api.APIRequestData;
import id.zagentstudio.belajarretrofitapi.api.Retroserver;
import id.zagentstudio.belajarretrofitapi.model.ModelRespon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etAlamat, etUrl;
    private Button btnSimpan;
    private String nama, alamat, urlmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etUrl = findViewById(R.id.et_url);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                alamat = etAlamat.getText().toString();
                urlmap = etUrl.getText().toString();

                if (nama.trim().isEmpty()){
                    etNama.setError("Nama Harus Diisi");
                } else if (alamat.trim().isEmpty()){
                    etAlamat.setError("Alamat Harus Diisi");
                } else if (urlmap.trim().isEmpty()) {
                    etUrl.setError("Url Gmap Harus diisi");
                } else {
                    prosesSimpan();
                }
            }
        });
    }

    private void prosesSimpan(){
        APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
        Call<ModelRespon> proses = API.ardCreate(nama, alamat, urlmap);

        proses.enqueue(new Callback<ModelRespon>() {
            @Override
            public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : "+ kode + " Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onFailure(Call<ModelRespon> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Hubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}