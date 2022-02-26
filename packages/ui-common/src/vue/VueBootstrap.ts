import {createApp} from "vue";
import {getDistinctElementNames, getElements} from "../dom/util";
import {Component} from "@vue/runtime-core";

export interface ModulePromises {
    [key: string]: () => Promise<ExportedModuleDefinition>;
}

export interface ModuleDefinition {
    getComponent: () => Component;
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
    }

    public init(): void {
        const elements = getElements(this.buildQuery());
        if (elements) {
            const elementNames = getDistinctElementNames(elements);
            const elementsPromises = this.getElementsPromises(elementNames, elements);
            this.resolveElementsPromises(elementsPromises);
        }
    }

    private buildQuery() {
        return Object.keys(this.modulePromises).join();
    }

    private getElementsPromises(elementNames: Set<string>, elements: HTMLElement[]): ElementsPromise[] {
        const results: ElementsPromise[] = [];
        elementNames.forEach(elementName => {
            results.push({
                elements: this.getElementsWithName(elements, elementName),
                promise: this.modulePromises[elementName]
            })
        });
        return results;
    }

    private getElementsWithName(elements: HTMLElement[], elementName: string): HTMLElement[] {
        return elements.filter(e => e.tagName.toLowerCase() === elementName.toLowerCase())
    }

    /**
     * @param elementsPromises
     * Resolved promises are returned in the same order as they are requested allowing us to use the index.
     */
    private resolveElementsPromises(elementsPromises: ElementsPromise[]) {
        this.loadModules(elementsPromises).then(data => data.forEach((result, index) => {
            if (result.status === 'fulfilled') {
                this.mountElements(elementsPromises[index].elements, result.value.default.getComponent())
            }
        }))
    }

    private async loadModules(elementsPromises: ElementsPromise[]): Promise<PromiseSettledResult<ExportedModuleDefinition>[]> {
        return await Promise.allSettled(elementsPromises.map(ep => ep.promise()));
    }

    private mountElements(elements: HTMLElement[], component: Component) {
        elements.forEach(element => createApp(component, {...element.dataset}).mount(element));
    }
}
