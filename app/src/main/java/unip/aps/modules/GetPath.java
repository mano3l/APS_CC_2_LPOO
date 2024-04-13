package unip.aps.modules;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GetPath{
    private final Path currentPath;



    private final String dataJson = "/app/src/main/resources/Data";

    public GetPath() {
        // Obtém o caminho atual do diretório de trabalho
        this.currentPath = Paths.get("").toAbsolutePath();

    }

    public String getDataJson() {
        return dataJson;
    }

    public Path getCurrentPath() {
        return currentPath;
    }
}


