package com.filmoteca.app.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Genero extends AbstractBean implements Parcelable {

    private int codigo;
    private String nome;

    public Genero() {}

    public Genero(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Genero(Parcel parcel) {
        this.codigo = parcel.readInt();
        this.nome = parcel.readString();
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getCodigo());
        dest.writeString(getNome());
    }

    public static final Creator<Genero> CREATOR = new Creator<Genero>() {
        @Override
        public Genero createFromParcel(Parcel source) {
            return new Genero(source);
        }

        @Override
        public Genero[] newArray(int size) {
            return new Genero[size];
        }
    };

}
