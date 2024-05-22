<template>
  <div class="form-container">
    <el-form label-width="auto" label-position="left">
      <el-form-item label="Username:">
        <el-input style="width: 200px" v-model="username" placeholder="Enter your name"></el-input>
      </el-form-item>
      <el-form-item label="Password:">
        <el-input
          style="width: 200px"
          v-model="password"
          placeholder="Enter your password"
          show-password
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button class="button" type="primary" @click="login">Login</el-button>
        <el-button class="button" type="info" @click="router.push('/register')">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')

const router = useRouter()

const login = async () => {
  const params = new URLSearchParams()
  params.append('username', username.value)
  params.append('password', password.value)
  await axios
    .post('/login', params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      withCredentials: true
    })
    .then((res) => {
      const user = res.data.data
      console.log('User:', user)

      localStorage.setItem('userId', JSON.stringify(user.id))
      localStorage.setItem('role', JSON.stringify(user.role))
      router.push('/borrow')
    })
    .catch((error) => {
      console.error(error)
    })
}
</script>

<style scoped>
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-button {
  margin-top: 20px;
}
</style>
