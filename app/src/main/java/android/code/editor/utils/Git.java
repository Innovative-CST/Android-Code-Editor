package android.code.editor.utils;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class Git {
    public static boolean isGitRepository(File file) {
        try {
            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            repositoryBuilder.setGitDir(file);
            Repository repository = repositoryBuilder.build();
            return repository != null && repository.getObjectDatabase().exists();
        } catch (IOException e) {
            return false;
        }
    }
}
