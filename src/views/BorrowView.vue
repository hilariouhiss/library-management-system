<template>
  <template v-if="role === 'ADMIN'">
    <router-link to="/management">
      <el-text>Management</el-text>
    </router-link>
  </template>
  <div class="table-container">
    <el-table :data="borrows" style="width: 100%">
      <el-table-column prop="book.title" label="Book Title"></el-table-column>
      <el-table-column prop="borrowDate" label="Borrow Date"></el-table-column>
      <el-table-column prop="returnDate" label="Return Date"></el-table-column>
      <el-table-column prop="status" label="Status" :formatter="formatStatus"></el-table-column>
      <el-table-column label="Actions">
        <template v-slot:default="scope">
          <el-button
            type="warning"
            style="width: 100px"
            v-if="scope.row.status === 0"
            @click="returnBook(scope.row.id)"
            >Return Book</el-button
          >
          <el-button type="info" style="width: 100px" v-else @click="borroAgain(scope.row.id)"
            >Borrow Again</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="link-container">
    <router-link to="/book">
      <el-button type="primary">Go to Book Page</el-button>
    </router-link>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const borrows = ref([])
const userId = JSON.parse(localStorage.getItem('userId'))
const role = ref(JSON.parse(localStorage.getItem('role')))

const formatStatus = (row) => {
  return row.status === 0 ? 'Borrowed' : 'Returned'
}

const returnBook = async (borrowId) => {
  console.log('BorrowId:', borrowId)
  try {
    await axios.get(`/borrow/return/${borrowId}`, {
      withCredentials: true
    })
    fetchBorrows()
  } catch (error) {
    console.error(error)
  }
}

const borroAgain = async (id) => {
  try {
    await axios.get('/borrow/add', {
      params: {
        userId: userId,
        bookId: id
      },
      withCredentials: true
    })
  } catch (error) {
    console.error(error)
  }

  fetchBorrows()
}

const fetchBorrows = async () => {
  const userId = JSON.parse(localStorage.getItem('userId'))
  try {
    const res = await axios.get(`/borrow/get/userId/${userId}`, {
      withCredentials: true
    })
    borrows.value = res.data.data
    localStorage.setItem(
      'borrowedBooks',
      JSON.stringify(
        res.data.data
          .filter((book) => book.status === 0)
          .map((book) => {
            book.id
          })
      )
    )
  } catch (error) {
    console.error(error)
  }
}

onMounted(fetchBorrows)
</script>

<style scoped>
.table-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
  margin-top: 50px;
  width: 95%;
}

.link-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
}
</style>
