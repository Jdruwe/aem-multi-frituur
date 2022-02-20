import {ModuleDefinition} from '@jeroendruwe/ui-common'

const FootModule: ModuleDefinition = {
    getComponentDefinition: () => {
        console.log('>>> FOO');
    },
};

export default FootModule;
