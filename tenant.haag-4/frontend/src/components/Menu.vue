<script setup lang="ts">
defineProps<{ config: { message: string } }>()
</script>

<template>
  <p>{{ config.message }}</p>
</template>

<style scoped>
p {
  background: greenyellow;
}
</style>
