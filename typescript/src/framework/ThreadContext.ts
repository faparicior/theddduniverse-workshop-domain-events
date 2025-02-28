import { AsyncLocalStorage } from 'async_hooks';

export class ThreadContext {
    private static storage = new AsyncLocalStorage<Map<string, any>>();

    public static setValue(key: string, value: any): void {
        const store = ThreadContext.storage.getStore();
        if (store) {
            store.set(key, value);
        }
    }

    public static getValue(key: string): any | undefined {
        const store = ThreadContext.storage.getStore();
        return store ? store.get(key) : undefined;
    }

    public static run(callback: () => void): void {
        ThreadContext.storage.run(new Map(), callback);
    }
}
