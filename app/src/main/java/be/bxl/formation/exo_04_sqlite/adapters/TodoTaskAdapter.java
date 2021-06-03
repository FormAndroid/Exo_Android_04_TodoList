package be.bxl.formation.exo_04_sqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import be.bxl.formation.exo_04_sqlite.R;
import be.bxl.formation.exo_04_sqlite.models.TodoTask;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.ViewHolder> {

    @FunctionalInterface
    public interface EventFinishTask {
        void onFinish(TodoTask task);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvPriority, tvLimitDate, tvDate;
        private Button btnFinish;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvName = v.findViewById(R.id.tv_item_task_name);
            tvPriority = v.findViewById(R.id.tv_item_task_priority_value);
            tvLimitDate = v.findViewById(R.id.tv_item_task_limit_date);
            tvDate = v.findViewById(R.id.tv_item_task_date);
            btnFinish = v.findViewById(R.id.btn_item_task_finish);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvPriority() {
            return tvPriority;
        }

        public TextView getTvLimitDate() {
            return tvLimitDate;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public Button getBtnFinish() {
            return btnFinish;
        }
    }

    private Context context;
    private List<TodoTask> dataSet;
    private EventFinishTask eventFinishTask;

    public TodoTaskAdapter(Context context, List<TodoTask> tasks) {
        this.context = context;
        this.dataSet = tasks;
    }

    public void setEventFinishTask(EventFinishTask eventFinishTask) {
        this.eventFinishTask = eventFinishTask;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_todo_task, parent, false);
        return new TodoTaskAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoTaskAdapter.ViewHolder holder, int position) {
        TodoTask task = dataSet.get(position);

        holder.getTvName().setText(task.getName());

        String priority = task.getPriority().getName(context);
        holder.getTvPriority().setText(priority);

        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(context.getString(R.string.date_pattern), locale);

        String dateMessage;
        String dateFormatted;
        if(task.getFinishDate() == null) {
            dateMessage = context.getString(R.string.create_date_task);
            dateFormatted = task.getCreationDate().format(formatter);
        }
        else {
            dateMessage = context.getString(R.string.finish_date_task);
            dateFormatted = task.getFinishDate().format(formatter);
            holder.getBtnFinish().setEnabled(false);
        }
        holder.getTvDate().setText(String.format(dateMessage, dateFormatted));

        if (task.getLimitDate() != null) {
            String limitMessage = context.getString(R.string.limit_date_task);
            String limitDateFormatted = task.getLimitDate().format(formatter);

            holder.getTvLimitDate().setVisibility(View.VISIBLE);
            holder.getTvLimitDate().setText(String.format(limitMessage, limitDateFormatted));
        }
        else {
            holder.getTvLimitDate().setVisibility(View.GONE);
            holder.getTvLimitDate().setText("");
        }

        holder.getBtnFinish().setOnClickListener(v -> {
            this.eventFinishTask.onFinish(task);
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
