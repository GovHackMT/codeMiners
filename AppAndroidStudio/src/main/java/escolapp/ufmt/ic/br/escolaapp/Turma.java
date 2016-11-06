package escolapp.ufmt.ic.br.escolaapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ''''VINI on 06/11/2016.
 */
public class Turma extends AppCompatActivity {
    BDController crud;
    ArrayList<TurmaObj> arrayTurmas;
    String dataSelecionada;
    AdaptadorTurma adaptador;
    ListView lvTurmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turma);

        dataSelecionada = this.getIntent().getStringExtra("dataSelecionada");
        final String idProfessor = this.getIntent().getStringExtra("idProfessor");

        crud = new BDController(getBaseContext());

        Cursor turmas = crud.carregaTurmasProfessor(Integer.valueOf(idProfessor));

        arrayTurmas = new ArrayList<TurmaObj>();

        if(turmas.getCount()>0) {
            try {
                Cursor res1 = crud.carregaTurma(turmas.getInt(turmas.getColumnIndex("_idturma")));

                TurmaObj novo1 = new TurmaObj(res1.getInt(res1.getColumnIndex("_id")),
                        res1.getString(res1.getColumnIndex("nome")));
                arrayTurmas.add(novo1);

                while (turmas.moveToNext()) {
                    Cursor res2 = crud.carregaTurma(turmas.getInt(turmas.getColumnIndex("_idturma")));

                    TurmaObj novo = new TurmaObj(res2.getInt(res2.getColumnIndex("_id")),
                            res2.getString(res2.getColumnIndex("nome")));
                    arrayTurmas.add(novo);
                }

            } finally {
                turmas.close();
            }

            adaptador = new AdaptadorTurma(this, arrayTurmas);

            lvTurmas = (ListView) findViewById(R.id.lvturmas);
            lvTurmas.setAdapter(adaptador);
        }
        }

    public class TurmaObj{
        public int id;
        public String nome;

        public TurmaObj(int id, String nome){
            this.id = id;
            this.nome = nome;
        }
    }

    public class AdaptadorTurma extends ArrayAdapter<TurmaObj> {
        public AdaptadorTurma(Context context, ArrayList<TurmaObj> turmas) {
            super(context, 0, turmas);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final TurmaObj turma = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nome, parent, false);
            }

            // Lookup view for data population
            TextView tvNomeItem = (TextView) convertView.findViewById(R.id.tvnome_item);
            // Populate the data into the template view using the data object
            tvNomeItem.setText(turma.nome);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Turma.this, DiarioAluno.class);
                    it.putExtra("dataSelecionada", dataSelecionada);
                    it.putExtra("idTurma", ""+turma.id);
                    startActivity(it);
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }
}