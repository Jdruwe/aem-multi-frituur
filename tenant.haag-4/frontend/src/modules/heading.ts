import {ModuleDefinition} from '@jeroendruwe/ui-common'
import Heading from '../components/Heading.vue'

const HeadingModule: ModuleDefinition = {
    getComponent: () => {
        return Heading;
    },
};

export default HeadingModule;
