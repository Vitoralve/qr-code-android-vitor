package com.example.scanner.de.scannertest;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    Button btnScanner, btnMatricula;
    public static TextInputEditText Aluno;
    public static String AlunoScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnScanner = findViewById(R.id.btnScanner);
        btnMatricula = findViewById(R.id.btnMatricula);
        Aluno = findViewById(R.id.edtAluno);

        final Activity activity= this;


        //Configuração de LeitorQrCode
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("SCAN");
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null) {
                ResultadoScanner(result.getContents());

            }else {
                ResultadoScanner("Scanner Cancelado");

            }
        }else  {
            super.onActivityResult(requestCode,resultCode,data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    //Configurando Resultado do Scanner
    public void ResultadoScanner (String msg) {

       AlunoScanner = msg;
        Toast.makeText(
                getApplicationContext(), (msg),
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(this, Confirmação.class);
        startActivity(intent);
    }

    //Configurando Resultado quando pesquisa e feita com matricula
    public void LeituraMatricula (View view) {

         String Matricula = Aluno.getText().toString();

        if ( Matricula.isEmpty()) {

            //Instanciar AlertDialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            //Configurar Titulo e mensagem
            dialog.setTitle("Digite uma Matricula");
            dialog.setMessage("Você precisa digitar uma matricula para confirmar a presença.");

            //Configurar Cancelamento
            dialog.setCancelable(false);


            //configurar icone
            dialog.setIcon(android.R.drawable.ic_dialog_alert);

            //Configurar açoes para sim e não
            dialog.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(
                            getApplicationContext(), "Pagina de Scanner",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });

            //Criar e exibir AlertDialog
            dialog.create();
            dialog.show();


        } else {


            Intent intent = new Intent(this, Confirmação.class);
            startActivity(intent);


        }
    }



}

