<template>
  <div class="container">
    <el-switch v-model="switchKey" size="large" active-text="User" inactive-text="Book" />
    <template v-if="switchKey === true">
      <UserManagement />
    </template>
    <template v-else-if="switchKey === false">
      <BookManagement />
    </template>
  </div>
</template>

<script setup>
import UserManagement from '@/components/UserManagement.vue'
import BookManagement from '@/components/BookManagement.vue'
import { ref } from 'vue'

const switchKey = ref(true)
</script>

<style scoped></style>
