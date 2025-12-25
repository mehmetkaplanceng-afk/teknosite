package com.ornek.teknolojisitesi.sohbet;

public class SohbetMesaji {

    private String gonderen;
    private String icerik;
    private String zaman; // HH:mm formatında string tutacağız

    public SohbetMesaji() {
    }

    public SohbetMesaji(String gonderen, String icerik, String zaman) {
        this.gonderen = gonderen;
        this.icerik = icerik;
        this.zaman = zaman;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
}
