<script setup>
import userApi from '../api/userApi.js'
import {ref} from "vue";
import {useRouter} from "vue-router";

const username = ref("")
const password = ref("")
const email = ref("")
const router = useRouter()

const register = async () => {
  try {
    const response = await userApi.register({
      username: username.value,
      password: password.value,
      email: email.value
    })
    alert("register success")
    router.push('/login')
  }catch (error) {
    alert("register failed")
    console.error(error)
  }
}

</script>

<template>

</template>

<style scoped>

</style>