package unip.aps.ui.scenes;

public class ExitScreen implements Runnable {
    @Override
    public void run() {
        System.out.println("Saindo do sistema...");
        System.exit(0);
    }
}
