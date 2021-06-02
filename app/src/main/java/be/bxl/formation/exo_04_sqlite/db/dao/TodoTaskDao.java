package be.bxl.formation.exo_04_sqlite.db.dao;

import android.content.Context;

import java.util.List;

import be.bxl.formation.exo_04_sqlite.models.TodoTask;

public class TodoTaskDao extends DaoBase<TodoTask> {

    public TodoTaskDao(Context context) {
        super(context);
    }

    @Override
    public long insert(TodoTask todoTask) {
        return 0;
    }

    @Override
    public TodoTask get(long id) {
        return null;
    }

    @Override
    public List<TodoTask> getAll() {
        return null;
    }

    @Override
    public boolean update(long id, TodoTask todoTask) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
