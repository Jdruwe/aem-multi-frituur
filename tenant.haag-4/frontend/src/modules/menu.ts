import {Menu, ModuleDefinition} from '@jeroendruwe/ui-common'
import '../styling/menu.css';

const MenuModule: ModuleDefinition = {
    getComponent: () => {
        return Menu;
    },
};

export default MenuModule;
