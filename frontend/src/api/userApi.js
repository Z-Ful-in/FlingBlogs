import request from '../utils/request.js'

const userApi = {
    login(data){
        return request.post('/users/login', data)
    },

    register(data){
        return request.post('/users/register', data)
    },

    getMyArticles(){
        return request.get('/articles/me')
    }
}

export default userApi