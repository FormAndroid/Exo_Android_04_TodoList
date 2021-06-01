package be.bxl.formation.exo_04_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Possibilité pour le setOnClickListener
// - Implementer l'interface View.OnClickListener sur l'activité
// - Créer une classe anonyme dans la méthode
// - Créer une lambda dans la méthode


public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_main_add_task);
        btnClear = findViewById(R.id.btn_main_clear_all);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTodoActivity();
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllTask();
            }
        });
    }

    private void openAddTodoActivity() {
        Intent openAddScreen = new Intent(getApplicationContext(), AddTodoActivity.class);
        startActivity(openAddScreen);
    }

    private void clearAllTask() {
        //TODO Faire des trucs ici =)
        throw new RuntimeException("Faut travailler :o");
    }
}

