import {ModuleDefinition, OpeningHours} from '@jeroendruwe/ui-common'

const OpeningHoursModule: ModuleDefinition = {
    getComponent: () => {
        return OpeningHours;
    },
};

export default OpeningHoursModule;
