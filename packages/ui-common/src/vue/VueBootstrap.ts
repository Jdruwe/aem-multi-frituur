//TODO: do we need to rename interfaces?

import {createApp} from "vue";

export interface ModulePromises {
    [key: string]: () => Promise<ExportedModuleDefinition>;
}

export interface ModuleDefinition {
    getComponentDefinition: () => any;
}

interface ExportedModuleDefinition {
    default: ModuleDefinition;
}

interface ElementsPromise {
    elements: HTMLElement[],
    promise: () => Promise<ExportedModuleDefinition>
}

export class VueBootstrap {

    private readonly modulePromises: ModulePromises;

    constructor(modulePromises: ModulePromises) {
        this.modulePromises = modulePromises;
        this.initialize();
    }

    private initialize(): void {
        const elements = this.getElements();
        if (elements) {
            const elementNames = VueBootstrap.getDistinctElementNames(elements);
            const lazyPromises = this.getLazyPromises(elementNames, elements);
            VueBootstrap.loadLazyModules(lazyPromises);
        }
    }

    private getElements(): HTMLElement[] {
        return Array.from(document.querySelectorAll(this.buildQuery()));
    }

    private buildQuery() {
        return Object.keys(this.modulePromises).join();
    }

    private static getDistinctElementNames(elements: HTMLElement[]): Set<string> {
        return new Set(elements.map((el: Element) => el.tagName.toLowerCase()));
    }

    private getLazyPromises(elementNames: Set<string>, elements: HTMLElement[]): ElementsPromise[] {
        const results: ElementsPromise[] = [];
        elementNames.forEach(elementName => {
            const promise = this.modulePromises[elementName];
            promise && results.push({
                elements: this.getElementsWithName(elements, elementName),
                promise: promise
            })
        });
        return results;
    }

    private getElementsWithName(elements: HTMLElement[], elementName: string): HTMLElement[] {
        return elements.filter(e => e.tagName.toLowerCase() === elementName.toLowerCase())
    }

    private static async loadLazyModules(elementsPromises: ElementsPromise[]) {
        if (elementsPromises.length) {
            const data = await Promise.allSettled(elementsPromises.map(ep => ep.promise()));
            data.forEach((result, index) => {
                if (result.status === 'fulfilled') {
                    elementsPromises[index].elements.forEach(el => {
                        const app = createApp(result.value.default.getComponentDefinition(), {...el.dataset});
                        app.mount(el);
                    })
                }
            });
        }
    }
}
