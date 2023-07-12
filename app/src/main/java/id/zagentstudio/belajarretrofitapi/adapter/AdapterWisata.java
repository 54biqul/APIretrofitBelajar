package id.zagentstudio.belajarretrofitapi.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.zagentstudio.belajarretrofitapi.R;
import id.zagentstudio.belajarretrofitapi.activity.HomeActivity;
import id.zagentstudio.belajarretrofitapi.activity.TambahActivity;
import id.zagentstudio.belajarretrofitapi.activity.UbahActivity;
import id.zagentstudio.belajarretrofitapi.api.APIRequestData;
import id.zagentstudio.belajarretrofitapi.api.Retroserver;
import id.zagentstudio.belajarretrofitapi.model.ModelRespon;
import id.zagentstudio.belajarretrofitapi.model.ModelWisata;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class AdapterWisata extends RecyclerView.Adapter<AdapterWisata.VHWisata>{
    private Context ctx;
    private List<ModelWisata> listWisata;

    public AdapterWisata(Context ctx, List<ModelWisata> listWisata) {
        this.ctx = ctx;
        this.listWisata = listWisata;
    }

    @NonNull
    @Override
    public VHWisata onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        return new VHWisata(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHWisata holder, int position) {
        ModelWisata MW = listWisata.get(position);

        holder.tvId.setText(MW.getId());
        holder.tvNama.setText(MW.getNama());
        holder.tvAlamat.setText(MW.getAlamat());
        holder.tvUrlMap.setText(MW.getUrlmap());

    }

    @Override
    public int getItemCount() {
        return listWisata.size();
    }

    public class VHWisata extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvAlamat, tvUrlMap;

        public VHWisata(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvUrlMap = itemView.findViewById(R.id.tv_url);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Memilih "+tvNama.getText().toString()+" Silahkan Pilih yang Dikehendaki");

                    pesan.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                            kirim.putExtra("xUrlMap", tvUrlMap.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvId.getText().toString());
                        }
                    });
                    pesan.show();
                    return false;
                }
            });
        }

        void prosesHapus(String id){
            APIRequestData API = Retroserver.konekRetrofit().create(APIRequestData.class);
            Call<ModelRespon> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelRespon>() {
                @Override
                public void onResponse(Call<ModelRespon> call, Response<ModelRespon> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : "+ kode + " Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                    ((HomeActivity) ctx).retriveWisata();
                }

                @Override
                public void onFailure(Call<ModelRespon> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Hubungi server" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}