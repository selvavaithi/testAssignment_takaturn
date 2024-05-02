package Components;

import java.util.function.Supplier;

public class IComponents {

    public static <T extends BaseComponents> T getComponents(Supplier<T> sup){
        return sup.get();
    }
}
