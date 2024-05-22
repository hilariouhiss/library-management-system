<template>
  <div class="form-container">
    <el-form :model="user" label-width="auto">
      <el-form-item label="Username:">
        <el-input v-model="user.username" />
      </el-form-item>
      <el-form-item label="Password:">
        <el-input v-model="user.password" show-password />
      </el-form-item>
      <el-form-item label="Email:">
        <el-input v-model="user.email" />
      </el-form-item>
      <el-button type="primary" @click="register">Confirm</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const user = ref({
  username: '',
  password: '',
  email: ''
})

const register = async () => {
  if (!user.value.username || !user.value.password || !user.value.email) {
    alert('Please fill in all fields')
  }
  await axios.post('/register', user.value).then((res) => {
    if (res.data.status === 200) {
      const userLogin = new URLSearchParams()
      userLogin.append('username', user.value.username)
      userLogin.append('password', user.value.password)
      axios
        .post('/login', userLogin, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          withCredentials: true
        })
        .then((res) => {
          console.log('User:', res.data.data)
          localStorage.setItem('userId', JSON.stringify(res.data.data.id))
          localStorage.setItem('role', JSON.stringify(res.data.data.role))
          router.push('/book')
        })
        .catch((error) => {
          console.error(error)
        })
    }
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
</style>
