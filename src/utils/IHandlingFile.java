
package utils;

import java.util.List;

public interface IHandlingFile<T> {
    boolean saveToFile(String filePath, List<T> list);
    boolean loadFromFile(String filePath, List<T> list);
}
