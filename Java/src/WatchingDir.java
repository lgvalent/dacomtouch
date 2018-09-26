
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael
 */
public class WatchingDir extends Thread {

    private Runnable target;
    private String path;
    
    public WatchingDir(String path, Runnable target) {
        this.target = target;
        this.path = path;
    }
    

    @Override
    public void run() {
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(this.path);
            WatchKey watchKey = path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            do {
                watchKey = service.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();
                    this.target.run();
//                    System.out.println(kind + ": " + eventPath);
                }
            } while (watchKey.reset());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro in whatch directory!");
        }
    }
}
