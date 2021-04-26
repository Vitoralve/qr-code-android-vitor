package com.example.scanner.de.scannertest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Confirmação extends AppCompatActivity {

    TextView txtNome, txtCurso, txtMatricula, txtEntrada;
    Button btnConfirmação;
    private String Aluno = MainActivity.Aluno.getText().toString();
    private String AlunoScanner = MainActivity.AlunoScanner;



    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    Date hora = Calendar.getInstance().getTime();
    String dataFormatada = sdf.format(hora);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        txtNome = findViewById(R.id.txtNome);
        txtCurso = findViewById(R.id.txtCurso);
        txtMatricula = findViewById(R.id.txtMatricula);
        txtEntrada = findViewById(R.id.txtEntrada);
        btnConfirmação = findViewById(R.id.btnEntrada);
        txtEntrada.setText(dataFormatada);


        /*
         * Evento de Gestão com o banco de dados
         */


        //Referencia com o banco de dadaos
        DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();


        //Serviço de pesquisa feito atraves do Scanner
        if (Aluno.isEmpty()) {

            Aluno = AlunoScanner;

            if (Aluno.equals(Aluno)) {


                txtMatricula.setText(Aluno);

                DatabaseReference NomeAluno1 = referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Nome");
                DatabaseReference CursoAluno1 = referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Curso");


                //Buscando Nome Do banco de dados
                NomeAluno1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        txtNome.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        txtNome.setText("Erro 404");
                    }
                });

                //Buscando Curso Banco de dados
                CursoAluno1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        txtCurso.setText(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        txtNome.setText("Erro 404");

                    }
                });


            } else {

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }

        }

        //Serviço de pesquisa feito atraves da digitação da matricula
        else if (Aluno.equals(Aluno)) {


            txtMatricula.setText(Aluno);

            DatabaseReference NomeAluno1 = referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Nome");
            DatabaseReference CursoAluno1 = referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Curso");


            //Buscando Nome Do banco de dados
            NomeAluno1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    txtNome.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    txtNome.setText("Erro 404");
                }
            });

            //Buscando Curso Banco de dados
            CursoAluno1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    txtCurso.setText(dataSnapshot.getValue().toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    txtNome.setText("Erro 404");

                }
            });
        }

    }
        //Lançamento de hora de chegada
        public void ButtonChegada (View view){

            DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

            referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Evento").child("Hora de Entrada").setValue(dataFormatada);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(
                    getApplicationContext(), "Entrada Confirmada",
                    Toast.LENGTH_SHORT
            ).show();

        }


        //Laçamento de hora de Saída
        public void ButtonSaida (View view){

            DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

            referencia.child("Usuario").child("Matricula").child(Aluno).child("DadosAluno").child("Evento").child("Hora de Saida").setValue(dataFormatada);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(
                    getApplicationContext(), "Saída Confirmada",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


