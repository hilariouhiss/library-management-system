<template>
  <el-dialog v-model="dialogVisible" title="warning" width="500">
    <span>Input can not be blank</span>
  </el-dialog>
  <div class="search-container">
    <el-input v-model="input" style="width: 240px" placeholder="Title or ISBN" clearable />
    <el-button @click="search">search</el-button>
  </div>
  <div class="table-container">
    <el-table :data="books">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="title" label="Title"></el-table-column>
      <el-table-column prop="author" label="Author"></el-table-column>
      <el-table-column prop="publisher" label="Publisher"></el-table-column>
      <el-table-column prop="publishDate" label="Publish Date"></el-table-column>
      <el-table-column prop="isbn" label="ISBN"></el-table-column>
      <el-table-column prop="quantity" label="Quantity"></el-table-column>
      <el-table-column label="Action">
        <template v-slot:default="scope">
          <el-button
            type="success"
            v-if="!borrowedBooks.includes(scope.row.id) && scope.row.quantity > 0"
            @click="borrowBook(scope.row.id)"
            >Borrow</el-button
          >
          <el-text v-else-if="borrowedBooks.includes(scope.row.id)" type="info">Borrowed</el-text>
          <el-text v-else type="danger">Out of Stock</el-text>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'

const input = ref('')
const books = ref([])
const dialogVisible = ref(false)

const userId = JSON.parse(localStorage.getItem('userId'))

const borrowedBooks = localStorage.getItem('borrowedBooks')
  ? JSON.parse(localStorage.getItem('borrowedBooks'))
  : []

const search = async () => {
  if (input.value === '') {
    dialogVisible.value = true
    return
  }
  try {
    const res = await axios.get(`/book/search/${input.value}`, {
      withCredentials: true
    })
    books.value = res.data.data
  } catch (error) {
    console.error(error)
  }
}

watch(input, (newValue) => {
  if (newValue === '') {
    fetchBooks()
  }
})

const borrowBook = async (bookId) => {
  console.log(userId, bookId)
  try {
    await axios.get('/borrow/add', {
      params: {
        userId: userId,
        bookId: bookId
      },
      withCredentials: true
    })
    fetchBooks()
  } catch (error) {
    console.error(error)
  }
}

const fetchBooks = async () => {
  try {
    await axios
      .get('/book', {
        withCredentials: true
      })
      .then((res) => {
        books.value = res.data.data
      })
  } catch (error) {
    console.error(error)
  }
}

onMounted(fetchBooks)
</script>

<style scoped>
.search-container {
  display: flex;
  justify-content: right;
  margin-top: 30px;
  margin-right: 8%;
  margin-bottom: 20px;
}

.table-container {
  margin-left: 8%;
  margin-right: 8%;
  margin-bottom: 20px;
}
</style>
