package pertsev.VCS.FileHandlers;

import pertsev.VCS.Commit.Change;
import pertsev.VCS.Commit.ChangeCreate;
import pertsev.VCS.Commit.ChangeDelete;
import pertsev.VCS.Commit.ChangeEdit;
import pertsev.VCS.File.FileState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileTextComparator implements Comparator<FileState> {

    //ВОПРОС: Приемлемое решение или нет? Может, нужно дать больше эффективности, не проходясь по-всему файлу целиком?
    // С точки зрения абстракции это повлиять на дело не должно, но такой код все-таки чуть проще читается
    @Override
    public int compare(FileState oldFile, FileState newFile) {
        List<Change> changes = getDiffs(oldFile, newFile);

        if (!changes.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    public List<Change> getDiffs(FileState oldFile, FileState newFile) {
        if (!oldFile.getPath().equals(newFile.getPath())) throw new IllegalArgumentException();

        List<Change> changes = new ArrayList<>();

        if (!oldFile.isExist() && newFile.isExist()) { // Файл создали
            changes.add(new ChangeCreate());
            //После того, как файл создали, его могли редактировать, поэтому ещё запишем изменения текста
            changes.add(new ChangeEdit(0, newFile.getValue()));
        } else if (oldFile.isExist() && !newFile.isExist()) { // Файл удалили
            changes.add(new ChangeDelete());
        } else if (oldFile.isExist() && newFile.isExist()) { // Файл был изменен (или не затронут)
            if (oldFile.getValue().equals(newFile.getValue())) {
                return changes;
            }

            String[] oldTextLines = oldFile.getValue().split("\n");
            String[] newTextLines = newFile.getValue().split("\n");

            //Проверим совпадающую по длине часть
            for (int i = 0; i < Math.min(oldTextLines.length, newTextLines.length); i++) {
                if (!oldTextLines[i].equals(newTextLines[i])) {
                    changes.add(new ChangeEdit(i, newTextLines[i]));
                }
            }

            //Добавим (уже точно измененную) оставшуюся часть
            for (int i = Math.min(oldTextLines.length, newTextLines.length); i < newTextLines.length; i++) {
                changes.add(new ChangeEdit(i, newTextLines[i]));
            }
        } else {
            throw new RuntimeException();
        }

        return changes;
    }
}
