import DataClasses.Graph;
import Visualiazation.*;

public class Main {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow("Dijkstra", new Graph());
        mainWindow.setVisible(true);

    }
}

//class ScheduledTask extends TimerTask {
//    // Добавляем такс
//    @Override
//    public void run() {
//        System.out.println("Hello\n");
//    }
//}
//
//class Main {
//
//    public static void main(String args[]) throws InterruptedException {
//
//        Timer time = new Timer();
//        ScheduledTask st = new ScheduledTask();
//        time.schedule(st, 0, 1000); // Создаем задачу с повторением через 1 сек.
//    }
//
//}
