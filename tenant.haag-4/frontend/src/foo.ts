import {ModuleDefinition} from '@jeroendruwe/ui-common'
import Foo from './components/Foo.vue'

const FootModule: ModuleDefinition = {
    getComponentDefinition: () => {
        return Foo;
    },
};

export default FootModule;
