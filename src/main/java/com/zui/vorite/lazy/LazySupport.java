package com.zui.vorite.lazy;

import java.util.function.Supplier;

/**
 * @author Dusk
 */

public class LazySupport<T> {
    final Supplier<T> supplier;

    public LazySupport(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T head() {
        return supplier.get();
    }
}
