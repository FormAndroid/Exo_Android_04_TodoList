package be.bxl.formation.exo_04_sqlite.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import be.bxl.formation.exo_04_sqlite.db.DbInfo;
import be.bxl.formation.exo_04_sqlite.enums.PriorityEnum;
import be.bxl.formation.exo_04_sqlite.models.TodoTask;

public class TodoTaskDao extends DaoBase<TodoTask> {

    public TodoTaskDao(Context context) {
        super(context);
    }

    private ContentValues generateContentValues(TodoTask task) {
        String name = task.getName();
        int priority = task.getPriority().getLevel();
        String creation = task.getCreationDate().format(DateTimeFormatter.ISO_DATE);
        String limit = task.getLimitDate() != null ?
                task.getLimitDate().format(DateTimeFormatter.ISO_DATE) : null;
        String finish = task.getFinishDate() != null ?
                task.getFinishDate().format(DateTimeFormatter.ISO_DATE) : null;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbInfo.TodoTaskTable.COLUMN_NAME, name);
        contentValues.put(DbInfo.TodoTaskTable.COLUMN_PRIORITY, priority);
        contentValues.put(DbInfo.TodoTaskTable.COLUMN_CREATION_DATE, creation);
        contentValues.put(DbInfo.TodoTaskTable.COLUMN_LIMIT_DATE , limit);
        contentValues.put(DbInfo.TodoTaskTable.COLUMN_FINISH_DATE, finish);

        return contentValues;
    }

    private TodoTask cursorToData(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_NAME));
        int priorityLevel = cursor.getInt(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_PRIORITY));
        String creation = cursor.getString(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_CREATION_DATE));
        String limit = cursor.getString(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_LIMIT_DATE));
        String finish = cursor.getString(cursor.getColumnIndex(DbInfo.TodoTaskTable.COLUMN_FINISH_DATE));

        PriorityEnum priority = PriorityEnum.fromLevel(priorityLevel);
        LocalDate creationDate = LocalDate.parse(creation, DateTimeFormatter.ISO_DATE);
        LocalDate limitDate = limit != null ? LocalDate.parse(limit, DateTimeFormatter.ISO_DATE) : null;
        LocalDate finishDate = finish != null ? LocalDate.parse(finish, DateTimeFormatter.ISO_DATE) : null;

        return new TodoTask(id, name, priority, creationDate, limitDate, finishDate);
    }

    @Override
    public long insert(TodoTask todoTask) {
        ContentValues cv = generateContentValues(todoTask);
        return db.insert(DbInfo.TodoTaskTable.TABLE_NAME, null, cv);
    }

    @Override
    public TodoTask get(long id) {
        String whereClause = DbInfo.TodoTaskTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };
        Cursor cursor = db.query(DbInfo.TodoTaskTable.TABLE_NAME, null, whereClause, whereArg, null, null, null);

        if(cursor.getCount() == 0) { return null;        }

        cursor.moveToFirst();
        TodoTask result = cursorToData(cursor);
        cursor.close();

        return result;
    }

    @Override
    public List<TodoTask> getAll() {
        List<TodoTask> results = new ArrayList<>();

        Cursor cursor = db.query(DbInfo.TodoTaskTable.TABLE_NAME, null, null, null, null, null, null);

        if(cursor.getCount() == 0) { return results; }

        cursor.moveToFirst();
        while(! cursor.isAfterLast()) {
            TodoTask task = cursorToData(cursor);
            results.add(task);

            cursor.moveToNext();
        }
        cursor.close();

        return results;
    }

    public List<TodoTask> getUnfinishedTask() {
        List<TodoTask> results = new ArrayList<>();

        String whereClause = DbInfo.TodoTaskTable.COLUMN_FINISH_DATE + " IS NULL";
        Cursor cursor = db.query(DbInfo.TodoTaskTable.TABLE_NAME, null, whereClause, null, null, null, null);

        if(cursor.getCount() == 0) { return results; }

        cursor.moveToFirst();
        while(! cursor.isAfterLast()) {
            TodoTask task = cursorToData(cursor);
            results.add(task);

            cursor.moveToNext();
        }
        cursor.close();

        return results;
    }

    @Override
    public boolean update(long id, TodoTask todoTask) {
        ContentValues cv = generateContentValues(todoTask);

        String whereClause = DbInfo.TodoTaskTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.update(DbInfo.TodoTaskTable.TABLE_NAME, cv, whereClause, whereArg);
        return nbRow == 1;
    }

    @Override
    public boolean delete(long id) {
        String whereClause = DbInfo.TodoTaskTable.COLUMN_ID + " = ?";
        String[] whereArg = new String[] {
                String.valueOf(id)
        };

        int nbRow = db.delete(DbInfo.TodoTaskTable.TABLE_NAME, whereClause, whereArg);
        return nbRow == 1;
    }
}
