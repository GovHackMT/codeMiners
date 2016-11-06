package escolapp.ufmt.ic.br.escolaapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ''''VINI on 05/11/2016.
 */
public class DiarioAluno extends AppCompatActivity {
    ListView lvDiarioAluno;
    Cursor cursor;
    AdaptadorAluno adaptador;
    ArrayList<Aluno> arrayAlunos;
    BDController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diario_aluno);

        EditText dataCalendario = (EditText) findViewById(R.id.etdata_calendario);

        final String dataSelecionada = this.getIntent().getStringExtra("dataSelecionada");
        final int idTurma = Integer.valueOf(this.getIntent().getStringExtra("idTurma"));

        dataCalendario.setText(dataSelecionada);

        ////////////

        crud = new BDController(getBaseContext());
        cursor = crud.carregaDadosAluno(idTurma);

        arrayAlunos = new ArrayList<Aluno>();
        if(cursor.getCount()>0) {
            try {
                Aluno novo1 = new Aluno(cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("nome")),
                        cursor.getString(cursor.getColumnIndex("cpf")),
                        cursor.getInt(cursor.getColumnIndex("_idturma_atual")));
                novo1.data = dataSelecionada;

                Cursor res = crud.verificarIdDataPresenca2(novo1.id, novo1.data);

                if (res.getCount() == 1 && res.getInt(1) == 1) {
                    novo1.presenca = true;
                }

                arrayAlunos.add(novo1);

                while (cursor.moveToNext()) {
                    Aluno novo = new Aluno(cursor.getInt(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("nome")),
                            cursor.getString(cursor.getColumnIndex("cpf")),
                            cursor.getInt(cursor.getColumnIndex("_idturma_atual")));
                    novo.data = dataSelecionada;

                    Cursor res2 = crud.verificarIdDataPresenca2(novo.id, novo.data);

                    if (res2.getCount() == 1 && res2.getInt(1) == 1) {
                        novo.presenca = true;
                    }

                    arrayAlunos.add(novo);
                }
            } finally {
                cursor.close();
            }

            adaptador = new AdaptadorAluno(this, arrayAlunos);

            lvDiarioAluno = (ListView) findViewById(R.id.lvdiario_aluno);
            lvDiarioAluno.setAdapter(adaptador);

            Button botaoSalvar = (Button) findViewById(R.id.btsalvar_diario_aluno);

            botaoSalvar.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   for (int i = 0; i < adaptador.getCount(); i++) {
                                                       arrayAlunos.set(i, adaptador.getItem(i));
                                                   }

                                                   crud.addPresenca(arrayAlunos);

                                                   finish();
                                               }
                                           }
            );
        }
    }

    public class AdaptadorAluno extends ArrayAdapter<Aluno> {
        public AdaptadorAluno(Context context, ArrayList<Aluno> alunos) {
            super(context, 0, alunos);
        }

        View v;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Aluno aluno = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nome, parent, false);
                v = convertView;
            }

            // Lookup view for data population
            TextView tvNomeItem = (TextView) convertView.findViewById(R.id.tvnome_item);
            // Populate the data into the template view using the data object
            tvNomeItem.setText(aluno.nome);

            if(aluno.presenca == true){
                convertView.setBackgroundColor(0xFF00FF00);
            }else{
                v.setBackgroundColor(Color.TRANSPARENT);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(v.getBackground() == null || v.getBackground().getAlpha() == 0){
                        v.setBackgroundColor(0xFF00FF00);
                        aluno.presenca = true;
                    }else{
                        v.setBackgroundColor(Color.TRANSPARENT);
                        aluno.presenca = false;
                    }
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
