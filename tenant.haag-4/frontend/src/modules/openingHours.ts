import {ModuleDefinition} from '@jeroendruwe/ui-common'
import OpeningHours from '../components/OpeningHours.vue'

const OpeningHoursModule: ModuleDefinition = {
    getComponent: () => {
        return OpeningHours;
    },
};

export default OpeningHoursModule;
