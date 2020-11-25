package entities;

public enum TaskPriority
{
    URGENT("Urgent"), MEDIUM("Medium"), LOW("Low");

    private String priorityString;
    private int priorityInt;

    TaskPriority(String s)
    {
        this.priorityString = s;
        switch (s)
        {
            case "Low":
                setPriorityInt(0);
                break;
            case "Medium":
                setPriorityInt(1);
                break;
            case "Urgent":
                setPriorityInt(2);
                break;
        }
    }

    public String getPriorityString()
    {
        return priorityString;
    }

    public void setPriorityString(String priorityString) {
        this.priorityString = priorityString;
    }

    public int getPriorityInt() {
        return priorityInt;
    }

    public void setPriorityInt(int priorityInt) {
        this.priorityInt = priorityInt;
    }
}
