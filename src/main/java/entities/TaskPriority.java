package entities;

public enum TaskPriority
{
    URGENT("Urgent"), MEDIUM("Medium"), LOW("Low");

    private String priorityString;
    TaskPriority(String s)
    {
        this.priorityString = s;
    }

    String getValue()
    {
        return priorityString;
    }
}
