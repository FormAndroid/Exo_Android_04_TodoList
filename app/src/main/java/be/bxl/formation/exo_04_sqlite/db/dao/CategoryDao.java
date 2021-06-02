package be.bxl.formation.exo_04_sqlite.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import be.bxl.formation.exo_04_sqlite.db.DbHelper;
import be.bxl.formation.exo_04_sqlite.db.DbInfo;
import be.bxl.formation.exo_04_sqlite.models.Category;

public class CategoryDao extends DaoBase<Category> {

    public CategoryDao(Context context) {
        super(context);
    }



    // Méthode du CRUD
    private ContentValues generateContentValues(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbInfo.CategoryTable.COLUMN_NAME, category.getName());

        return contentValues;
    }

    private Category cursorToData(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DbInfo.CategoryTable.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(DbInfo.CategoryTable.COLUMN_NAME));

        return new Category(id, name);
    }

    // - Create
    public long insert(Category category) {
        ContentValues cv = generateContentValues(category);

        return db.insert(DbInfo.CategoryTable.TABLE_NAME, null, cv);
    }

    // - Read
    public Category get(long id) {
        String whereClause = DbInfo.CategoryTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(DbInfo.CategoryTable.TABLE_NAME, null, whereClause, whereArg, null, null, null);

        // Tester si on a un resultat
        if(cursor.getCount() == 0) {
            return null;
        }

        // On déplace le curseur sur le resutlat
        cursor.moveToFirst();

        // On lire les données pointée par le curseur
        Category result = cursorToData(cursor);

        // On cloture le curseur
        cursor.close();

        return result;
    }

    public List<Category> getAll() {
        // Création d'un curseur qui permet d'obtenir le resultat d'un select
        Cursor cursor = db.query(DbInfo.CategoryTable.TABLE_NAME, null, null, null, null, null, null);

        // Initialise la liste de resultat
        List<Category> results = new ArrayList<>();

        // Verification qu'il y a un resultat
        if(cursor.getCount() == 0) {
            return results; // Liste vide
        }

        // On place le curseur sur le premier resultat
        cursor.moveToFirst();

        while(! cursor.isAfterLast()) {  // On continue tant qu'on a pas fait toute les resultats

            // On extrait les données du curseur
            Category cat = cursorToData(cursor);
            results.add(cat);

            // On passe à la prochain valeur de resultat
            cursor.moveToNext();
        }

        // On cloture le curseur
        cursor.close();

        // On renvoie les resultats
        return results;
    }

    // - Update
    public boolean update(long id, Category data) {
        ContentValues cv = generateContentValues(data);

        String whereClause = DbInfo.CategoryTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.update(DbInfo.CategoryTable.TABLE_NAME, cv, whereClause, whereArg);
        return nbRow == 1;
    }

    // - Delete
    public boolean delete(long id) {
        String whereClause = DbInfo.CategoryTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.delete(DbInfo.CategoryTable.TABLE_NAME, whereClause, whereArg);
        return nbRow == 1;
    }
}
