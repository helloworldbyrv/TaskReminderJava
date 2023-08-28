import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

class TaskReminder {
    private ScheduledExecutorService scheduler;

    public TaskReminder() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void scheduleTask(String taskName, Date dueDate) {
        long delay = dueDate.getTime() - System.currentTimeMillis();
        
        if (delay <= 0) {
            System.out.println("Task '" + taskName + "' is already overdue.");
            return;
        }

        Runnable task = () -> {
            System.out.println("Reminder: Task '" + taskName + "' is due now!");
        };

        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
        System.out.println("Task '" + taskName + "' scheduled for " + dueDate);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        TaskReminder taskReminder = new TaskReminder();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter task name (or 'exit' to quit): ");
            String taskName = scanner.nextLine();
            if (taskName.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter due date (yyyy-MM-dd HH:mm): ");
            String dueDateStr = scanner.nextLine();
            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dueDateStr);
                taskReminder.scheduleTask(taskName, dueDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
            }
        }

        taskReminder.shutdown();
        scanner.close();
    }
}