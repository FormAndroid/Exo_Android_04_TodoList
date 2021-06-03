package be.bxl.formation.exo_04_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.bxl.formation.exo_04_sqlite.adapters.TodoTaskAdapter;
import be.bxl.formation.exo_04_sqlite.db.dao.TodoTaskDao;
import be.bxl.formation.exo_04_sqlite.models.TodoTask;

// Possibilité pour le setOnClickListener
// - Implementer l'interface View.OnClickListener sur l'activité
// - Créer une classe anonyme dans la méthode
// - Créer une lambda dans la méthode


public class MainActivity extends AppCompatActivity {

    private List<TodoTask> tasks = new ArrayList<>();
    private TodoTaskAdapter taskAdapter;

    Button btnAdd, btnClear;
    RecyclerView lvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_main_add_task);
        btnClear = findViewById(R.id.btn_main_clear_all);
        lvTasks = findViewById(R.id.lv_main_task_list);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTodoActivity();
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogClearTask();
            }
        });


        // Adapter
        taskAdapter = new TodoTaskAdapter(this, tasks);

        // Event on list
        taskAdapter.setEventFinishTask(new TodoTaskAdapter.EventFinishTask() {
            @Override
            public void onFinish(TodoTask task) {
                finishTask(task);
            }
        });


        // Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lvTasks.setLayoutManager(layoutManager);
        lvTasks.setHasFixedSize(false);
        lvTasks.setAdapter(taskAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Récuperation des tasks
        TodoTaskDao taskDao = new TodoTaskDao(getApplicationContext());
        taskDao.openReadable();
        List<TodoTask> data = taskDao.getAll();
        taskDao.close();

        // Mise a jours de l'adapter de la RecyclerView
        tasks.clear();
        tasks.addAll(data);
        taskAdapter.notifyDataSetChanged();
    }

    private void openAddTodoActivity() {
        Intent openAddScreen = new Intent(getApplicationContext(), AddTodoActivity.class);
        startActivity(openAddScreen);
    }

    private void openDialogClearTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearAllTask();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // On ne fait rien ;)
                    }
                })
                .show();
    }

    private void finishTask(TodoTask task) {
        // Mise a jours de la date de validation
        task.setFinishDate(LocalDate.now());

        // Sauvegarde dans la DB
        TodoTaskDao taskDao = new TodoTaskDao(getApplicationContext());
        taskDao.openWritable();
        taskDao.update(task.getTodoTaskId(), task);
        taskDao.close();

        // Mise a jours de la RecyclerView
        taskAdapter.notifyDataSetChanged();
    }

    private void clearAllTask() {
        // Supression des tasks
        TodoTaskDao taskDao = new TodoTaskDao(getApplicationContext());
        taskDao.openWritable();
        taskDao.deleteAll();
        taskDao.close();

        // Mise a jours de l'adapter de la RecyclerView
        tasks.clear();
        taskAdapter.notifyDataSetChanged();
    }
}

