import '../styling/menu.css';
import {Menu, ModuleDefinition} from '@jeroendruwe/ui-common'

const MenuModule: ModuleDefinition = {
    getComponent: () => {
        return Menu;
    },
};

export default MenuModule;
