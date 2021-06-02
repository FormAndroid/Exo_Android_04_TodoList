package be.bxl.formation.exo_04_sqlite.db;

public class DbInfo {

    public static final String DB_NAME = "my-db";
    public static final int DB_VERSION = 1;

    public static class CategoryTable {
        // Le nom de la table
        public static final String TABLE_NAME = "category";

        // Les noms des colonnes
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";

        // Les requetes SQL (DDL)
        public static final String REQUEST_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL "
                + ");" ;

        public static final String REQUEST_DROP =
                "DROP TABLE " + TABLE_NAME + ";" ;

        // Les requetes SQL (DML)
        public static final String REQUEST_ADD_INITIAL_DATA =
                "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (?) ;";
    }
}
