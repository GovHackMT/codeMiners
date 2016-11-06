package escolapp.ufmt.ic.br.escolaapp;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ''''VINI on 05/11/2016.
 */
public class Calendario extends AppCompatActivity {
    GregorianCalendar dataSelecionada;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario);

        final String tipo = this.getIntent().getStringExtra("tipo");
        final String idProfessor = this.getIntent().getStringExtra("idProfessor");

        final CalendarView calendario = (CalendarView) findViewById(R.id.calendarView);

        Button btContinuar = (Button)findViewById(R.id.btcontinuar);

        Calendar c = Calendar.getInstance();

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        dataSelecionada = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));

        final long dataAtual = dataSelecionada.getTimeInMillis();

        calendario.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                dataSelecionada = new GregorianCalendar(year, month, dayOfMonth);
            }
        });

        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dataSelecionada.getTimeInMillis() > dataAtual){
                    Toast.makeText(getApplicationContext(), "DATA INVÁLIDA!", Toast.LENGTH_LONG).show();
                }else {

                    sdf.setCalendar(dataSelecionada);

                    String dataIntent = sdf.format(dataSelecionada.getTime());

                    if(tipo.equals("diario_aluno")) {

                        Intent it = new Intent(Calendario.this, Turma.class);
                        it.putExtra("dataSelecionada", dataIntent);
                        it.putExtra("idProfessor", idProfessor);
                        startActivity(it);

                    }else{

                        Intent it = new Intent(Calendario.this, PontoProfessor.class);
                        it.putExtra("dataSelecionada", dataIntent);
                        startActivity(it);

                    }
                }
            }}
        );
    }
}