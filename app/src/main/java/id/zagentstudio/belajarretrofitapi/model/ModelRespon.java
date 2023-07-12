package id.zagentstudio.belajarretrofitapi.model;

import java.util.List;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public class ModelRespon {
    private String kode, pesan;
    private List<ModelWisata> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelWisata> getData() {
        return data;
    }
}