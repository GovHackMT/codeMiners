package escolapp.ufmt.ic.br.escolaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BDController {
    private SQLiteDatabase db;
    private BD banco;

    public BDController(Context context){
        banco = new BD(context);
    }

    public void insereDadoAluno(String nome, String cpf, int idTurmaAtual){
        ContentValues valores;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(BD.NOME, nome);
        valores.put(BD.CPF, cpf);
        valores.put(BD.IDTURMAATUAL, idTurmaAtual);

        db.insert(BD.tabelaAluno, null, valores);

        db.close();
    }

    public void insereDadoProfessor(int id, String nome, String cpf, String senha){
        ContentValues valores;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(BD.ID, id);
        valores.put(BD.NOME, nome);
        valores.put(BD.CPF, cpf);
        valores.put(BD.SENHA, senha);

        db.insert(BD.tabelaProfessor, null, valores);

        db.close();
    }

    public void insereDadoTurma(String nome){
        ContentValues valores;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(BD.NOME, nome);

        db.insert(BD.tabelaTurma, null, valores);

        db.close();
    }

    public void insereDadoProfessorTurma(int idProfessor, int idTurma){
        ContentValues valores;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(BD.IDPROFESSOR, idProfessor);
        valores.put(BD.IDTURMA, idTurma);

        db.insert(BD.tabelaProfessorTurma, null, valores);

        db.close();
    }

    public void insereDadoPresenca(int idAluno, String data, int idTurma, boolean presenca){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(BD.IDALUNO, idAluno);
        valores.put(BD.DATA, data);
        valores.put(BD.IDTURMA, idTurma);
        valores.put(BD.PRESENCA, presenca);

        db.insert(BD.tabelaPresenca, null, valores);

        db.close();
    }

    public Cursor carregaDadosAluno(int idTurma){
        Cursor cursor;
        String[] campos = {BD.ID,BD.NOME,BD.CPF,BD.IDTURMAATUAL};
        String where = BD.IDTURMAATUAL + " = " + idTurma;

        db = banco.getReadableDatabase();

        cursor = db.query(BD.tabelaAluno, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor carregaTurmasProfessor(int idProfessor){
        Cursor cursor;
        String[] campos = {BD.IDTURMA};
        String where = BD.IDPROFESSOR + " = " + idProfessor;

        db = banco.getReadableDatabase();

        cursor = db.query(BD.tabelaProfessorTurma, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public Cursor carregaTurma(int idTurma){
        Cursor cursor;
        String[] campos = {BD.ID,BD.NOME};
        String where = BD.ID + " = " + idTurma;

        db = banco.getReadableDatabase();

        cursor = db.query(BD.tabelaTurma, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }


    public Cursor carregaProfessor(String cpf, String senha){
        Cursor cursor;
        String[] campos = {BD.ID,BD.NOME,BD.CPF,BD.SENHA};
        String where = BD.CPF + " LIKE " + cpf + " AND " + BD.SENHA + " LIKE \"" + senha +"\"";

        db = banco.getReadableDatabase();

        cursor = db.query(BD.tabelaProfessor, campos, where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public void addPresenca(ArrayList<Aluno> alunos){
        ContentValues valores;

        db = banco.getWritableDatabase();

        for (int i = 0; i < alunos.size(); i++) {
            Cursor res = verificarIdDataPresenca(alunos.get(i).id,alunos.get(i).data);
            if(res.getCount() == 0) {
                valores = new ContentValues();

                valores.put(BD.IDALUNO, alunos.get(i).id);
                valores.put(BD.DATA, alunos.get(i).data);
                valores.put(BD.IDTURMA, alunos.get(i).turmaAtual);
                valores.put(BD.PRESENCA, alunos.get(i).presenca);

                db.insert(BD.tabelaPresenca, null, valores);
            }else{
                String where;

                where = BD.ID + "=" + res.getInt(0);

                valores = new ContentValues();

                valores.put(BD.PRESENCA, alunos.get(i).presenca);

                db.update(BD.tabelaPresenca,valores,where,null);
            }
        }

        db.close();
    }

    public Cursor verificarIdDataPresenca(int idAluno, String data){
        Cursor cursor;
        String[] campos =  {BD.ID,BD.PRESENCA};

        String where = BD.IDALUNO + " = " + idAluno + " AND " + BD.DATA + " LIKE \"" + data +"\"";

        cursor = db.query(BD.tabelaPresenca,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor verificarIdDataPresenca2(int idAluno, String data){
        Cursor cursor;
        String[] campos =  {BD.ID,BD.PRESENCA};

        db = banco.getReadableDatabase();

        String where = BD.IDALUNO + " = " + idAluno + " AND " + BD.DATA + " LIKE \"" + data +"\"";

        cursor = db.query(BD.tabelaPresenca,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }
}
