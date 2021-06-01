package be.bxl.formation.exo_04_sqlite.enums;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import be.bxl.formation.exo_04_sqlite.R;

public enum PriorityEnum {

    HIGH(10, R.string.priority_high),
    NORMAL(5, R.string.priority_normal),
    LOW(0, R.string.priority_low);

    private int level;

    @StringRes
    private int name;

    PriorityEnum(int level, @StringRes int name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName(@NonNull Context context) {
        return context.getString(this.name);
    }


    public static PriorityEnum parse(@NonNull Context context, @NonNull String name) {
        boolean found = false;
        PriorityEnum[] values = PriorityEnum.values();
        PriorityEnum result = null;

        for(int i = 0; !found && i < values.length ; i++) {
            PriorityEnum v = values[i];

            if(v.getName(context).equals(name)) {
                found = true;
                result = v;
            }
        }

        if(!found) {
            throw new RuntimeException("Parse PriorityEnum fail !");
        }

        return result;
    }
}
