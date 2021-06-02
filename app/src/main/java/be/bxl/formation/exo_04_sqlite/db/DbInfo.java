package be.bxl.formation.exo_04_sqlite.db;

import java.time.LocalDate;

import be.bxl.formation.exo_04_sqlite.enums.PriorityEnum;

public class DbInfo {

    public static final String DB_NAME = "my-db";
    public static final int DB_VERSION = 3;

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
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";" ;

        // Les requetes SQL (DML)
        public static final String REQUEST_ADD_INITIAL_DATA =
                "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (?) ;";
    }

    public static class TodoTaskTable {
        public static final String TABLE_NAME = "todo_task";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_CREATION_DATE = "create_date";
        public static final String COLUMN_LIMIT_DATE = "limit_date";
        public static final String COLUMN_FINISH_DATE = "finish_date";

        public static final String REQUEST_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( "
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME + " TEXT NOT NULL, "
                        + COLUMN_PRIORITY + " INTEGER NOT NULL, "
                        + COLUMN_CREATION_DATE + " TEXT NOT NULL, "
                        + COLUMN_LIMIT_DATE + " TEXT NULL, "
                        + COLUMN_FINISH_DATE + " TEXT NULL "
                        + ");" ;

        public static final String REQUEST_DROP =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";" ;
    }
}