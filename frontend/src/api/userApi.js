import request from './request.js'

const userApi = {
    login(data){
        return request.post('/user/login', data)
    },

    register(data){
        return request.post('/user/register', data)
    },
}