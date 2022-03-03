<script setup lang="ts">
defineProps<{ config: { message: string } }>()
</script>

<template>
  <h1>OpeningHours</h1>
  <p>{{ config.message }}</p>
</template>

<style scoped>
p {
  background: lightblue;
}
</style>
