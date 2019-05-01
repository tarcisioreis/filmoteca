package com.filmoteca.app.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.filmoteca.app.dao.GeneroBD;

public class Filme extends AbstractBean implements Parcelable {

    private int codigo;
    private String titulo;
    private String diretor;
    private int anoLancamnento;
    private int idGenero;

    public Filme() {}

    public Filme(int codigo,
                 String titulo,
                 String diretor,
                 int anoLancamnento,
                 int idGenero) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamnento = anoLancamnento;
        this.idGenero = idGenero;
    }

    public Filme(Parcel parcel) {
        this.codigo = parcel.readInt();
        this.titulo = parcel.readString();
        this.diretor = parcel.readString();
        this.anoLancamnento = parcel.readInt();
        this.idGenero = parcel.readInt();
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public int getAnoLancamnento() {
        return anoLancamnento;
    }
    public void setAnoLancamnento(int anoLancamnento) {
        this.anoLancamnento = anoLancamnento;
    }

    public int getIdGenero() {
        return idGenero;
    }
    public void setGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCodigo());
        dest.writeString(getTitulo());
        dest.writeString(getDiretor());
        dest.writeInt(getAnoLancamnento());
        dest.writeInt(getIdGenero());
    }

    public static final Creator<Filme> CREATOR = new Creator<Filme>() {
        @Override
        public Filme createFromParcel(Parcel source) {
            return new Filme(source);
        }

        @Override
        public Filme[] newArray(int size) {
            return new Filme[size];
        }
    };

}
