package be.bxl.formation.exo_04_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.bxl.formation.exo_04_sqlite.enums.PriorityEnum;
import be.bxl.formation.exo_04_sqlite.models.Category;

public class AddTodoActivity extends AppCompatActivity {

    private List<Category> categories;
    private ArrayAdapter<Category> categoryAdapter;
    private LocalDate limitDateSelected = null;

    EditText etName, etLimitDate;
    ImageView imgOpenCalendar;
    Spinner spPriority, spCategory;
    Button btnValid, btnCancel, btnAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        etName = findViewById(R.id.et_add_task_name);
        etLimitDate = findViewById(R.id.et_add_task_limit_date);
        imgOpenCalendar = findViewById(R.id.img_add_task_open_calendar);
        spPriority = findViewById(R.id.sp_add_task_priority);
        spCategory = findViewById(R.id.sp_add_task_category);
        btnValid = findViewById(R.id.btn_add_task_valid);
        btnCancel = findViewById(R.id.btn_add_task_cancel);
        btnAddCategory = findViewById(R.id.btn_add_task_new_category);

        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCategory();
            }
        });


        // DatePicker de la date limite
        //  - Déactivation de la saisie via le clavier
        etLimitDate.setInputType(InputType.TYPE_NULL);

        // - Ouverture d'un dialog pour la date (Alternative => le déclancher via l'editText)
        imgOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int initialDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int initialMonth = calendar.get(Calendar.MONTH);
                int initialYear = calendar.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(
                        AddTodoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                limitDateSelected = LocalDate.of(year, month + 1, dayOfMonth);

                                Locale locale = getApplicationContext().getResources().getConfiguration().getLocales().get(0);
                                String date = limitDateSelected.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", locale));
                                etLimitDate.setText(date);
                            }
                        },
                        initialYear, initialMonth, initialDayOfMonth);
                picker.show();
            }
        });


        // Spinner des priorités
        List<String> priorities = new ArrayList<>();
        for(PriorityEnum priority : PriorityEnum.values()) {
            priorities.add(priority.getName(getApplicationContext()));
        }

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                priorities
        );
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPriority.setAdapter(priorityAdapter);


        // Spinner des catégories
        //FIXME Fausse liste de categorie => A remplacer par l'acces a la DB
        categories = new ArrayList<>();
        categories.add(new Category(1, "Riri"));
        categories.add(new Category(2, "Fifi"));
        categories.add(new Category(3, "Zaza"));

        categoryAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCategory.setAdapter(categoryAdapter);

    }

    private void addNewTask() {
        throw  new RuntimeException();
    }

    private void openDialogCategory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Note : On donne en parametre le context de l'activité actuelle
        //        ce qui lui fourni egalement le theme à utiliser.

        builder.setTitle(R.string.dialog_titlte_add_category)
                .setView(R.layout.dialog_add_category)
                .setPositiveButton(R.string.dialog_btn_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etName = ((AlertDialog)dialog).findViewById(R.id.et_dialog_add_category_name);
                        String nameCategory = etName.getText().toString();

                        addNewCategory(nameCategory);
                    }
                })
                .setNegativeButton(R.string.dialog_btn_cancel, new DialogInterface.OnClickListener() {
                    // On laisse le "setNegativeButton" pour avoir le bouton negatif
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // On ne fait rien
                    }
                })
                .show();
    }

    private void addNewCategory(String name) {
        //FIXME Replace by add in database (Solution temporaire : Recupere l'id max et lui ajoute 1)
        long id = categories.stream().map(c -> c.getCategoryId()).max(Long::compareTo).orElse(0l) + 1;

        categories.add(new Category(id, name));
        categoryAdapter.notifyDataSetChanged();
    }


    private void closeActivity() {
        finish();
    }
}