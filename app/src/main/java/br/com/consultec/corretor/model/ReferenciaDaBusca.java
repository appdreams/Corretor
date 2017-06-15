package br.com.consultec.corretor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paulo on 26/05/2017.
 */

public class ReferenciaDaBusca implements Parcelable {
    private String idCorretor;
    private String idProcessoSeletivo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idCorretor);
        dest.writeString(this.idProcessoSeletivo);
    }

    public ReferenciaDaBusca() {
    }

    protected ReferenciaDaBusca(Parcel in) {
        this.idCorretor = in.readString();
        this.idProcessoSeletivo = in.readString();
    }

    public static final Parcelable.Creator<ReferenciaDaBusca> CREATOR = new Parcelable.Creator<ReferenciaDaBusca>() {
        @Override
        public ReferenciaDaBusca createFromParcel(Parcel source) {
            return new ReferenciaDaBusca(source);
        }

        @Override
        public ReferenciaDaBusca[] newArray(int size) {
            return new ReferenciaDaBusca[size];
        }
    };

    public String getIdCorretor() {
        return idCorretor;
    }

    public void setIdCorretor(String idCorretor) {
        this.idCorretor = idCorretor;
    }

    public String getIdProcessoSeletivo() {
        return idProcessoSeletivo;
    }

    public void setIdProcessoSeletivo(String idProcessoSeletivo) {
        this.idProcessoSeletivo = idProcessoSeletivo;
    }
}
