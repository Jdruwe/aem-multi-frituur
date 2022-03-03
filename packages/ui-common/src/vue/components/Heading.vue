<script setup lang="ts">
defineProps<{ config: { image: string, alt: string } }>()
</script>

<template>
  <img :src="config.image" :alt="config.alt">
</template>

<style scoped>
img {
  max-width: 400px;
}
</style>
