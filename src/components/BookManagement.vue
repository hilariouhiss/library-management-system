<template>
  <h1>Book Management</h1>
  <div>
    <el-table :data="books" style="width: 100%">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="title" label="Title"></el-table-column>
      <el-table-column prop="author" label="Author"></el-table-column>
      <el-table-column prop="publisher" label="Publisher"></el-table-column>
      <el-table-column prop="publishDate" label="Publish Date"></el-table-column>
      <el-table-column prop="isbn" label="ISBN"></el-table-column>
      <el-table-column prop="quantity" label="Quantity"></el-table-column>
      <el-table-column label="Action">
        <template v-slot:default="scope">
          <el-button type="danger" @click="deleteBook(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-button type="success" @click="dialogFormVisible = true">Add Book</el-button>
  <el-dialog v-model="dialogFormVisible" title="Add Book" width="800">
    <el-form :model="book">
      <el-form-item label="Title">
        <el-input v-model="book.title" />
      </el-form-item>
      <el-form-item label="Author">
        <el-input v-model="book.author" />
      </el-form-item>
      <el-form-item label="Publisher">
        <el-input v-model="book.publisher" />
      </el-form-item>
      <el-form-item label="Publish Date">
        <el-date-picker v-model="book.publishDate" type="date" placeholder="Pick a day" />
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="book.isbn" />
      </el-form-item>
      <el-form-item label="Quantity">
        <el-input-number v-model="book.quantity" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogFormVisible = false">Cancel</el-button>
      <el-button type="primary" @click="addBook"> Confirm </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const books = ref([])
const dialogFormVisible = ref(false)
const book = ref({
  title: '',
  author: '',
  publisher: '',
  publishDate: '',
  isbn: '',
  quantity: 0
})

const addBook = () => {
  dialogFormVisible.value = false
  axios
    .post('/book/add', book.value, {
      withCredentials: true
    })
    .then((res) => {
      console.log(res.data)
      fetchBooks()
    })
    .catch((error) => {
      console.error(error)
    })
}

const fetchBooks = async () => {
  try {
    const res = await axios.get('/book', {
      withCredentials: true
    })
    books.value = res.data.data
  } catch (error) {
    console.error(error)
  }
}

const deleteBook = async (id) => {
  try {
    await axios.delete(`/book/delete/id/${id}`, {
      withCredentials: true
    })
    fetchBooks()
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  fetchBooks()
})
</script>

<style scoped></style>
