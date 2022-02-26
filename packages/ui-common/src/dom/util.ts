export const getDistinctElementNames = (elements: HTMLElement[]): Set<string> => {
    return new Set(elements.map((el: Element) => el.tagName.toLowerCase()));
}

export const getElements = (query: string): HTMLElement[] => {
    return Array.from(document.querySelectorAll(query));
}
