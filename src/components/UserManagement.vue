<template>
  <div>
    <h1>User Management</h1>
  </div>
  <el-table :data="users">
    <el-table-column prop="id" label="ID"></el-table-column>
    <el-table-column prop="username" label="Name"></el-table-column>
    <el-table-column prop="email" label="Email"></el-table-column>
    <el-table-column prop="role" label="Role"></el-table-column>
    <el-table-column label="Action">
      <template v-slot:default="scope">
        <el-button type="danger" @click="deleteUser(scope.row.id)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])

const fetchUsers = async () => {
  try {
    const res = await axios.get('/user', {
      withCredentials: true
    })
    users.value = res.data.data
  } catch (error) {
    console.error(error)
  }
}

const deleteUser = async (id) => {
  // Implement delete user
  await axios
    .delete(`/user/delete/${id}`, {
      withCredentials: true
    })
    .then(() => {
      fetchUsers()
    })
    .catch((error) => {
      console.error(error)
    })
}

onMounted(fetchUsers)
</script>

<style scoped></style>
