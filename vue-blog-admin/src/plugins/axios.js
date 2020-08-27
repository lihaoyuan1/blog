import Vue from 'vue';
import axios from "axios";
import qs from "qs";
import router from "../router";
import {Notification} from 'element-ui'

if (process.env.NODE_ENV === 'development') {

    axios.defaults.baseURL = '/api';

} else if (process.env.NODE_ENV === 'debug') {

    axios.defaults.baseURL = '';

} else if (process.env.NODE_ENV === 'production') {

    axios.defaults.baseURL = 'http://www.lihaoyuan.online/lhy-blog/admin';

}

axios.defaults.timeout = 10000;
axios.defaults.withCredentials = true;

let showMessageFlag = true;

axios.interceptors.request.use(
    function (config) {
        // Do something before request is sent
        // let token = storage.session.get('token', null);
        // if (token !== null)
        //     config.headers.common['token'] = token;
        return config;
    },
    function (error) {
        // Do something with request error
        return Promise.reject(error);
    }
);

// Add a response interceptor
axios.interceptors.response.use(
    function (response) {
        // Do something with response data
        if (response && response.status === 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    function (error) {
// Do something with response error
        if (showMessageFlag){
            showMessageFlag = false;
            if (error.response && error.response.status === 401) {
                Notification.error({
                    title: '警告',
                    message: error.response.data.message
                });
                router.replace('/login');
            }
            setTimeout(() => {
                showMessageFlag = true;
            }, 1000);
        }
        return Promise.reject(error);
    }
);


function get(url, params, contentType = 'application/x-www-form-urlencoded;charset=UTF-8') {
    // eslint-disable-next-line no-unused-vars
    return new Promise((resolve, reject) => {
        axios.get(url, {
            params: params,
            headers: {'Content-Type': contentType}
        }).then(res => {
            resolve(res.data)
        }).catch(err => {
            // eslint-disable-next-line no-console
            console.log(err);
        })
    });
}

function post(url, params, contentType = 'application/x-www-form-urlencoded;charset=UTF-8') {
    // eslint-disable-next-line no-unused-vars
    return new Promise((resolve, reject) => {
        axios.post(url, qs.stringify(params), {
            headers: {'Content-Type': contentType}
        }).then(res => {
            resolve(res.data)
        }).catch(err => {
            // eslint-disable-next-line no-console
            console.log(err);
        })
    });
}

Vue.prototype.get = get;
Vue.prototype.post = post;
