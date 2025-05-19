package com.br.tasks.domain.enums;

public enum TaskTypeEnum {
    WORK("W"),
    EXERCISE("E"),
    CHORE("C"),
    DEFAULT("D");

    private final String label;

    TaskTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TaskTypeEnum fromLabel(String label) {
        for (TaskTypeEnum type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return type;
            }
        }
        return DEFAULT;
    }
}
