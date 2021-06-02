package be.bxl.formation.exo_04_sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.bxl.formation.exo_04_sqlite.R;

public class DbHelper extends SQLiteOpenHelper {

    Context context;

    public DbHelper(Context context) {
        super(context, DbInfo.DB_NAME, null, DbInfo.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des bases de donnes
        db.execSQL(DbInfo.CategoryTable.REQUEST_CREATE);
        db.execSQL(DbInfo.TodoTaskTable.REQUEST_CREATE);

        // Ajout des données initial
        db.execSQL(DbInfo.CategoryTable.REQUEST_ADD_INITIAL_DATA, new String[] {context.getString(R.string.initial_category_general)});
        db.execSQL(DbInfo.CategoryTable.REQUEST_ADD_INITIAL_DATA, new String[] {context.getString(R.string.initial_category_pro)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migration d'une version d'une base de donnée vers une autre
        // - Suppression de l'ancienne version
        db.execSQL(DbInfo.CategoryTable.REQUEST_DROP);
        db.execSQL(DbInfo.TodoTaskTable.REQUEST_DROP);
        // - Appel de la méthode de création
        onCreate(db);

        // Note : Cette solution implique la perde des données !
    }
}
