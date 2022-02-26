import {ModuleDefinition} from '@jeroendruwe/ui-common'
import Bar from './components/Bar.vue'

const BarModule: ModuleDefinition = {
    getComponent: () => {
        return Bar;
    },
};

export default BarModule;
