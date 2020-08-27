import Vue from 'vue'

import {
    Button,
    Row,
    Col,
    Form,
    FormItem,
    Input,
    Notification,
    Container,
    Header,
    MenuItem,
    Menu,
    Submenu,
    Avatar,
    Aside,
    Main,
    Card,
    Divider,
    Table,
    TableColumn,
    Message,
    Dialog,
    Tag,
    Popover,
    Alert,
    Checkbox,
    CheckboxGroup,
    Select,
    Option,
    Upload,
    Switch,
    Pagination,
    Popconfirm,
    Breadcrumb,
    BreadcrumbItem,
    Image
} from 'element-ui';

Vue.use(Image);
Vue.use(BreadcrumbItem);
Vue.use(Breadcrumb);
Vue.use(Popconfirm);
Vue.use(Pagination);
Vue.use(CheckboxGroup);
Vue.use(Switch);
Vue.use(Upload);
Vue.use(Option);
Vue.use(Select);
Vue.use(Checkbox);
Vue.use(Alert);
Vue.use(Popover);
Vue.use(Tag);
Vue.use(Dialog);
Vue.use(TableColumn);
Vue.use(Table);
Vue.use(Divider);
Vue.use(Card);
Vue.use(Main);
Vue.use(Aside);
Vue.use(Submenu);
Vue.use(Avatar);
Vue.use(MenuItem);
Vue.use(Menu);
Vue.use(Header);
Vue.use(Container);
Vue.use(Input);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Col);
Vue.use(Row);
Vue.use(Button);

Vue.prototype.$notify = Notification;
Vue.prototype.$message = Message;

import '../assets/theme/index.css'
