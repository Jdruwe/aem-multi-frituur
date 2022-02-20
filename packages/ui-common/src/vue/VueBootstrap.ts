export interface ModulePromises {
    [key: string]: () => Promise<ExportedModuleDefinition>;
}

export interface ModuleDefinition {
    getComponentDefinition: () => any;
}

interface ExportedModuleDefinition {
    default: ModuleDefinition;
}

const isFulfilled = <T>(input: PromiseSettledResult<T>): input is PromiseFulfilledResult<T> =>
    input.status === 'fulfilled';

export class VueBootstrap {

    private readonly modulePromises: ModulePromises;

    constructor(modulePromises: ModulePromises) {
        this.modulePromises = modulePromises;
        this.initialize();
    }

    private initialize(): void {
        const elements = this.getElements();
        if (elements) {
            const elementNames = VueBootstrap.getUniqueElementNames(elements);
            const lazyPromises = this.getLazyPromises(elementNames);
            VueBootstrap.loadLazyModules(lazyPromises);
        }
    }

    private getElements(): NodeListOf<Element> | undefined {
        return document.querySelectorAll(this.buildQuery());
    }

    private buildQuery() {
        return Object.keys(this.modulePromises).join();
    }

    private static getUniqueElementNames(elements: NodeListOf<Element>): Set<string> {
        return new Set(Array.from(elements).map((el: Element) => el.tagName.toLowerCase()));
    }

    private getLazyPromises(elementNames: Set<string>): (() => Promise<ExportedModuleDefinition>)[] {
        const promises: (() => Promise<ExportedModuleDefinition>)[] = [];
        elementNames.forEach(elementName => {
            const modulePromise = this.modulePromises[elementName];
            modulePromise && promises.push(modulePromise);
        });
        return promises;
    }

    private static async loadLazyModules(promises: (() => Promise<ExportedModuleDefinition>)[]) {
        if (Array.isArray(promises) && promises.length) {
            const data = await Promise.allSettled(promises.map(promise => promise()));
            data.filter(isFulfilled).forEach(f => {
                f.value.default.getComponentDefinition();
            })
        }
    }
}
