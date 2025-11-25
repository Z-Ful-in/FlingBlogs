import request from '../utils/request.js'

const userApi = {
    login(data){
        return request.post('/user/login', data)
    },

    register(data){
        return request.post('/user/register', data)
    },

    getMyArticles(){
        return request.get('/articles/me')
    }
}

export default userApi