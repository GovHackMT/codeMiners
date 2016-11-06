package escolapp.ufmt.ic.br.escolaapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    BDController crud;
    AsyncHttpClient client;
    RequestParams params;
    List<Professor> prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cpf = (EditText) findViewById(R.id.etCPF);
        final EditText senha = (EditText) findViewById(R.id.etSenha);

        crud = new BDController(getBaseContext());

        Button botao = (Button)findViewById(R.id.btEntrar);

        client = new AsyncHttpClient();
        params = new RequestParams();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sCPF = cpf.getText().toString();
                final String sSenha = senha.getText().toString();

                params.add("cpf",sCPF);
                params.add("senha",sSenha);
                client.get("http://10.0.2.2:8080/WebServiceEscolaApp/ProfessorServlet", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Professor>>(){}.getType();
                        // In this test code i just shove the JSON here as string.
                        prof = gson.fromJson(response.toString(), listType);

                        if(prof.get(0).id!=-1) {
                            Cursor c = crud.carregaProfessor(sCPF,sSenha);

                            if(c.getCount()==0) {
                                crud.insereDadoProfessor(prof.get(0).id,prof.get(0).nome,sCPF,sSenha);

                                ///////////////////////////

                                crud.insereDadoTurma("TURMA1");
                                crud.insereDadoTurma("TURMA2");
                                crud.insereDadoTurma("TURMA3");


                                crud.insereDadoAluno("NOME1","CPF1",1);

                                crud.insereDadoAluno("NOME2","CPF2",1);

                                crud.insereDadoAluno("NOME3","CPF3",2);

                                crud.insereDadoAluno("NOME4","CPF4",2);

                                crud.insereDadoAluno("NOME5","CPF5",2);

                                crud.insereDadoAluno("NOME6","CPF6",3);

                                crud.insereDadoProfessorTurma(1,1);
                                crud.insereDadoProfessorTurma(1,2);

                                ///////////////////////////
                            }

                            Intent it = new Intent(MainActivity.this, TelaLogado.class);
                            it.putExtra("idProfessor", ""+prof.get(0).id);
                            startActivity(it);
                        }else{
                            Toast.makeText(getApplicationContext(), "Login e/ou Senha ErradXs!", Toast.LENGTH_LONG).show();
                        }
                    }
                    // When error occured
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Cursor c = crud.carregaProfessor(sCPF,sSenha);

                        if(c.getCount()==1) {

                            Intent it = new Intent(MainActivity.this, TelaLogado.class);
                            it.putExtra("idProfessor", ""+c.getString(0));
                            startActivity(it);
                        }else{
                            Toast.makeText(getApplicationContext(), "Login e/ou Senha ErradXs!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    public class Professor {
        public int id=-1;
        public String nome;
        public String cpf;
        public String senha;
    }

}