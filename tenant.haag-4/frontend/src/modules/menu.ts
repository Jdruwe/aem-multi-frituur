import {ModuleDefinition} from '@jeroendruwe/ui-common'
import Menu from '../components/Menu.vue'

const MenuModule: ModuleDefinition = {
    getComponent: () => {
        return Menu;
    },
};

export default MenuModule;
