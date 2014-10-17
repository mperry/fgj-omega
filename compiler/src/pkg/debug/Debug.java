package pkg.debug;

import pkg.util.List;

/**
 * Created by mperry on 17/10/2014.
 */
public class Debug {

    public static <A> RuntimeException unexpected(A paramList1) {
        return new RuntimeException("unexpected list value: " + paramList1);
    }

    public static <A> RuntimeException illegal(A typeList, String drop, Object[] objects) {
        return new RuntimeException("illegal list value - typeList: " + typeList + " drop: " + drop + " objects: " + objects);
    }

}
