package br.com.consultec.corretor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paulo on 26/05/2017.
 */

public class Teste implements Parcelable {
    private String teste1;
    private String teste2;
    private String teste3;

    public String getTeste1() {
        return teste1;
    }

    public void setTeste1(String teste1) {
        this.teste1 = teste1;
    }

    public String getTeste2() {
        return teste2;
    }

    public void setTeste2(String teste2) {
        this.teste2 = teste2;
    }

    public String getTeste3() {
        return teste3;
    }

    public void setTeste3(String teste3) {
        this.teste3 = teste3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teste1);
        dest.writeString(this.teste2);
        dest.writeString(this.teste3);
    }

    public Teste() {
    }

    protected Teste(Parcel in) {
        this.teste1 = in.readString();
        this.teste2 = in.readString();
        this.teste3 = in.readString();
    }

    public static final Parcelable.Creator<Teste> CREATOR = new Parcelable.Creator<Teste>() {
        @Override
        public Teste createFromParcel(Parcel source) {
            return new Teste(source);
        }

        @Override
        public Teste[] newArray(int size) {
            return new Teste[size];
        }
    };
}
