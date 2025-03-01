import { AsyncLocalStorage } from 'async_hooks';

export class ThreadContext {
    private static storage = new AsyncLocalStorage<Map<string, any>>();

    public static setValue(key: string, value: any): void {
        const store = ThreadContext.storage.getStore();

        if (!store) {
            throw new Error('No store available. Did you forget to call ThreadContext.run()?');
        }
        store.set(key, value);
    }

    public static getValue(key: string): any | undefined {
        const store = ThreadContext.storage.getStore();
        if (!store) {
            throw new Error('No store available. Did you forget to call ThreadContext.run()?');
        }

        return store ? store.get(key) : undefined;
    }

    public static run(callback: () => void): void {
        ThreadContext.storage.run(new Map(), callback);
    }

    public static async runAsync<T>(callback: () => Promise<T>): Promise<T> {
        return new Promise<T>((resolve, reject) => {
            ThreadContext.storage.run(new Map(), async () => {
                try {
                    const result = await callback();
                    resolve(result);
                } catch (error) {
                    reject(error);
                }
            });
        });
    }
}
