<script setup lang="ts">
defineProps<{ config: { message: string } }>()
</script>

<template>
  <h1>Heading</h1>
  <p>{{ config.message }}</p>
</template>

<style scoped>
p {
  background: lightcoral;
}
</style>
