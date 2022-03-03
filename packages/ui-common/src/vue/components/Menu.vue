<script setup lang="ts">
defineProps<{ config: { message: string } }>()
</script>

<template>
  <h1>Menu</h1>
  <p>{{ config.message }}</p>
</template>

<style scoped>
p {
  background: lightgreen;
}
</style>
