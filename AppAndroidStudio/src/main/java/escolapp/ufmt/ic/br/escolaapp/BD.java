package escolapp.ufmt.ic.br.escolaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BD extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "escolaApp";

    public static final String tabelaAluno = "aluno";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String CPF = "cpf";
    public static final String IDTURMAATUAL = "_idturma_atual";

    public static final String tabelaPresenca = "presenca";
    public static final String IDALUNO = "_idaluno";
    public static final String DATA = "data";
    public static final String IDTURMA = "_idturma";
    public static final String PRESENCA = "presenca";

    public static final String tabelaProfessor = "professor";
    public static final String SENHA = "senha";

    public static final String tabelaTurma = "turma";

    public static final String tabelaProfessorTurma = "professor_turma";
    public static final String IDPROFESSOR = "_idprofessor";

    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+tabelaProfessor+" ("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + CPF + " text, "
                + SENHA + " text"
                +")";
        db.execSQL(sql);

        sql = "CREATE TABLE "+tabelaTurma+" ("
                + ID + " integer primary key autoincrement, "
                + NOME + " text"
                +")";
        db.execSQL(sql);

        sql = "CREATE TABLE "+tabelaAluno+" ("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + CPF + " text, "
                + IDTURMAATUAL + " integer, "
                + "foreign key("+ IDTURMAATUAL +") REFERENCES "+ tabelaTurma +"("+ ID +")"
                +")";
        db.execSQL(sql);

        sql = "CREATE TABLE "+tabelaPresenca+" ("
                + ID + " integer primary key autoincrement, "
                + IDALUNO + " integer, "
                + DATA + " text, "
                + IDTURMA + " integer, "
                + PRESENCA + " integer, "
                + "foreign key("+ IDALUNO +") REFERENCES "+ tabelaAluno +"("+ ID +"),"
                + "foreign key("+ IDTURMA +") REFERENCES "+ tabelaTurma +"("+ ID +")"
                +")";
        db.execSQL(sql);

        sql = "CREATE TABLE "+tabelaProfessorTurma+" ("
                + IDTURMA + " integer primary key autoincrement, "
                + IDPROFESSOR + " integer, "
                + "foreign key("+ IDTURMA +") REFERENCES "+ tabelaTurma +"("+ ID +"),"
                + "foreign key("+ IDPROFESSOR +") REFERENCES "+ tabelaProfessor +"("+ ID +")"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabelaAluno);
        db.execSQL("DROP TABLE IF EXISTS " + tabelaPresenca);
        db.execSQL("DROP TABLE IF EXISTS " + tabelaProfessor);
        db.execSQL("DROP TABLE IF EXISTS " + tabelaTurma);
        db.execSQL("DROP TABLE IF EXISTS " + tabelaProfessorTurma);
        onCreate(db);
    }
}
