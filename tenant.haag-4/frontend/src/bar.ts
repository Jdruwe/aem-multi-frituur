import {ModuleDefinition} from '@jeroendruwe/ui-common'

const BarModule: ModuleDefinition = {
    getComponentDefinition: () => {
        console.log('>>> BAR');
    },
};

export default BarModule;
