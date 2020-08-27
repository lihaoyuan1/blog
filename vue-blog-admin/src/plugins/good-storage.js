import Vue from 'vue'
import storage from 'good-storage'

function save(key, obj){
    storage.set(key, obj);
}

function load(key) {
    return storage.get(key, null);
}

function remove(key) {
    storage.remove(key);
}

function sessionSave(key, obj){
    storage.session.set(key, obj);
}

function sessionLoad(key){
    storage.session.get(key, null);
}

function sessionRemove(key){
    storage.session.remove(key);
}

Vue.prototype.saveStorage = save;
Vue.prototype.loadStorage = load;
Vue.prototype.removeStorage = remove;
Vue.prototype.sessionSave = sessionSave;
Vue.prototype.sessionLoad = sessionLoad;
Vue.prototype.sessionRemove = sessionRemove;