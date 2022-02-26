import {ModuleDefinition} from '@jeroendruwe/ui-common'
import Foo from './components/Foo.vue'

const FootModule: ModuleDefinition = {
    getComponent: () => {
        return Foo;
    },
};

export default FootModule;
