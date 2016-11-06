package escolapp.ufmt.ic.br.escolaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ''''VINI on 05/11/2016.
 */
public class TelaLogado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_logado);

        Button btDiarioAluno = (Button)findViewById(R.id.btdiario_aluno);
        //Button btPontoProfessor = (Button)findViewById(R.id.btponto_professor);

        final String idProfessor = this.getIntent().getStringExtra("idProfessor");

        btDiarioAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent it = new Intent(TelaLogado.this, Calendario.class);
                    it.putExtra("tipo", "diario_aluno");
                    it.putExtra("idProfessor", idProfessor);
                    startActivity(it);

            }}
        );

        /*btPontoProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(TelaLogado.this, Calendario.class);
                it.putExtra("tipo", "ponto_professor");
                startActivity(it);

            }}
        );*/
}}

