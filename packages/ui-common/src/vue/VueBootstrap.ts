import {createApp} from "vue";
import {Component} from "@vue/runtime-core";
import {getDistinctElementNames, getElements} from "../dom/util";

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

export interface RenderRequestEvent {
    data: {
        type: string;
    };
}

export class VueBootstrap {

    private readonly modulePromises: ModulePromises;

    private readonly PROMISE_STATUS_FULFILLED = 'fulfilled';
    private readonly MESSAGE_EVENT = 'message';
    private readonly MESSAGE_RENDER_REQUEST = 'render-request';
    private readonly ATTRIBUTE_ELEMENT_MOUNTED = 'data-v-app';

    constructor(modulePromises: ModulePromises) {
        this.modulePromises = modulePromises;
    }

    public init(): void {
        this.render();
        this.listenToRenderRequest()
    }

    private render() {
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
     * Resolved promises are returned in the same order as they are requested allowing the use of index.
     */
    private resolveElementsPromises(elementsPromises: ElementsPromise[]) {
        VueBootstrap.loadModules(elementsPromises).then(data => data.forEach((result, index) => {
            if (result.status === this.PROMISE_STATUS_FULFILLED) {
                this.mountElements(elementsPromises[index].elements, result.value.default.getComponent())
            }
        }))
    }

    private static async loadModules(elementsPromises: ElementsPromise[]): Promise<PromiseSettledResult<ExportedModuleDefinition>[]> {
        return await Promise.allSettled(elementsPromises.map(ep => ep.promise()));
    }

    private mountElements(elements: HTMLElement[], component: Component) {
        elements.forEach(element => {
            if (!this.isElementMounted(element)) {
                createApp(component, {
                    ...(element.dataset.config && {config: JSON.parse(element.dataset.config)})
                }).mount(element)
            }
        });
    }

    private isElementMounted(element: HTMLElement) {
        return element.hasAttribute(this.ATTRIBUTE_ELEMENT_MOUNTED);
    }

    private listenToRenderRequest() {
        window.parent.addEventListener(this.MESSAGE_EVENT, (event): void => this.handleRenderRequest(event), false);
    }

    private handleRenderRequest(event: RenderRequestEvent): void {
        if (this.isValidRenderRequest(event)) {
            this.render();
        }
    }

    private isValidRenderRequest(event: RenderRequestEvent): boolean {
        return event.data && event.data.type === this.MESSAGE_RENDER_REQUEST;
    }
}
